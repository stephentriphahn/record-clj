(ns records.render-test
  (:require [clojure.test :refer [deftest is testing]]
            [records.render :as render]))

(deftest data-by-lastname-test
  (testing "Valid Data"
    (let [valid-data [{:last-name "Abc" :email "A@xyz.co" :favorite-color "blue"
                       :date-of-birth "1/1/2000" :first-name "s"}
                      {:last-name "Xyz" :email "A@xyz.co" :favorite-color "blue"
                       :date-of-birth "1/1/2000" :first-name "s"}
                      {:last-name "Mno" :email "A@xyz.co" :favorite-color "blue"
                       :date-of-birth "1/1/2000" :first-name "s"}]
          sorted-data (render/data-by-lastname valid-data)]

      (is (= ["Xyz" "Mno" "Abc"]
             (mapv :last-name sorted-data))
          "Last names are in Descending order"))))

(deftest data-by-dob-test
  (testing "Valid Data"
    (let [valid-data [{:last-name "Abc" :email "A@xyz.co" :favorite-color "blue"
                       :date-of-birth "12/12/2012" :first-name "xxx"}
                      {:last-name "Abc" :email "A@xyz.co" :favorite-color "blue"
                       :date-of-birth "12/11/2012" :first-name "xxx"}
                      {:last-name "Abc" :email "A@xyz.co" :favorite-color "blue"
                       :date-of-birth "1/1/2011" :first-name "xxx"}
                      {:last-name "Abc" :email "A@xyz.co" :favorite-color "blue"
                       :date-of-birth "8/08/1988" :first-name "xxx"}]
          sorted-data (render/data-by-dob valid-data)]

      (is (= ["8/08/1988" "1/1/2011" "12/11/2012" "12/12/2012"]
             (mapv :date-of-birth sorted-data))
          "Birthdates are in Ascending order"))))

(deftest data-by-email
  (testing "Valid data"
    (let [valid-data [{:last-name "A" :email "A@xyz.com"
                       :favorite-color "blue" :date-of-birth "12/12/2012"
                       :first-name "xxx"}
                      {:last-name "Z" :email "B@xyz.com"
                       :favorite-color "blue" :date-of-birth "12/11/2012"
                       :first-name "xxx"}
                      {:last-name "B" :email "B@xyz.com"
                       :favorite-color "blue"
                       :date-of-birth "1/1/2011" :first-name "xxx"}
                      {:last-name "C" :email "A@xyz.com"
                       :favorite-color "blue" :date-of-birth "8/08/1988"
                       :first-name "xxx"}]
          sorted-data (render/data-by-email valid-data)]

      (is (= ["B@xyz.com" "B@xyz.com" "A@xyz.com" "A@xyz.com"]
             (mapv :email sorted-data))
          "Data is sorted by descending email")
      (is (= ["B" "Z" "A" "C"]
             (mapv :last-name sorted-data))
          "Data is sorted by last-name ascending after email"))))
