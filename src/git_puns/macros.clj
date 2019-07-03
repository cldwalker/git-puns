(ns git-puns.macros)

(defmacro inline-file [path]
  (slurp path))
