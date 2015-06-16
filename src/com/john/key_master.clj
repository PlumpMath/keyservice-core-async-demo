(ns com.john.key-master
  (:require [clojure.core.async :as async]))

(defn get-keys [channel]
  (let [priv-key (async/<!! channel)
        public-key (async/<!! channel)]
    (str "Private Key " (.toString priv-key) "\n" "Public Key " (.toString public-key) "\n" )))
