(ns clojure-hbase.internal
  (:refer-clojure :rename {get map-get}))

;; This file contains utility functions that probably won't be useful for
;; client code. Shouldn't need to use util. But hey, it's here if you want it.

(defn partition-query
  "Given a query sequence and a command argnum map (each keyword in map
   mapped to how many arguments that item expects), this function returns
   a sequence of sequences; each sub-sequence is just a single command,
   command keyword followed by args."
  [query cmd-argnum-map]
  (loop [result []
         remaining-commands query]
    (let [kw (first remaining-commands)]
      (if (nil? kw)
        result
        (let [[a-cmd rest-cmds] (split-at (inc (map-get cmd-argnum-map kw 1))
                                          remaining-commands)]
          (recur (conj result a-cmd) rest-cmds))))))