(ns semantic.assertion.type.catalog
  (:require [clojure.spec.alpha :as s]
            [semantic.assertion.type :as assertion.type]))

(s/def :documentation/content string?)

(assertion.type/register! :semantic.assertion.type/docs [:documentation/content])

