(ns com.john.keyservice-key-maker-service
  (:require [clojure.tools.logging :as log]
            [com.john.key-maker :as maker]
            [com.john.keyservice-service :as hello-svc]
            [puppetlabs.trapperkeeper.services :as services]
            [puppetlabs.trapperkeeper.core :as trapperkeeper]))



(trapperkeeper/defservice key-maker
  [ChannelService]
  (init [this context]
        (log/info "Initializing Key Maker")
        context)
  (start [this context]
         (log/info "Initializing Key Maker")
         (maker/start-making-keys (hello-svc/get-channel (services/get-service this :ChannelService) :key-chan))
         context)
  (stop [this context]
        (log/info "Shutting down Key Maker")
        context))