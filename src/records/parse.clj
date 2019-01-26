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

(defn parse-reader
  "Given a reader and specific delimeter, parses and returns the data as a seq of maps."
  [rdr delimeter]
  (parse rdr delimeter))
