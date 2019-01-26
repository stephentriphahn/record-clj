(ns records.core
  (:require [clojure.string :as str]
            [records.parse :as parse])
  (:import (java.io Reader))
  (:gen-class))

(defn -main
  "Opens a file reader and prints out the parsed data...for now."
  [& args]
  ;;fixme- storing readers to manually close, with-open closes too early.
  (let [rdr-data (atom [])
        data (map (partial parse/parse-reader #(swap! rdr-data conj %)) args)]
    ;;fixme- parse args with tools.cli
    (run! println (apply concat data))
    (run! #(.close %) @rdr-data)))
