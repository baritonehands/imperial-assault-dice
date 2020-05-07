(ns imperial-assault.status)

(defn weakened [attacker]
  (update-in
    attacker
    [:baseline '⚡]
    (fn [v]
      (if (nil? v)
        -1
        (- v 1)))))
