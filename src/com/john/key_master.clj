(ns com.john.key-master
  (:require [clojure.core.async :as async]))

(defn get-keys [channel]
  (let [priv-key (async/<!! channel)
        public-key (async/<!! channel)]
    {:private (.toString priv-key) :public (.toString public-key)}))
