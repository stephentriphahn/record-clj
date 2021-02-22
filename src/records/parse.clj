(ns records.parse
  (:require [camel-snake-kebab.core :as csk]
            [clojure.string :as str]
            [clojure.spec.alpha :as spec])
  (:import (java.io  InputStream)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  Line parsing

(defn- split-trim
  [s delimiter]
  (map str/trim (str/split s delimiter)))

(defn line->map
  [fields line]
  {:pre [(= (count fields) (count line))]
   :post [(spec/valid? :records.record/record %)]}
  (zipmap fields line))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  File parsing

(defn find-delimiter
  "Given a string, finds which delimiter is being used.  Defaults to space,
   so any malformed data should fail during validation step of parsing."
  [line]
  (cond
    (re-find #"\|" line) #"\|"
    (re-find #"," line) #","
    :else #" "))

(defn- keywordize-fields
  "Turns the fields into keywords to leverage spec validation."
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
  "Given a source (file path as a string, or data as an input stream), opens
   a reader, parses the data, and closes the reader."
  [source]
  {:pre [(or (string? source) (instance? InputStream source))]}
  (with-open [rdr (clojure.java.io/reader source)]
    (doall (parse-reader rdr))))

