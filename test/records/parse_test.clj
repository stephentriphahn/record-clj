(ns records.parse-test
  (:require [clojure.spec.alpha :as spec]
            [clojure.test :refer [deftest testing is]]
            [records.parse :as parse]
            [records.record :as record])
  (:import (java.io FileNotFoundException)))


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
      (is (every? #(spec/valid? :records.record/record %) actual))))

  (testing "Invalid date of birth"
    (let [path "resources/records/bad.csv"]
      (is (thrown? AssertionError (parse/parse path)))))

  (testing "File path that does not exist."
    (let [path "does/not/exist.csv"]
      (is (thrown? FileNotFoundException (parse/parse path))))))
