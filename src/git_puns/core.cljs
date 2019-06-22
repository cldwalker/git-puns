(ns git-puns.core
  (:require [git-puns.package :as package]
            [git-puns.commands :as commands]))

;; Package hooks
;; =============
(def activate (partial package/activate commands/commands))
(def deactivate package/deactivate)

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
