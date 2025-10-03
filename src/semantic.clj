(ns semantic
  (:require [clojure.spec.alpha :as s]))

(defonce registry (atom {}))
