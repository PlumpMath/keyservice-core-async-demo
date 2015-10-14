(ns com.john.key-master
  (:require [clojure.core.async :as async]))

(defn get-keys [channel]
  (let [[public private] (async/<!! channel)]
    {:private private :public public}))
