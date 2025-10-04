(ns semantic.observation.type.catalog
  (:require [clojure.spec.alpha :as s]
            [semantic.observation.type :as observation.type]))

(s/def :documentation/content string?)

(observation.type/def :semantic.observation.type/docs [:documentation/content])

