(ns semantic.observation-test
  (:require [clojure.spec.alpha :as s]
            [clojure.test :refer :all]
            [semantic.observation :as observation]
            [semantic.observation.type :as observation.type]
            [semantic.observation.type.catalog]))



(deftest observation-test
  (testing "define specs, observation type and observation"

    (s/def ::bar string?)
    (s/def ::zoo int?)

    (observation.type/register! ::yikes [::bar ::zoo]) 

    (is (= (observation/register! ::foo ::yikes {::bar "juan" ::zoo 14})
           {:semantic.observation/subject :semantic.observation-test/foo,
            :semantic.observation/observation
            #:semantic.observation-test{:bar "juan", :zoo 14},
            :semantic.observation/type :semantic.observation-test/yikes}))))

(deftest catalog-test
  (testing "define specs, observation type and observation"

    (is (= (observation/register! ::other-id :semantic.observation.type/docs 
             {:documentation/content "Hello docs!"})
           {:semantic.observation/subject ::other-id,
            :semantic.observation/observation {:documentation/content "Hello docs!"},
            :semantic.observation/type :semantic.observation.type/docs}))
    (is (= (observation/register! ::cool-id :semantic.observation.type/docs
             {:documentation/content "Cool stuff!"})
           {:semantic.observation/subject ::cool-id,
            :semantic.observation/observation {:documentation/content "Cool stuff!"},
            :semantic.observation/type :semantic.observation.type/docs}))

    (is (= (observation/fetch ::cool-id :semantic.observation.type/docs)
           {:documentation/content "Cool stuff!"}))
    
    (is (= (semantic.observation.type/implementers :semantic.observation.type/docs)
           #{:semantic.observation-test/cool-id
	    :semantic.observation-test/other-id}))))
