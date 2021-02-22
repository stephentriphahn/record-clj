(ns records.render
  (:require [clojure.string :as str]
            [records.record :as record])
  (:import (java.text SimpleDateFormat)
           (java.time.format DateTimeFormatter)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  Constants

(def formatter (SimpleDateFormat. "mm/dd/yyyy"))

(def descending #(compare %2 %1))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  Implementation

(defn- concat-genders
  "Ensures that females are first and males are second in grouped, sorted data."
  [{:strs [Male Female] :as m}]
  (concat Female Male))

(defn- sort-genders
  "Given grouped data by gender, sorts each of the map values by last name"
  [data]
  (-> data
      (update "Male" (partial sort-by record/last-name))
      (update "Female" (partial sort-by record/last-name))
      concat-genders))

(defn data-by-gender
  [data]
  (->> data
       (group-by record/email)
       sort-genders))

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
