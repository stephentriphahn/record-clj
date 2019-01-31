(ns records.web.handler
  (:require [clojure.java.io :as io]
            [records.record :as record]
            [records.parse :as parse]
            [ring.util.response :as response]
            [records.render :as render]))

(defn add-record
  [body]
  (let [db (record/connect "memory")]
    (try
      (println (parse/parse body))
      (record/save db (parse/parse body))
      (response/created "path/to/created/resource")

      (catch AssertionError err
        (response/bad-request "Failed to parse data from your request.
        Please make sure the data is in the proper format.")))))

(defn get-records-with
  [sort-fn]
  (let [db (record/connect "memory")]
    (-> db
      (record/get-all)
      (sort-fn)
      (response/response)
      (response/content-type "application/json"))))
