(ns i18n-helper.newcore)

(defn report-missing [messages-path]
  (println (str "Reporting missing for resources in '" messages-path  "'")))

(defn report-translations-pending []
  (println "Reporting translations pending..."))

(defn report-usage []
  (println "Reporting usage..."))
