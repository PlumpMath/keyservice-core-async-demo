(ns com.john.keyservice-core-test
  (:require [clojure.test :refer :all]
            [com.john.keyservice-core :refer :all]))

(deftest hello-test
  (testing "says hello to caller"
    (is (= "Hello, foo!" (hello "foo")))))
