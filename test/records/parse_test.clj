(ns records.parse-test
  (:require [clojure.spec.alpha :as spec]
            [clojure.test :refer [deftest testing is]]
            [records.parse :as parse]
            [records.record :as record])
  (:import (java.io FileNotFoundException)))


(declare thrown?) ;;appease IntelliJ

(def fields ["LastName" "FirstName" "Email" "FavoriteColor" "DateOfBirth"])
(def valid-sample-row ["Doe" "John" "jdoe@test.com" "blue" "10/3/1988"])
(def invalid-dob-sample-row ["Doe" "John" "jdoe@test.com" "blue" "13/3/1988"])

(defn gen-input-string
  [delim & data-rows]
  (let [headers (clojure.string/join delim fields)
        data (map #(clojure.string/join delim %) data-rows)]
    (clojure.string/join "\n" (conj data headers))))

(deftest file-parse-test
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

(deftest input-stream-parse-test
  (testing "Valid data from input stream"
    (let [stream (clojure.java.io/input-stream
               (.getBytes (gen-input-string "," valid-sample-row)))
          parsed (parse/parse stream)]
      (is (every? #(spec/valid? :records.record/record %) parsed))))

  (testing "Invalid data from input stream"
    (let [stream (clojure.java.io/input-stream
                   (.getBytes (gen-input-string "," invalid-dob-sample-row)))]
      (is (thrown? AssertionError (parse/parse stream))))))
