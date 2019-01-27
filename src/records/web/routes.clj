(ns records.web.routes
  (:require [compojure.core :refer [defroutes context GET PUT POST]]
            [compojure.route :as route]
            [records.web.handler :as handler]))

(defroutes app

  (context "/records" []
    (POST "/" []
      {:status 404
       :body "Not implemented yet"})

    (GET "/gender" []
      {:status 404
       :body "Not implemented yet"})

    (GET "/birthdate" []
      {:status 404
       :body "Not implemented yet"})

    (GET "/name" []
      {:status 404
       :body "Not implemented yet"}))

  (route/not-found "<h1>NOT FOUND</h1>"))
