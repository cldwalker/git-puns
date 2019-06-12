(ns git-puns.core
  (:require [git-puns.aux :as aux]))

(def config {})

(defn- toggle []
  (prn :TOGGLED!))

(defn activate []
  (aux/create-subscriptions)
  (aux/command-for "toggle" toggle))

(defn deactivate []
  (.dispose @aux/subscriptions))

(defn before [done]
  (deactivate)
  (done)
  (activate)
  (println "Reloaded"))
