(ns records.record)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  Record lens functions

(defn last-name
  [record]
  (get record "LastName"))

(defn date-of-birth
  [record]
  (get record "DateOfBirth"))

(defn gender
  [record]
  (get record "Gender"))


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
    (swap! this concat data))
  (get-all [this]
    (set @this)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  Errors

