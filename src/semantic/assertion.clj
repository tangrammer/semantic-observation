(ns semantic.assertion
  (:require [clojure.spec.alpha :as s]
            [semantic.assertion.type :as assertion.type]))

(defonce ^:private registry (ref {}))

(defn register!
  "adds to the registry an assertion if it conforms the assertion-type"
  ^{:pre [(qualified-keyword? subject-id) (qualified-keyword? assertion-type) (map? assertion)]}
  [subject-id assertion-type assertion]
  (let [attributes-kw (assertion.type/attributes assertion-type)]
    (mapv #(when-not (s/valid? % (% assertion))
             (throw (ex-info (format "Invalid data asserted: %s" (s/explain-str % (% assertion)))
                             {:semantic.assertion/type assertion-type
                              :semantic/assertion assertion}))) attributes-kw)

    (dosync
     (alter registry update subject-id merge assertion)
     (alter assertion.type/registry update-in [assertion-type ::assertion.type/subjects] conj subject-id))
    {:semantic/subject subject-id
     :semantic/assertion assertion
     :semantic.assertion/type assertion-type}))

(defn fetch
  ^{:pre [(qualified-keyword? subject-id) (qualified-keyword? assertion-type) ]}
  [subject-id assertion-type]
  (-> @registry subject-id (select-keys (assertion.type/attributes assertion-type))))

