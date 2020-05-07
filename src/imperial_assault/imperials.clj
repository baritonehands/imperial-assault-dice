(ns imperial-assault.imperials
  (:require [imperial-assault.dice :as dice]))

(def e-web-engineer
  {:dice     [dice/blue dice/red dice/yellow]
   :surge    ['{* 1}
              '{:recover 2}]
   :baseline {:range 3}})
