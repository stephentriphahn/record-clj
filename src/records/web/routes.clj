(ns records.web.routes
  (:require [compojure.core :refer [defroutes context GET PUT POST]]
            [compojure.route :as route]
            [records.web.handler :as handler]
            [records.render :as render]
            [ring.middleware.json :as json]))

(defroutes routes

  (context "/records" []
    (POST "/" request
      (handler/add-record (:body request)))

    (GET "/gender" []
      (handler/get-records-with render/data-by-gender))

    (GET "/birthdate" []
      (handler/get-records-with render/data-by-dob))

    (GET "/name" []
      (handler/get-records-with render/data-by-lastname)))

  (route/not-found "<h1>NOT FOUND</h1>"))


(def app
  (-> routes
      json/wrap-json-response))
