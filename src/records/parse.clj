(ns records.parse
  (:require [clojure.string :as str])
  (:import (java.io BufferedReader Reader InputStream)
           (clojure.lang ExceptionInfo)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  Line parsing

(defn- split-trim
  [s delimiter]
  (map str/trim (str/split s delimiter)))

(defn line->map
  [fields delimiter line]
  (let [fs (split-trim fields delimiter)
        l (split-trim line delimiter)]
    (if (= (count fs) (count l))
      (zipmap fs l)
      (throw
        (ex-info "Invalid line" {:line line :delimiter l})))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  File parsing

(defn find-delimiter
  [fields]
  (cond
    (re-find #"\|" fields) #"\|"
    (re-find #"," fields) #","
    :else #" "))

(defn parse-reader
  "Takes the string contents of a file and returns a vector of maps"
  [file-reader]
  (let [[fields & data-lines] (line-seq file-reader)
        delimiter (find-delimiter fields)] ;; lazy file read by line
    (map (partial line->map fields delimiter) data-lines)))

(defn parse
  [source]
  {:pre [(or (string? source) (instance? InputStream source))]}
  (with-open [rdr (clojure.java.io/reader source)]
    (try
      (doall (parse-reader rdr))
      (catch ExceptionInfo e
        (ex-data e)))))

