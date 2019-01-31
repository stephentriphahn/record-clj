(ns records.core
  (:require [clojure.spec.alpha :as spec]
            [clojure.string :as str]
            [ring.adapter.jetty :as jetty]
            [records.parse :as parse]
            [records.record :as record]
            [records.render :as render]
            [records.web.routes :as routes])
  (:import (java.io Reader FileNotFoundException))
  (:gen-class))

(defn print-vals!
  [label data]
  (let [results (map (comp #(str/join " " %) vals) data)]
    (when (not-empty results)
      (println label)
      (run! println results)
      (print "\n"))))

(def print-by-gender (partial print-vals! "Sorted by Gender"))
(def print-by-lastname (partial print-vals! "Sorted by Last Name"))
(def print-by-dob (partial print-vals! "Sorted by Date of Birth"))

(defn save!
  [db data]
  (record/save db data))

(defn- try-parse
  [path]
  (try
    (parse/parse path)
    (catch AssertionError err
      (println "Invalid File: " path)
      (println "Please make sure your file uses a common delimiter with"
               "even rows. This file's data was not saved.\n"))
    (catch FileNotFoundException fe
      (println "File not found: " path)
      (println "Please make sure it exists and try again.\n"))))

(defn -main
  [& args]
  (let [db (record/connect "memory")
        _ (run! (comp (partial save! db) try-parse) args)
        data (record/get-all db)]

    (print-by-gender (render/data-by-gender data))
    (print-by-lastname (render/data-by-lastname data))
    (print-by-dob (render/data-by-dob data))

    (println "starting server...")
    (jetty/run-jetty routes/app {:port 8081})))
