(ns semantic.observation-test
  (:require [clojure.test :refer :all]
            [semantic :as semantic]
            [clojure.spec.alpha :as s]
            [semantic.observation.type :as observation.type]
            [semantic.observation.type.catalog]))



(deftest observation-test
  (testing "define specs, observation type and observation"

    (s/def ::bar string?)
    (s/def ::zoo int?)

    (observation.type/register! ::yikes [::bar ::zoo]) 

    (is (= (semantic/observe! ::foo ::yikes {::bar "juan" ::zoo 14})
           {:semantic/subject :semantic.observation-test/foo,
            :semantic/observation
            #:semantic.observation-test{:bar "juan", :zoo 14},
            :semantic.observation/type :semantic.observation-test/yikes}))))

(deftest catalog-test
  (testing "define specs, observation type and observation"

    (is (= (semantic/observe! ::other-id :semantic.observation.type/docs 
             {:documentation/content "Hello docs!"})
           {:semantic/subject ::other-id,
            :semantic/observation {:documentation/content "Hello docs!"},
            :semantic.observation/type :semantic.observation.type/docs}))
    (is (= (semantic/observe! ::cool-id :semantic.observation.type/docs
             {:documentation/content "Cool stuff!"})
           {:semantic/subject ::cool-id,
            :semantic/observation {:documentation/content "Cool stuff!"},
            :semantic.observation/type :semantic.observation.type/docs}))
    (is (= (semantic.observation.type/implementers :semantic.observation.type/docs)
           #{:semantic.observation-test/cool-id
	    :semantic.observation-test/other-id}))))
