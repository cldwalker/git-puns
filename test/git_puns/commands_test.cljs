(ns git-puns.commands-test
  (:require [cljs.test :refer [deftest is]]
            [clojure.string :as s]
            [git-puns.commands :as commands]))

(deftest fetch-joke
  (is (s/includes? commands/jokes-string (commands/fetch-joke))))
