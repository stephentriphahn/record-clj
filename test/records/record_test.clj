(ns records.record-test
  (:require [clojure.test :refer [testing is deftest]]
            [records.record :as record]))

(deftest lens-fn-test
  (let [record {"FirstName" "Joe"
                "LastName" "Smith"
                "Gender" "Male"
                "DateOfBirth" "1/1/2000"
                "FavoriteColor" "Blue"}]

    (testing "last-name"
      (is (= "Smith" (record/last-name record))
          "should return record's last name field"))

    (testing "date-of-birth"
      (is (= "1/1/2000" (record/date-of-birth record))))

    (testing "gender"
      (is (= "Male" (record/gender record))))))

(deftest mem-db-test

  (let [db (record/connect "memory")]

    (testing "Multiple records"

      (record/save db [1 2 3])

      (is (= (count (record/get-all db)) 3)
          "All items in collection added to mem db")

      (record/save db [1])

      (is (= (count (record/get-all db)) 3)
          "Adding an existing item does not duplicate it"))))
