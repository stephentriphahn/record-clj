(ns records.web.handler
  (:require [clojure.java.io :as io]
            [records.record :as record]
            [records.parse :as parse]
            [ring.util.response :as response]
            [records.render :as render]))

(defn add-record
  [body]
  (let [db (record/connect "memory")
        data (->> body io/reader parse/parse)]
    (when data
      (record/save db data)
      (response/created ""))))

(defn get
  [sort-fn]
  (let [db (record/connect "memory")]
    (or
      (some-> db
              (record/get-all)
              (sort-fn)
              (response/response)
              (response/content-type "application/json"))
      (response/bad-request "No Data Found"))))
