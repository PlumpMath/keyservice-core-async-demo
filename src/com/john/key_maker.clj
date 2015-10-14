(ns com.john.key-maker
  (:require [clojure.core.async :as async]
            [clojure.tools.logging :as log])
  (:import (java.security KeyPairGenerator)))

(defn new-key-pair []
  (let [keyGen (KeyPairGenerator/getInstance "RSA")
        _ (.initialize keyGen 2048)
        pair (.generateKeyPair keyGen)]
    [(.toString (BigInteger. (.getEncoded (.getPublic pair))))
     (.toString (BigInteger. (.getEncoded (.getPrivate pair))))]))

(defn start-making-keys [channel]
  (println "Channel" channel)
  (async/go-loop []
    (let [new-key (new-key-pair)]
      (if-not (nil? channel)
        (do
          (async/>!! channel new-key)
          (log/info (str "New Key Added to channel: " (.toString new-key)))
          (recur))))))
