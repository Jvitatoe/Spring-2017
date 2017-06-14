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
  (println "doing myfib on " n)
  (if (<= n 1)
               1
               (+ (myfib (- n 1)) (myfib (- n 2)) )
  )
)

(defn myfib3aux [ n ]
  "return list of all fib guys (fib(n) fib(n-1) ... 3 2 1 1)"
  (cond 
     (= n 0) '(1)
     (= n 1) '(1 1)
     true
          (let [chart (myfib3aux (- n 1))]
            (cons (+  (first chart) (second chart) ) 
              chart
            )
          )    
  ); end of let zone
)

(def myfib4
  (memoize
      ( fn [ n ]
        (println "calling myfib4 on n=" n)
        (if (<= n 1)
            1
            (+ (myfib4 (- n 1)) (myfib4 (- n 2)) )
        )
      )
  )
)  

(defn our-map [ f stuff ]
  "return a list created by applying f to each item in stuff"
  (if (= stuff () )
      ()
      (cons (f (first stuff)) (our-map f (rest stuff) ) )
  )
)

(defn our-filter [ pred stuff ]
  "return a list containing the items in stuff that make pred true"
  (if (= stuff ())
      ()
      (if (pred (first stuff))
         (cons (first stuff) (our-filter pred (rest stuff)))
         (our-filter pred (rest stuff))
      )
  )
)

(defn mycount [ x ]
   (println x)
   (if (= x ())
       0
       (+ 1 (mycount (rest x)))
   )
)
