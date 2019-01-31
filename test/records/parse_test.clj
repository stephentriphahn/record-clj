(ns records.parse-test
  (:require [clojure.test :refer [deftest testing is]]
            [records.parse :as parse]))


(declare thrown?) ;;appease IntelliJ

#_(deftest parse-test
  (testing "Nil source"
    (is (thrown? (parse/parse nil))))

  (testing "CSV From file"
    (let [path "resources/records/sample.csv"
          actual (parse/parse path)]
      (is (= (count actual) 2) "Both records should get parsed")

      )))
