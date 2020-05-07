(ns imperial-assault.dice
  (:require [clojure.math.combinatorics :as combo]))

(def yellow
  ['{⚡ 1}
   '{⚡ 1 * 1}
   '{* 2 :range 1}
   '{* 1 ⚡ 1 :range 1}
   '{⚡ 1 :range 2}
   '{* 1 :range 2}])

(def green
  ['{⚡ 1 :range 1}
   '{⚡ 1 * 1 :range 1}
   '{* 2 :range 1}
   '{* 1 ⚡ 1 :range 2}
   '{* 2 :range 3}
   '{* 2 :range 2}])

(def blue
  ['{⚡ 1 :range 2}
   '{* 1 :range 2}
   '{* 2 :range 3}
   '{* 1 ⚡ 1 :range 3}
   '{* 2 :range 4}
   '{* 1 :range 5}])

(def red
  ['{* 1}
   '{* 2}
   '{* 2}
   '{* 2 ⚡ 1}
   '{* 3}
   '{* 3}])

(def black
  ['{* -1}
   '{* -1}
   '{* -2}
   '{* -2}
   '{* -3}
   '{⚡ -1}])

(def white
  ['{}
   '{* -1}
   '{⚡ -1}
   '{* -1 ⚡ -1}
   '{* -1 ⚡ -1}
   '{* :block}])

(defn roll [dice]
  (mapv #(rand-nth %) dice))

(defn +block [lhs rhs]
  (if (or (= lhs :block)
          (= rhs :block))
    0
    (+ lhs rhs)))

(defn sum [dice]
  (reduce
    (partial merge-with +block)
    dice))

(defn damage [totals]
  (let [v (get totals '* 0)]
    (if (= v :block) 0 v)))

(def rank (juxt damage :range '⚡))

(defn rolls [dice]
  (map sum (apply combo/cartesian-product dice)))

(defn combos [dice]
  (let [odds (frequencies (rolls dice))
        total (reduce + (vals odds))]
    (->> (for [[roll cnt] odds]
           [roll (/ cnt total)])
         (sort-by (juxt second (comp rank first))))))

(defn avg-all [all]
  (let [cnts (sum all)]
    (->> (for [[k v] cnts]
           [k (double (/ v (count all)))])
         (into {}))))
