(ns git-puns.aux)

(def ^:private atom-ed (js/require "atom"))
(def ^:private CompositeDisposable (.-CompositeDisposable atom-ed))
(def subscriptions (atom (CompositeDisposable.)))

(defn create-subscriptions []
  (reset! subscriptions (atom (CompositeDisposable.))))

; This, for example, creates a command in Atom:
(defn command-for [name f]
  (let [disp (-> js/atom .-commands (.add "atom-text-editor"
                                          (str "git-puns:" name)
                                          f))]
    (.add @subscriptions disp)))
