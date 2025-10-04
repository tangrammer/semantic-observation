(ns semantic.assertion-test
  (:require [clojure.spec.alpha :as s]
            [clojure.test :refer :all]
            [semantic.assertion :as assertion]
            [semantic.assertion.type :as assertion.type]
            [semantic.assertion.type.catalog]))



(deftest assertion-test
  (testing "define specs, assertion type and assertion"

    (s/def ::bar string?)
    (s/def ::zoo int?)

    (assertion.type/register! ::yikes [::bar ::zoo]) 

    (is (= (assertion/register! ::foo ::yikes {::bar "juan" ::zoo 14})
           {:semantic/subject :semantic.assertion-test/foo,
            :semantic/assertion
            #:semantic.assertion-test{:bar "juan", :zoo 14},
            :semantic.assertion/type :semantic.assertion-test/yikes}))))

(deftest catalog-test
  (testing "define specs, assertion type and assertions"

    (is (= (assertion/register! ::other-id :semantic.assertion.type/docs 
             {:documentation/content "Hello docs!"})
           {:semantic/subject ::other-id,
            :semantic/assertion {:documentation/content "Hello docs!"},
            :semantic.assertion/type :semantic.assertion.type/docs}))
    (is (= (assertion/register! ::cool-id :semantic.assertion.type/docs
             {:documentation/content "Cool stuff!"})
           {:semantic/subject ::cool-id,
            :semantic/assertion {:documentation/content "Cool stuff!"},
            :semantic.assertion/type :semantic.assertion.type/docs}))

    (is (= (assertion/fetch ::cool-id :semantic.assertion.type/docs)
           {:documentation/content "Cool stuff!"}))
    
    (is (= (assertion.type/subjects :semantic.assertion.type/docs)
           #{:semantic.assertion-test/cool-id
	    :semantic.assertion-test/other-id}))))
