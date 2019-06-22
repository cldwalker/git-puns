(ns git-puns.commands
  (require [clojure.string :as string]))

(def fs (js/require "fs"))
;; From https://github.com/EugeneKay/git-jokes/
(def jokes-file (str js/__dirname "/../resources/jokes.txt"))
(def jokes-string (str (.readFileSync fs jokes-file)))

(defn- fetch-joke []
  (rand-nth (string/split-lines jokes-string)))

(defn- joke-command []
  (.. js/atom -notifications (addInfo (fetch-joke) #js{:dismissable true})))

(def commands {:joke joke-command})
