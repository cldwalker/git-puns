(ns git-puns.aux)

(def ^:private atom-ed (js/require "atom"))
(def ^:private CompositeDisposable (.-CompositeDisposable atom-ed))
(def subscriptions (atom (CompositeDisposable.)))

(defn reset-subscriptions []
  (reset! subscriptions (CompositeDisposable.)))

(defn command-for [name f]
  (let [cmd (-> js/atom .-commands (.add "atom-text-editor"
                                          (str "git-puns:" name)
                                          f))]
    (.add @subscriptions cmd)))
