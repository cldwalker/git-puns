(ns git-puns.core
  (:require [git-puns.aux :as aux]
            [clojure.string :as string]))

;; Commands
;; ========
(def fs (js/require "fs"))
(def jokes-file (str js/__dirname "/../resources/jokes.txt"))
(def jokes-string (str (.readFileSync fs jokes-file)))

(defn- fetch-joke []
  (rand-nth (string/split-lines jokes-string)))

(defn- joke-command []
  (.. js/atom -notifications (addInfo (fetch-joke) #js{:dismissable true})))

;; Package hooks
;; =============
(defn activate []
  (aux/reset-subscriptions)
  (aux/command-for "joke" joke-command))

(defn deactivate []
  (.dispose @aux/subscriptions))

;; Repl hooks
;; ==========
(defn before [done]
  (let [main (.. js/atom -packages (getActivePackage "git-puns") -mainModule)]
    (.deactivate main)
    (done)))

(defn after []
  (let [main (.. js/atom -packages (getActivePackage "git-puns") -mainModule)]
    (.activate main)
    (.. js/atom -notifications (addSuccess "Reloaded Git Puns"))
    (println "Reloaded")))
