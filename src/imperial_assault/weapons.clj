(ns imperial-assault.weapons
  (:require [imperial-assault.dice :as dice]
            [clojure.math.combinatorics :as combo]))

(def vintage-blaster
  {:dice     [dice/green dice/green]
   :surge    ['{* 1}
              '{:range 1}]
   :baseline {}})

(def sporting-blaster
  {:dice     [dice/blue dice/yellow dice/yellow]
   :surge    ['{* 1}
              '{:pierce 2}
              '{:range 2}
              '{:stun 1}]
   :baseline {}})

(def plasteel-staff
  {:dice     [dice/green dice/yellow]
   :surge    ['{* 1}
              '{:stun 1}]
   :baseline {}})

(def force-pike
  {:dice     [dice/red dice/yellow dice/yellow]
   :surge    ['{* 1}
              '{* 1}
              '{:stun 1}]
   :baseline {}})

