(defn aux-reverse [ a b ]
  "return the list obtained by .... "
  (if (= a ())
              b
              (aux-reverse (rest a) (cons (first a) b) )
  )
)

(defn my-reverse [ x ]
  "return the given list in reversed order"
  (aux-reverse x () )
)

(defn myfib [ n ]
  "compute nth fibonacci number"
;  (println "doing fib on " n)
  (if (<= n 1)
               1
               (+ (myfib (- n 1)) (myfib (- n 2)) )
  )
)

(defn my-nth [ list index ]
  "return item in position index in the list (don't worry about index out of range)"
  (if (= index 0)
                 (first list)
                 (my-nth (rest list) (- index 1) )
  )
)

(defn myfib2 [ n table ]
  "efficient dynamic programming version of fib"
)
