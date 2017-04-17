(ns i18n-helper.fileparser
  (:require [clojure.spec :as s]
            [clojure.java.io :as io]))

(s/def ::messages-path (s/and string? #(.exists (io/file %))  #(.isDirectory (io/file %))))

(defn parse-bundles [messages-path]
  {:pre [(s/valid? ::messages-path messages-path)]}
  (let [absolute-file-paths (->> (io/file ".")
                                 (.listFiles)
                                 (filter #(.isFile %))
                                 (map #(-> (.getAbsolutePath %))))]
    (map #(-> {:path % :contents "Contents"}) absolute-file-paths)))
