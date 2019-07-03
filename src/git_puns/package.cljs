(ns git-puns.package)

;; Subscriptions are currently unused but may be used if more features are added
(def ^:private atom-ed (js/require "atom"))
(def ^:private CompositeDisposable (.-CompositeDisposable atom-ed))
(def subscriptions (atom (CompositeDisposable.)))

(defn- reset-subscriptions []
  (reset! subscriptions (CompositeDisposable.)))

(defn command-for [name f]
  (let [cmd (-> js/atom .-commands (.add "atom-text-editor"
                                          (str "git-puns:" name)
                                          f))]
    (.add @subscriptions cmd)))

(defn activate
  "Activates packages by creating commands and defining a Disposable for event subscriptions. Given commands is a map of names to fns"
  [commands]
  (reset-subscriptions)
  (doseq [[cmd cmd-fn] commands]
    (command-for (name cmd) cmd-fn)))

(defn deactivate
  []
  (.dispose @subscriptions))
