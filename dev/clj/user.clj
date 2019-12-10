(ns user
  (:require [integrant.repl :refer [clear go halt prep init reset reset-all]]))

;; (integrant.repl/set-prep! (constantly {::foo {:example? true}}))
