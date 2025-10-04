(ns semantic.assertion.type
  (:require [clojure.spec.alpha :as s]))

(defonce registry (ref {}))

(defn register!
  "adds to the registry an assertion type if all of the attributes exists on clojure.spec/registry "
  ^{:pre [(qualified-keyword? type-kw)
          (every? true? (map  qualified-keyword? spec-kw-col))]}
  [type-kw spec-kw-col]
  (mapv #(assert (% (s/registry)) (format "%s is not a spec yet, do you forget to s/def or to load/eval firstly that other ns?" (pr-str %))) spec-kw-col)
  (dosync (alter registry assoc type-kw {::attributes spec-kw-col
                                         ::subjects #{}}))
  type-kw)

(defn subjects
  ^{:pre [(qualified-keyword? assertion-type)]}
  [assertion-type]
  (::subjects (assertion-type @registry)))

(defn attributes
  ^{:pre [(qualified-keyword? assertion-type)]}
  [assertion-type]
  (::attributes (assertion-type @registry)))



