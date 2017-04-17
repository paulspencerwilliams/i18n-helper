(ns i18n-helper.comparer
  (:require [clojure.spec :as s]))

(defn report-missing [bundles]
  (doseq [bundle bundles]
    (println (str "Processing " (:path bundle)))))
