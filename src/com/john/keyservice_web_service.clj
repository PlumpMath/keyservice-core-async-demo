(ns com.john.keyservice-web-service
  (:require [clojure.tools.logging :as log]
            [compojure.core :as compojure]
            [com.john.keyservice-web-core :as core]
            [puppetlabs.trapperkeeper.core :as trapperkeeper]
            [puppetlabs.trapperkeeper.services :as tk-services]))

(trapperkeeper/defservice hello-web-service
  [[:ConfigService get-in-config]
   [:WebroutingService add-ring-handler get-route]
   HelloService
   ChannelService]
  (init [this context]
    (log/info "Initializing hello webservice")
    (let [url-prefix (get-route this)]
      (add-ring-handler
        this
        (compojure/context url-prefix []
          (core/app (tk-services/get-service this :HelloService) (tk-services/get-service this :ChannelService))))
      (assoc context :url-prefix url-prefix)))

  (start [this context]
         (let [host (get-in-config [:webserver :host])
               port (get-in-config [:webserver :port])
               url-prefix (get-route this)]
              (log/infof "Hello web service started; visit http://%s:%s%s/world to check it out!"
                         host port url-prefix))
         context))
