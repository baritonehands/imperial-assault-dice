(ns imperial-assault.attack
  (:require [imperial-assault.dice :as dice]
            [clojure.math.combinatorics :as combo]))

(defn apply-surge [roll surge]
  (-> (reduce
        #(dice/sum %&)
        roll
        surge)
      (update '⚡ #(- (or % 0) (count surge)))))

(defn apply-pierce [roll pierce]
  (update roll '⚡ #(max (+ (or % 0) (or pierce 0)) 0)))

(defn surge-combos [surge atk blk]
  (let [n (-> (get atk '⚡ 0)
              (+ (get blk '⚡ 0))
              (max 0))]
    (combo/permuted-combinations surge n)))

(defn total [{:keys [dice surge baseline]} defense]
  (->> (for [atk (dice/rolls dice)
             blk (dice/rolls defense)
             combo (surge-combos surge (dice/sum [atk baseline]) blk)]
         (let [with-status (dice/sum [atk baseline])
               surged (apply-surge with-status combo)]
           (dice/sum [surged (apply-pierce blk (:pierce surged))])))
       (sort-by dice/rank)))
;(distinct)))
