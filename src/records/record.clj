(ns records.record)



(defprotocol RecordsDB
  (save [_ data])
  (get-all [_]))

(extend-protocol RecordsDB
  clojure.lang.Atom
  (save [this data]
    (swap! this concat data))
  (get-all [this]
    @this))