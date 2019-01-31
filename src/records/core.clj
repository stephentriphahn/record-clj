(ns records.core
  (:require [clojure.string :as str]
            [ring.adapter.jetty :as jetty]
            [records.parse :as parse]
            [records.record :as record]
            [records.render :as render]
            [records.web.routes :as routes]
            [clojure.spec.alpha :as spec])
  (:import (java.io Reader))
  (:gen-class))

(defn print-vals
  [label data]
  (let [results (map (comp #(str/join " " %) vals) data)]
    (println label)
    (run! println results)))

(def print-by-gender (partial print-vals "Sorted by Gender"))
(def print-by-lastname (partial print-vals "Sorted by Last Name"))
(def print-by-dob (partial print-vals "Sorted by Date of Birth"))

(defn save!
  [db data]
  (record/save db data))

(defn -main
  [& args]
  (let [db (record/connect "memory")
        _ (run! (comp (partial save! db) parse/parse) args)
        data (record/get-all db)]

    (println (spec/valid? :records.record/record
                          {:last-name "Smith"
                           :first-name "John"
                           :gender "Male"
                           :favorite-color "Blue"
                           :date-of-birth "10/03/1988"}))
    (print-by-gender (render/data-by-gender data))
    (print "\n")
    (print-by-lastname (render/data-by-lastname data))
    (print "\n")
    (print-by-dob (render/data-by-dob data))

    (println "starting server...")
    #_(jetty/run-jetty routes/app {:port 8081})))
