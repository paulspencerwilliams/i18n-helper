(ns i18n-helper.newcore
  (:require [i18n-helper.fileparser :as parser]
            [i18n-helper.comparer :as comparer]
            [clojure.spec :as s]
            [clojure.java.io :as io]))

(defn report-missing [messages-path]
  (comparer/report-missing (parser/parse-bundles messages-path)))

(defn report-translations-pending []
  (println "Reporting translations pending..."))

(defn report-usage []
  (println "Reporting usage..."))
