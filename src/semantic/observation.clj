(ns semantic.observation
  (:require [clojure.spec.alpha :as s]
            [semantic.observation.type :as observation.type]
            [semantic :as sem]))

(defn def [semantic-id observation-type-kw observation]
  (let [attributes-kw (observation-type-kw @sem/registry)]
    (mapv #(when-not (s/valid? % (% observation))
             (throw (ex-info (format "Invalid data observed: %s" (s/explain-str % (% observation)))
                             {:semantic.observation/type observation-type-kw
                              :semantic/observation observation}))) attributes-kw)
    (swap! sem/registry update semantic-id merge observation)
    {:semantic/id semantic-id
     :semantic/observation observation
     :semantic.observation/type observation-type-kw}))

(s/def ::bar string?)
(s/def ::zoo int?)

(observation.type/def ::yikes [::bar ::zoo])

(semantic.observation/def ::foo ::yikes {::bar "juan" ::zoo 12})

