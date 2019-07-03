## Description

This [Atom](https://atom.io/) package displays a [random git pun](https://github.com/EugeneKay/git-jokes/) to add an extra 😆 to your git flow. This package also serves as a PoC on writing a package using ClojureScript.

## Install
This package is not distributed as an Atom package yet. For now, install with:

```sh
$ git clone https://github.com/cldwalker/git-puns ~/.atom/packages/
$ cd ~/.atom/packages/git-puns/
$ yarn install
$ yarn shadow-cljs compile dev
```
Note: You will need Java installed for shadow-cljs to compile ClojureScript - https://github.com/thheller/shadow-cljs#requirements

If Atom was open while installing, you may need to run the `Window: Reload` command for the package to take effect.

## Usage

This package provides three commands:

* `Git Puns: Joke` - Tell a joke!
* `Git Puns: Github Push With Joke` - Executes the [standard github](https://github.com/atom/github) push command and then tells a joke
* `Git Puns: Github Pull With Joke` - Executes the [standard github](https://github.com/atom/github) pull command and then tells a joke

No keybindings are configured by default. If you'd like to override the default push, you can with:

```js
'atom-workspace atom-pane, atom-workspace atom-text-editor:not(.mini)':
  'alt-g p': 'git-puns:github-push-with-joke'
```

## Package Development

This section is for those interested in the development of this package.

### ClojureScript and Atom

The following list describes what is required to setup ClojureScript/cljs to interface with the Atom package API:
* package.json's `main` key specifies a lib/main.js as the main entry point. In shadow-cljs.edn, we specify the `:output-to` key to be lib/main.js as the final asset built from our cljs code.
* In shadow-cljs.edn we configure the `:exports` key to export cljs fns to the main module. For this package, we define `activate` and `deactivate` of the [available Atom functions](https://flight-manual.atom.io/hacking-atom/sections/package-word-count/#source-code).
* Commands are configured under the `activationCommands` key in package.json. These commands are defined as a cljs map in `git-puns.commands/commands`.
* The shadow-cljs repl reload is configured under the `:devtools` key. The functions referenced there activate/deactivate the package and notify the user when the package reloads.

### REPL Features

Since this package is written in ClojureScript, it provides a powerful repl environment. When a developer saves a ClojureScript file:
* The ClojureScript files are rebuilt, all relevant Atom and cljs state is reset and all file changes are available in the repl.
  * To avoid resetting cljs state, use `defonce` on vars as needed.
  * To see if a failure occurs during reload, you will need to check the JS console after running the `Window: Toggle Dev Tools` command.
* When the changes are done reloading, a developer gets an explicit reloaded message in Atom.

### REPL Setup
```sh
# Kick off a watcher to compile clojurescript to js
$ yarn shadow-cljs watch dev

# In another terminal, start your repl
$ yarn shadow-cljs cljs-repl dev
[1:1]~cljs.user=>

# In Atom, activate the package by kicking off one of its commands e.g. choose "Git Puns: Joke"

# Your repl should now be live! To confirm, kick off an alert in Atom
[1:1]~cljs.user=> (js/alert "WOOT")
```

To confirm something more interesting, inspect your Atom editor. Your output will look similar to the following:

```clojure
[1:1]~cljs.user=> (-> js/atom js-keys)
#js ["id" "clipboard" "updateProcessEnv" "enablePersistence" "applicationDelegate" "nextProxyRequestId" "unloading" "l
oadTime" "emitter" "disposables" "pathsWithWaitSessions" "deserializers" "deserializeTimings" "views" "notifications"
"stateStore" "config" "keymaps" "tooltips" "commands" "uriHandlerRegistry" "grammars" "styles" "packages" "themes" "me
nu" "contextMenu" "project" "commandInstaller" "protocolHandlerInstaller" "textEditors" "workspace" "autoUpdater" "win
dowEventHandler" "history" "window" "document" "blobStore" "configDirPath" "appVersion" "initialStyleElements" "styles
Element" "previousWindowErrorHandler" "windowDimensions" "reopenProjectMenuManager" "backgroundStylesheet" "devMode" "
specMode" "shellEnvironmentLoaded" "saveStateDebounceInterval"]
[1:1]~cljs.user=> (-> js/atom .-commands js-keys)
#js ["handleCommandEvent" "rootNode" "registeredCommands" "selectorBasedListenersByCommandName" "inlineListenersByComm
andName" "emitter"]
[1:1]~cljs.user=> (-> js/atom .-commands .-registeredCommands js-keys count)
1049
```

### REPL Driven Development

This section is for those unfamiliar with repl driven development.

The repl is normally used for iterative development. This workflow allows for confirming individual functions on the way to building a full feature. For example, let's say we want to work on the main function for telling jokes, `fetch-joke`.

```clojure
[1:1]~cljs.user=> (require '[git-puns.commands :as c])
nil
[1:1]~cljs.user=> (c/fetch-joke)
Error: ENOENT: no such file or directory, open '/Users/me/code/repo/git-puns/lib/resources/jokes.txt'
...
```

Oops. We have incorrectly wired up the resources file. After some further repl testing we obtain the correct path. Once we've updated our code, we simply save the file and we get an Atom notification `Reloaded Git Puns`. Now we can confirm if our function works.

```clojure
[1:1]~cljs.user=> (require '[git-puns.commands :as c])
nil
[1:1]~cljs.user=> (c/fetch-joke)
"git-bisect: The good, the bad and the... uhh... skip"
```

Huzzah!

For more on repl driven development, see [Stuart Halloway's excellent presentation](https://github.com/stuarthalloway/presentations/wiki/REPL-Driven-Development).

### Testing

Testing is configured through `deps.edn`. To run tests, be sure to have the [clojure binary installed](https://clojure.org/guides/getting_started).

To run tests: `clj -Atest`

To have tests run as changes are made to src/ or test/: `clj -Atest -w src`

For more options on running tests, run `clj -Atest --help` and consult the [test runner documentation](https://github.com/Olical/cljs-test-runner).

## License
See LICENSE.md

## Credits
Thanks to @mauricioszabo for sharing his [useful package](https://github.com/mauricioszabo/atom-chlorine) and [blog post](https://mauricio.szabo.link/blog/2018/10/02/atom-packages-with-clojurescript-upgraded/#more-1022). The approach taken here is primarily based on his work.
