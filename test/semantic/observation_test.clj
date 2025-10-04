(ns semantic.observation-test
  (:require [clojure.test :refer :all]
            [semantic.observation :as observation]
            [clojure.spec.alpha :as s]
            [semantic.observation.type :as observation.type]
            [semantic.observation.type.catalog]))



(deftest observation-test
  (testing "define specs, observation type and observation"

    (s/def ::bar string?)
    (s/def ::zoo int?)

    (observation.type/def ::yikes [::bar ::zoo]) 

    (is (= (semantic.observation/def ::foo ::yikes {::bar "juan" ::zoo 14})
           {:semantic/id :semantic.observation-test/foo,
            :semantic/observation
            #:semantic.observation-test{:bar "juan", :zoo 14},
            :semantic.observation/type :semantic.observation-test/yikes}))))

(deftest catalog-test
  (testing "define specs, observation type and observation"

    (is (= (semantic.observation/def ::other-id :semantic.observation.type/docs
             {:documentation/content "Hello docs!"})
           {:semantic/id ::other-id,
            :semantic/observation {:documentation/content "Hello docs!"},
            :semantic.observation/type :semantic.observation.type/docs}))))
