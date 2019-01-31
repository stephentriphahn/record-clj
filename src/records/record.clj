(ns records.record
  (:require [clojure.spec.alpha :as spec]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  Spec - Work in Progress

;; both 1/1/2000 01/01/2000 are valid
(def dob-regex #"^(0?[1-9]|1[0-2])/(0?[1-9]|1[0-9]|2[0-9]|3[01])/\d{4}$")

(spec/def ::first-name string?)
(spec/def ::last-name string?)
(spec/def ::favorite-color string?)
(spec/def ::gender #{"Male" "Female"})
(spec/def ::date-of-birth (spec/and string? #(re-matches dob-regex %)))

(spec/def ::record
  (spec/keys :req-un [::first-name ::last-name ::gender
                      ::favorite-color ::date-of-birth]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  Record lens functions

(defn last-name
  [record]
  (:last-name record))

(defn date-of-birth
  [record]
  (:date-of-birth record))

(defn gender
  [record]
  (:gender record))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  DB, just memory for now, could be expanded on later

(def ^:private mem-db (atom #{}))

(defn connect
  "Returns mem-db atom. Could be turned into multimethod to dispach on
   connection string protocols."
  [cs]
  (case cs
    "memory" mem-db))

(defprotocol RecordsDB
  (save [_ data])
  (get-all [_]))

(extend-protocol RecordsDB
  clojure.lang.Atom
  (save [this data]
    (swap! this into data))
  (get-all [this]
    (set @this)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  Errors

