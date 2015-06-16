(ns com.john.keyservice-service
  (:require [clojure.tools.logging :as log]
            [clojure.core.async :as async]
            [puppetlabs.trapperkeeper.services :as services]
            [puppetlabs.trapperkeeper.core :as trapperkeeper]))


(defprotocol ChannelService
  (get-channel [this name]))

(trapperkeeper/defservice channel-service
 ChannelService
 [[:ConfigService get-in-config]]
  (init [this context]
        (log/info "Initializing Channel Service")
        (assoc context :key-chan (async/chan (get-in-config [:buffersize]))))
  (start [this context]
         (log/info "Starting Channel Service")
         context)
  (stop [this context]
         (log/info "Stopping Channel Service")
         (assoc context :key-chan nil))
  (get-channel [this chan-name]
         (let [context (services/service-context this)]
           (chan-name context))))


