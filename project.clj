(defproject simple-search "0.0.1-SNAPSHOT"
  :description "Cool new project to do things and stuff"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [me.raynes/fs "1.4.6"]
		 [com.climate/claypoole "1.1.3"]]
  :profiles {:dev {:dependencies [[midje "1.5.1"]]}}
  :jvm-opts ["-XX:+UseParallelGC" "-XX:ParallelGCThreads=48"]
  :main simple-search.experiment)

