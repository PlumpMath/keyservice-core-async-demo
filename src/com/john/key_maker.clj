(ns com.john.key-maker
  (:require [clojure.core.async :as async]
            [clojure.tools.logging :as log]))


(defn start-making-keys [channel]
  (println "Channel" channel)
  (async/go-loop []
    (let [new-key (java.util.UUID/randomUUID)]
      (async/>!! channel new-key)
      (log/info (str "New Key Added to channel: " (.toString new-key))))
    (recur)))
