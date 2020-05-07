(ns imperial-assault.weapons
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

(defn total [{:keys [dice surge free]} distance defense]
  (->> (for [atk (dice/rolls dice)
             blk (dice/rolls defense)
             combo (surge-combos surge atk blk)
             :when (-> (apply-surge atk combo)
                       :range
                       (or 0)
                       (>= distance))]
         (let [surged (apply-surge atk combo)]
           (reduce
             #(dice/sum %&)
             [free surged (apply-pierce blk (:pierce surged))])))
       (sort-by dice/rank)))
       ;(distinct)))

(def vintage-blaster
  {:dice  [dice/green dice/green]
   :surge ['{* 1}
           '{:range 1}]
   :free  {}})

(def sporting-blaster
  {:dice  [dice/blue dice/yellow dice/yellow]
   :surge ['{* 1}
           '{:pierce 2}
           '{:range 2}
           '{:stun 1}]
   :free  {}})

(def plasteel-staff
  {:dice  [dice/green dice/yellow]
   :surge ['{* 1}
           '{:stun 1}]
   :free  {}})

(def force-pike
  {:dice  [dice/red dice/yellow dice/yellow]
   :surge ['{* 1}
           '{* 1}
           '{:stun 1}]
   :free  {}})

