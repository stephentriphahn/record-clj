(ns records.render-test
  (:require [clojure.test :refer [deftest is testing]]
            [records.render :as render]))

(deftest data-by-gender-test
  (testing "Valid Data"
    (let [valid-data [{"LastName" "Abc" "Gender" "Male"}
                      {"LastName" "Xyz" "Gender" "Female"}
                      {"LastName" "Mno" "Gender" "Female"}]
          sorted-data (render/data-by-gender valid-data)]

      (is (= ["Mno" "Xyz" "Abc"]
             (mapv #(get % "LastName") sorted-data))
          "Last names are in Ascending order")
      (is (= ["Female" "Female" "Male"]
             (mapv #(get % "Gender") sorted-data))
          "All females are before males"))))

(deftest data-by-lastname-test
  (testing "Valid Data"
    (let [valid-data [{"LastName" "Abc" "Gender" "Male"}
                      {"LastName" "Xyz" "Gender" "Female"}
                      {"LastName" "Mno" "Gender" "Female"}]
          sorted-data (render/data-by-lastname valid-data)]

      (is (= ["Xyz" "Mno" "Abc"]
             (mapv #(get % "LastName") sorted-data))
          "Last names are in Descending order"))))

(deftest data-by-dob-test
  (testing "Valid Data"
    (let [valid-data [{"DateOfBirth" "12/12/2012"}
                      {"DateOfBirth" "12/11/2012"}
                      {"DateOfBirth" "1/1/2011"}
                      {"DateOfBirth" "8/08/1988"}]
          sorted-data (render/data-by-dob valid-data)]

      (is (= ["8/08/1988" "1/1/2011" "12/11/2012" "12/12/2012"]
             (mapv #(get % "DateOfBirth") sorted-data))
          "Birthdates are in Ascending order"))))
