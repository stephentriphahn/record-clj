(ns records.core
  (:require [records.parse :as parse])
  (:gen-class))

(defn -main
  "Opens a file reader and prints out the parsed data...for now."
  [& args]
  (with-open [rdr (clojure.java.io/reader "resources/records/sample.csv")]
    (let [data (parse/parse-reader rdr #",")]
      (run! println data))))
