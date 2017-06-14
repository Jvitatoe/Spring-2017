(defn myfib [ n first second]
    (if (<= n 1)
         first
        (myfib (- n 1) (+ second first) first)
    )
)
