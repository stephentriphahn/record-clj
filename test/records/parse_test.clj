(ns records.parse-test
  (:require [clojure.test :refer [deftest testing is]]
            [records.parse :as parse]
            [clojure.spec.alpha :as spec]))


(declare thrown?) ;;appease IntelliJ

(deftest parse-test
  (testing "Nil source"
    (is (thrown? AssertionError (parse/parse nil))))

  (testing "CSV From file"
    (let [path "resources/records/sample.csv"
          actual (parse/parse path)]
      (is (= (count actual) 2) "Both records should get parsed")
      (is (every? #(spec/valid? :records.record/record %) actual))))

  (testing "Pipe delimited From file"
    (let [path "resources/records/sample.psv"
          actual (parse/parse path)]
      (is (= (count actual) 2) "Both records should get parsed")
      (is (every? #(spec/valid? :records.record/record %) actual))))

  (testing "Space delimited From file"
    (let [path "resources/records/sample.ssv"
          actual (parse/parse path)]
      (is (= (count actual) 2) "Both records should get parsed")
      (is (every? #(spec/valid? :records.record/record %) actual)))))
