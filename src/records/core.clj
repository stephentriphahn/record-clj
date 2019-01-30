(ns records.core
  (:require [clojure.string :as str]
            [records.parse :as parse]
            [records.record :as record]
            [records.render :as render])
  (:import (java.io Reader))
  (:gen-class))

(def print-vals (comp println (partial str/join " ") vals))

(defn print-vals
  [data]
  (println data))

#_(defn- print-vals
  [])

#_(defn -main
  "Opens a file reader and prints out the parsed data...for now."
  [& args]
  ;;fixme- storing readers to manually close, with-open closes too early.
  (let [open-readers (atom [])
        data (map (partial parse/parse-file #(swap! open-readers conj %)) args)
        combined-data (apply concat data)]
    (println "Sorted by gender, then Last Name ascending")
    (run! print-vals (render/data-by-gender combined-data))
    (print "\n\n")

    (println "Sorted by LastName Descending")
    (print "\n")
    (run! print-vals (render/data-by-lastname combined-data))
    (print "\n\n")

    (print "Sorted by Date of birth, ascending.")
    (print "\n")
    (run! print-vals (render/data-by-dob combined-data))

    (run! #(.close ^Reader %) @open-readers)))

(defn save!
  [db data]
  (record/save db data))

(defn -main
  [& args]
  (let [data (mapcat #(parse/from-path %) args)]
    (run! print-vals data)))
