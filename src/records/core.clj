(ns records.core
  (:require [clojure.string :as str]
            [records.parse :as parse]
            [records.render :as render])
  (:import (java.io Reader))
  (:gen-class))

(def print-vals (comp println (partial str/join " ") vals))

(defn -main
  "Opens a file reader and prints out the parsed data...for now."
  [& args]
  ;;fixme- storing readers to manually close, with-open closes too early.
  (let [open-readers (atom [])
        data (map (partial parse/parse-file #(swap! open-readers conj %)) args)
        combined-data (apply concat data)]
    (println "Sorted by gender, then Last Name ascending")
    (run! print-vals (render/render-by "gender" combined-data))
    (println "\n\n")
    (println "Sorted by LastName Descending")
    (println "\n")
    (run! print-vals (render/render-by "LastName" combined-data))
    (println "\n\n")
    (println "Sorted by Date of birth, ascending.")
    (println "\n")
    (run! print-vals (render/render-by "dateofbirth" combined-data))
    (run! #(.close ^Reader %) @open-readers)))
