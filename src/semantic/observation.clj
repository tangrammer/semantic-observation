(ns semantic.observation
  (:require [clojure.spec.alpha :as s]
            [semantic :as semantic]
            [semantic.observation.type :as observation.type]))

(defn def
  "adds to the registry an observation if it conforms the observation-type"
  ^{:pre [(qualified-keyword? semantic-id)
          (qualified-keyword? observation-type)
          (map? observation)]}
  [semantic-id observation-type observation]
  (let [attributes-kw (::observation.type/attributes (observation-type @observation.type/registry))]
    (mapv #(when-not (s/valid? % (% observation))
             (throw (ex-info (format "Invalid data observed: %s" (s/explain-str % (% observation)))
                             {:semantic.observation/type observation-type
                              :semantic/observation observation}))) attributes-kw)
    (swap! semantic/registry update semantic-id merge observation)
    (swap! observation.type/registry update-in [observation-type ::observation.type/implementers] conj semantic-id)
    {:semantic/id semantic-id
     :semantic/observation observation
     :semantic.observation/type observation-type}))
