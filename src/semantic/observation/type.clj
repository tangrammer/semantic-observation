(ns semantic.observation.type
  (:require [clojure.spec.alpha :as s]
            [semantic :as sem]))

(defn def [kw spec-kws]
  (mapv #(assert (% (s/registry)) (format "%s is not a spec yet, do you forget to s/def or to load/eval firstly that other ns?" (pr-str %))) spec-kws)
  (swap! sem/registry assoc kw spec-kws))



