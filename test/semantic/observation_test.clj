(ns semantic.observation-test
  (:require [clojure.test :refer :all]
            [semantic.observation :as observation]
            [clojure.spec.alpha :as s]
            [semantic.observation.type :as observation.type]))

(s/def ::bar string?)
(s/def ::zoo int?)


(deftest observation-test
  (testing "define specs, observation type and observation"


    (observation.type/def ::yikes [::bar ::zoo])

    (semantic.observation/def ::foo ::yikes {::bar "juan" ::zoo 12})

    (is (= (semantic.observation/def ::foo ::yikes {::bar "juan" ::zoo 14})
           {:semantic/id :semantic.observation-test/foo,
            :semantic/observation
            #:semantic.observation-test{:bar "juan", :zoo 14},
            :semantic.observation/type :semantic.observation-test/yikes}))))
