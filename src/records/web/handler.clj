(ns records.web.handler
  (:require [records.record :as record]
            [records.parse :as parse]
            [ring.util.response :as response]))

(def ^:Private bad-request-msg
  (str "Failed to parse data from your request.  "
        "Please make sure the data is in the proper format."))

(defn add-record
  "Tries to parse and add a record from the body of the HTTP request.
   Assumes the body is a text representation of a parsable file, including
   the fields on the first line, and the data on the second line. Returns a
   valid HTTP Ring response."
  [body]
  (let [db (record/connect "memory")]
    (try
      (record/save db (parse/parse body))
      (response/created "path/to/created/resource")

      (catch AssertionError err
        (-> bad-request-msg
          (response/bad-request)
          (response/content-type "text/plain"))))))

(defn get-records-with
  "Gets all records from the data store, and calls the supplied function on
   it.  Returns a valid HTTP Ring response."
  [sort-fn]
  (let [db (record/connect "memory")]
    (-> db
      (record/get-all)
      (sort-fn)
      (response/response)
      (response/content-type "application/json"))))
