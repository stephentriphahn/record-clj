(ns records.parse
  (:require [clojure.string :as str])
  (:import (java.io BufferedReader)))

(defn- split-trim
  [s dillemeter]
  (map str/trim (str/split s dillemeter)))

(defn line->map
  [fields dillimeter line]
  (zipmap (split-trim fields dillimeter)
          (split-trim line dillimeter)))

(defn parse
  "Takes the string contents of a file and returns a vector of maps"
  [file-reader dillimeter]
  (let [[fields & data] (line-seq file-reader)] ;; lazy file read by line
    (map (partial line->map fields dillimeter) data)))

;;todo configure these, not hard coded mapping
(def ext->delimiter {"ssv" #" "
                     "csv" #","
                     "psv" #"|"})

(defn- delimiter
  [path]
  (ext->delimiter (peek (str/split path #"\."))))

(defn parse-reader
  "Given a reader and specific delimiter, parses and returns the data as a seq of maps."
  [rdr-fn path]
  (let [rdr (clojure.java.io/reader path)]
    (rdr-fn rdr)
    (parse rdr (delimiter path))))
