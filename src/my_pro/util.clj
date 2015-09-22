(ns my-pro.util
  (:require [cljfmt.core :refer :all]))

(defn create-test
  {:doc "生成测试文件"}
  [ns-str]
  (let [file-name (-> ns-str
                      symbol
                      the-ns
                      namespace-munge)
        generate-ns (-> file-name
                        (clojure.string/replace  #"_" "-")
                        (str "-test"))
        {:keys [out]} (clojure.java.shell/sh "pwd")
        test-dir (-> out
                     (clojure.string/replace #"\n" "")
                     (str  "/test/"))
        target-dir (->>
                    (-> generate-ns
                        (clojure.string/replace #"-" "_")
                        (clojure.string/replace #"\." "/")
                        (str ".clj"))
                    (str test-dir))
        content (reformat-string
                 (format "(ns %s
                    (:require [clojure.test :refer :all]
                              [%s :refer :all]))

                    (deftest a-test
                       (testing \"FIXME, I fail.\"
                       (is (not= 0 1)))) "
                                  generate-ns
                                  ns-str))]
    (clojure.core/spit target-dir content)
    target-dir))
