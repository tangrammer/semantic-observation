(defproject com.github.tangrammer/semantic-observation "0.1.0-SNAPSHOT"
  :description "decomplecting clojure.spec, from validation to self-discoverable semantic services"
  :url "https://github.com/tangrammer/semantic-observation"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :profiles {:dev {:source-paths   ["dev/src"]
                   :repl-options   {:init-ns dev}
                   :dependencies [[org.clojure/tools.namespace "1.4.4"]]}})
