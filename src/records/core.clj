(ns records.core
  (:require [clojure.string :as str]
            [records.parse :as parse]
            [records.render :as render])
  (:import (java.io Reader))
  (:gen-class))


(defn -main
  "Opens a file reader and prints out the parsed data...for now."
  [& args]
  ;;fixme- storing readers to manually close, with-open closes too early.
  (let [open-readers (atom [])
        data (map (partial parse/parse-file #(swap! open-readers conj %)) args)
        combined-data (apply concat data)]
    (run! println (render/render-by "gender" combined-data))
    (println "\n\n")
    (run! println (render/render-by "LastName" combined-data))
    (println "\n\n")
    (run! println (render/render-by "dateofbirth" combined-data))
    (run! #(.close ^Reader %) @open-readers)))
