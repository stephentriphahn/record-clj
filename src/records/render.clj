(ns records.render
  (:require [clojure.string :as str]))

(defmulti render (fn [order-by _]
                   (str/lower-case order-by)))  ;; lowercase for dispatching

(defmethod render "gender"
  [_ data]
  (group-by #(get % "Gender") data))
