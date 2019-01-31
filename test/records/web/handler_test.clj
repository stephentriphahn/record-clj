(ns records.web.handler-test
  (:require [clojure.test :refer [deftest testing is]]
            [records.web.handler :as handler])
  (:import (java.io ByteArrayInputStream)))


(defn string->stream
  ([s] (string->stream s "UTF-8"))
  ([s encoding]
   (-> s
     (.getBytes encoding)
     (ByteArrayInputStream.))))

(deftest add-record-handler-test
  (testing "Add valid record"
    (let [test-str "LastName, FirstName, Gender, FavoriteColor, DateOfBirth\n
         Smith, John, Male, Blue, 12/12/1966"
          test-input-stream (string->stream test-str)
          res (handler/add-record test-input-stream)]
      (is (= 201 (:status res)) "Response status should be 200")
      (is (get-in res [:headers "Location"])))

    (testing "Invalid record DOB"
      (let [test-str "LastName, FirstName, Gender, FavoriteColor, DateOfBirth\n
                    Smith, John, Male, Blue, 15/12/66"
            test-input-stream (string->stream test-str)
            res (handler/add-record test-input-stream)]
        (is (= 400 (:status res)) "Response status should be 400")
        (is (= "text/plain" (get-in res [:headers "Content-Type"])))))))
