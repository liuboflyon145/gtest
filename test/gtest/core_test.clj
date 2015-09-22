(ns gtest.core-test
  (:require [clojure.test :refer :all]
            [my-pro.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (not= 0 1))))

(deftest unnil-test
  (testing "unnil test"
    (is (not (unnil? "")) "null string")
    (is (unnil? [123 456 54]) "not null collection")
    (is (not (unnil? [])) "null collection")
    (is (unnil? "123456") "not null string")))
