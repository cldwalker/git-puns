(ns git-puns.commands
  (:require [clojure.string :as string])
  (:require-macros [git-puns.macros :as m]))

;; Joke command
;; ============
;; From https://github.com/EugeneKay/git-jokes/
(def jokes-string (m/inline-file "resources/jokes.txt"))

(defn fetch-joke []
  (rand-nth (string/split-lines jokes-string)))

(defn- joke-command []
  (.. js/atom -notifications (addInfo (fetch-joke) #js{:dismissable true})))

;; Wrapped github commands
;; =======================
(defn- invoke-commands [commands]
  (let [editor (js/atom.workspace.getActiveTextEditor)
        editor-view (js/atom.views.getView editor)]
    (doseq [cmd commands]
      (js/atom.commands.dispatch editor-view cmd))))

(def github-push-with-joke-command
  (partial invoke-commands ["github:push" "git-puns:joke"]))

(def github-pull-with-joke-command
  (partial invoke-commands ["github:pull" "git-puns:joke"]))

;; Exported commands
(def commands {:joke joke-command
               :github-push-with-joke github-push-with-joke-command
               :github-pull-with-joke github-pull-with-joke-command})
