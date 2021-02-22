(ns records.render
  (:require [records.record :as record])
  (:import (java.text SimpleDateFormat)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  Constants

(def formatter (SimpleDateFormat. "mm/dd/yyyy"))

(def descending #(compare %2 %1))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  Implementation

(defn data-by-email
  [data]
  (->> data
       (group-by record/email)
       (into (sorted-map-by descending))
       (mapcat #(sort-by record/last-name (val %1)))))

(defn data-by-lastname
  [data]
  (sort-by record/last-name descending data))

(defn data-by-dob
  [data]
  (sort-by (comp #(.parse formatter %) record/date-of-birth) data))
