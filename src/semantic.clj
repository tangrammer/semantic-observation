(ns semantic
  (:require [clojure.spec.alpha :as s]
            [semantic.observation.type :as observation.type]))

(defonce ^:private registry (ref {}))

(defn observe!
  "adds to the registry an observation if it conforms the observation-type"
  ^{:pre [(qualified-keyword? subject-id) (qualified-keyword? observation-type) (map? observation)]}
  [subject-id observation-type observation]
  (let [attributes-kw (observation.type/attributes observation-type)]
    (mapv #(when-not (s/valid? % (% observation))
             (throw (ex-info (format "Invalid data observed: %s" (s/explain-str % (% observation)))
                             {:semantic.observation/type observation-type
                              :semantic/observation observation}))) attributes-kw)

    (dosync
     (alter registry update subject-id merge observation)
     (alter observation.type/registry update-in [observation-type ::observation.type/implementers] conj subject-id))
    {:semantic/subject subject-id
     :semantic/observation observation
     :semantic.observation/type observation-type}))


