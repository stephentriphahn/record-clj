(ns records.parse
  (:require [camel-snake-kebab.core :as csk]
            [clojure.string :as str])
  (:import (java.io BufferedReader Reader InputStream)
           (clojure.lang ExceptionInfo)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  Line parsing

(defn- split-trim
  [s delimiter]
  (map str/trim (str/split s delimiter)))

(defn line->map
  [fields line]
  {:pre [(= (count fields) (count line))]}
  (zipmap fields line))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  File parsing

(defn find-delimiter
  [fields]
  (cond
    (re-find #"\|" fields) #"\|"
    (re-find #"," fields) #","
    :else #" "))

(defn- keywordize-fields
  [fields d]
  (map csk/->kebab-case-keyword (split-trim fields d)))

(defn parse-reader
  "Takes the string contents of a file and returns a vector of maps"
  [file-reader]
  (let [[fields & data-lines] (line-seq file-reader)
        del (find-delimiter fields) ;; lazy file read by line
        parsed-fields (keywordize-fields fields del)]
    (map
      (comp #(line->map parsed-fields %) #(split-trim % del))
      data-lines)))

(defn parse
  [source]
  {:pre [(or (string? source) (instance? InputStream source))]}
  (with-open [rdr (clojure.java.io/reader source)]
    (doall (parse-reader rdr))))

