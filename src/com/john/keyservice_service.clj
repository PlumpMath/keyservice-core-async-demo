(ns com.john.keyservice-service
  (:require [clojure.tools.logging :as log]
            [com.john.keyservice-core :as core]
            [com.john.key-maker :as maker]
            [clojure.core.async :as async]
            [puppetlabs.trapperkeeper.services :as services]
            [puppetlabs.trapperkeeper.core :as trapperkeeper]))

(defprotocol HelloService
  (hello [this caller]))

(trapperkeeper/defservice hello-service
  HelloService
  []
  (init [this context]
    (log/info "Initializing hello service")
    context)
  (start [this context]
    (log/info "Starting hello service")
    context)
  (stop [this context]
    (log/info "Shutting down hello service")
    context)
  (hello [this caller]
         (core/hello caller)))


(defprotocol ChannelService
  (get-channel [this name]))

(trapperkeeper/defservice channel-service
 ChannelService
 []
  (init [this context]
        (log/info "Initializing Channel Service")
        (assoc context :key-chan (async/chan 10)))
  (start [this context]
         (log/info "Starting Channel Service")
         context)
  (stop [this context]
         (log/info "Stopping Channel Service")
         context)
  (get-channel [this chan-name]
         (let [context (services/service-context this)]
           (chan-name context))))


