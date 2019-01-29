(ns records.parse
  (:require [clojure.string :as str])
  (:import (java.io BufferedReader Reader)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  Line parsing

(defn- split-trim
  [s delimiter]
  (map str/trim (str/split s delimiter)))

(defn line->map
  [fields delimiter line]
  (zipmap (split-trim fields delimiter)
          (split-trim line delimiter)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  File parsing

(defn parse
  "Takes the string contents of a file and returns a vector of maps"
  [file-reader delimiter]
  {:pre [(instance? Reader file-reader)]}
  (let [[fields & data-lines] (line-seq file-reader)] ;; lazy file read by line
    (map (partial line->map fields delimiter) data-lines)))

;;todo configure these, not hard coded mapping
(def ext->delimiter {"ssv" #" "
                     "csv" #","
                     "psv" #"\|"})

(defn- delimiter
  [path]
  (ext->delimiter
    (peek (str/split path #"\.")))) ;; peek is more efficient than last on vec

(defn parse-file
  "Given a file path and specific delimiter, parses and returns the data as a
   seq of maps."
  [rdr-fn path]
  (let [rdr (clojure.java.io/reader path)]
    (rdr-fn rdr)
    (parse rdr (delimiter path))))
