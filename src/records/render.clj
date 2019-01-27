(ns records.render
  (:require [clojure.string :as str]))

(defmulti render-by (fn [order-by _]
                   (str/lower-case order-by)))  ;; lowercase for dispatching

(def sort-by-lastname (partial sort-by #(get % "LastName")))

(defn- sort-genders
  [data]
  (-> data
      (update "Male" sort-by-lastname)
      (update "Female" sort-by-lastname)))

(defmethod render-by "gender"
  [_ data]
  (->> data
       (group-by #(get % "Gender"))
       sort-genders
       vals
       (apply concat)))

(defmethod render-by "lastname"
  [_ data]
  (sort-by #(get % "LastName") #(compare %2 %1) data))



(defmethod render-by "dateofbirth"
  [_ data]
  ;;todo sort by birthday
  data)
