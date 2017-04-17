(ns i18n-helper.newcore
  (:require [clojure.spec :as s]
            [clojure.java.io :as io]))

(s/def ::messages-path (s/and string? #(.exists (io/file %))  #(.isDirectory (io/file %))))

(defn report-missing [messages-path]
  {:pre [(s/valid? ::messages-path messages-path)]}
  (println (str "Reporting missing for resources in '" messages-path  "'")))

(defn report-translations-pending []
  (println "Reporting translations pending..."))

(defn report-usage []
  (println "Reporting usage..."))
