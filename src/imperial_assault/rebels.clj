(ns imperial-assault.rebels
  (:require [imperial-assault.dice :as dice]))

(def chewbacca
  {:dice     [dice/blue dice/red dice/yellow]
   :surge    ['{* 2}
              '{:stun 1}
              '{:range 2}]
   :baseline {}})
