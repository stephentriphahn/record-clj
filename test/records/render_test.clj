(ns records.render-test
  (:require [clojure.test :refer [deftest is testing]]
            [records.render :as render]))

(deftest data-by-gender-test
  (testing "Valid Data"
    (let [valid-data [{:last-name "Abc" :gender "Male"}
                      {:last-name "Xyz" :gender "Female"}
                      {:last-name "Mno" :gender "Female"}]
          sorted-data (render/data-by-gender valid-data)]

      (is (= ["Mno" "Xyz" "Abc"]
             (mapv :last-name sorted-data))
          "Last names are in Ascending order")
      (is (= ["Female" "Female" "Male"]
             (mapv :gender sorted-data))
          "All females are before males"))))

(deftest data-by-lastname-test
  (testing "Valid Data"
    (let [valid-data [{:last-name "Abc" :gender "Male"}
                      {:last-name "Xyz" :gender "Female"}
                      {:last-name "Mno" :gender "Female"}]
          sorted-data (render/data-by-lastname valid-data)]

      (is (= ["Xyz" "Mno" "Abc"]
             (mapv :last-name sorted-data))
          "Last names are in Descending order"))))

(deftest data-by-dob-test
  (testing "Valid Data"
    (let [valid-data [{:date-of-birth "12/12/2012"}
                      {:date-of-birth "12/11/2012"}
                      {:date-of-birth "1/1/2011"}
                      {:date-of-birth "8/08/1988"}]
          sorted-data (render/data-by-dob valid-data)]

      (is (= ["8/08/1988" "1/1/2011" "12/11/2012" "12/12/2012"]
             (mapv :date-of-birth sorted-data))
          "Birthdates are in Ascending order"))))
