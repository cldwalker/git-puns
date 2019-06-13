(ns git-puns.core
  (:require [git-puns.aux :as aux]))

(def config {})

(defn- toggle []
  (prn :PUNS :TOGGLED!))

(defn activate []
  (aux/reset-subscriptions)
  (aux/command-for "toggle" toggle))

(defn deactivate []
  (.dispose @aux/subscriptions))

(defn before [done]
  (let [main (.. js/atom -packages (getActivePackage "git-puns") -mainModule)]
    (.deactivate main)
    (done)))

(defn after []
  (let [main (.. js/atom -packages (getActivePackage "git-puns") -mainModule)]
    (.activate main)
    (.. js/atom -notifications (addSuccess "Reloaded Git Puns"))
    (println "Reloaded")))
