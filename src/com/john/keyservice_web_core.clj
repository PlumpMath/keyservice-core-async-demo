(ns com.john.keyservice-web-core
  (:require [com.john.keyservice-service :as hello-svc]
            [clojure.tools.logging :as log]
            [com.john.key-master :as master]
            [compojure.core :as compojure]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]))

(defn app
  [channel-service]
  (->(compojure/routes
     (compojure/GET "/encrypt/keys" []
       (fn [req]
         (log/info "Handling request for keys:")
         {:status  200
          :headers {"Content-Type" "text/plain"}
          :body    (master/get-keys (hello-svc/get-channel channel-service :key-chan))}))
     (route/not-found "Not Found"))
     (middleware/wrap-json-body {:keywords? true})
     (middleware/wrap-json-response)))
