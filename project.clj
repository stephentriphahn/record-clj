(defproject records "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.10.0"]

                 [camel-snake-kebab "0.4.2"]
                 [compojure "1.6.1"]
                 [ring/ring-core "1.7.0"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [ring/ring-json "0.4.0"]
                 [ring-logger "1.0.1"]]

  :main ^:skip-aot records.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
