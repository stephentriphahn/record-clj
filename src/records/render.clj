(ns records.render
  (:require [clojure.string :as str])
  (:import (java.text SimpleDateFormat)
           (java.time.format DateTimeFormatter)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  Record lens functions

(defn last-name
  [record]
  (get record "LastName"))

(defn date-of-birth
  [record]
  (get record "DateOfBirth"))

(defn gender
  [record]
  (get record "Gender"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  Constants

(def formatter (SimpleDateFormat. "mm/dd/yyyy"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  Implementation

(defmulti render-by (fn [order-by _] ;; lowercase for dispatching
                      (str/lower-case order-by)))

(defn- concat-genders
  [{:strs [Male Female] :as m}]
  (concat Female Male))

(defn- sort-genders
  [data]
  (-> data
      (update "Male" (partial sort-by last-name))
      (update "Female" (partial sort-by last-name))
      concat-genders))

(defmethod render-by "gender"
  [_ data]
  (->> data
       (group-by gender)
       sort-genders))

(defmethod render-by "lastname"
  [_ data]
  (sort-by last-name #(compare %2 %1) data))

(defmethod render-by "dateofbirth"
  [_ data]
  (sort-by (comp #(.parse formatter %) date-of-birth) data))
