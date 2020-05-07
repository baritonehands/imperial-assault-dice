(ns imperial-assault.abilities
  (:require [imperial-assault.dice :as dice]))

(defn dancing-weapon [weapon]
  (-> weapon
      (update :dice conj dice/blue)
      (update :surge conj '{:range 2
                            *      1})))
