(ns records.record-test
  (:require [clojure.test :refer [testing is deftest]]
            [records.record :as record]))

(deftest lens-fn-test
  (testing "valid record"
    (let [record {:first-name "Joe"
                  :last-name "Smith"
                  :gender "Male"
                  :date-of-birth "1/1/2000"
                  :favorite-color "Blue"}]

      (testing "last-name"
        (is (= "Smith" (record/last-name record))
            "should return record's last name field"))

      (testing "date-of-birth"
        (is (= "1/1/2000" (record/date-of-birth record))))

      (testing "gender"
        (is (= "Male" (record/gender record))))))

  (testing"invalid record"
    (let [invalid-rec {:middle-name "Jeffrey"
                       :date-of-birth "31/10/19"}]
      (is (thrown? java.lang.AssertionError (record/last-name invalid-rec)))
      (is (thrown? java.lang.AssertionError (record/date-of-birth invalid-rec)))
      (is (thrown? java.lang.AssertionError (record/gender invalid-rec))))))

(deftest mem-db-test

  (let [db (record/connect "memory")]

    (testing "Multiple records"

      (record/save db [1 2 3])

      (is (= (count (record/get-all db)) 3)
          "All items in collection added to mem db")

      (record/save db [1])

      (is (= (count (record/get-all db)) 3)
          "Adding an existing item does not duplicate it"))))
