(ns my-pro.core
  (:refer-clojure :exclude [format])
  (:require [cljfmt.core :refer :all]
            [clj-time.core :as time]
            [clj-time.format :as fmt]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn unnil?
  {:doc "参数非空判断，如果为空返回false，否则返回参数值"}
  [sources]
  (cond
    (string? sources) (if (> (count sources) 0) sources false)
    (coll? sources) (if (not (empty? sources)) sources false)
    (number? sources) (unnil? (str sources))
    :else false))




(defn create-migration [& [command]]
  (let [file-name (->
                   "yyyyMMddHHmmss"
                   java.text.SimpleDateFormat.
                   (.format (java.util.Date.)))
        {:keys [out]} (clojure.java.shell/sh "pwd")
        path (-> out
                 (clojure.string/replace #"\n" "")
                 (str "/db/sql/")
                 (str  file-name ".sql"))
        init-content (case command
                         :create "drop table if exists table_name;
create table if not exists table_name ();"
                         :insert "insert into table_name() values();"
                         :update "update table table_name set ... where ...;"
                         :alter "alter table table_name modify ....;"
                         :delete "delete from table table_name where ...;"
                         :drop "drop table table_name;"
                         "#database migration created TODO by yourself")]
    (clojure.core/spit path init-content)
    path))
