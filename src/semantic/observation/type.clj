(ns semantic.observation.type
  (:require [clojure.spec.alpha :as s]))

(defonce registry (atom {}))

(defn def
  "adds to the registry an observation type if all of the attributes exists on clojure.spec/registry "
    ^{:pre [(qualified-keyword? kw)
            (every? true? (map  qualified-keyword? spec-kw-col))]}
  [kw spec-kw-col]
  (mapv #(assert (% (s/registry)) (format "%s is not a spec yet, do you forget to s/def or to load/eval firstly that other ns?" (pr-str %))) spec-kw-col)
  (swap! registry assoc kw {::attributes spec-kw-col
                            ::implementers #{}}))






