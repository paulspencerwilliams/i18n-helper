(ns i18n-helper.core
  (:require [clojure.java.shell :refer [sh]])
  (:gen-class))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn read-keys[])
(def new-path "/Users/will/src/bcam/bcam-services/server/bcam-server/src/main/resources/i18n/messages.properties")
(def old-path "/Users/will/messages.properties.old")
(def french-path "/Users/will/src/bcam/bcam-services/server/bcam-server/src/main/resources/i18n/messages_fr.properties")
(def src-path "/Users/will/src/bcam/bcam-services/server/bcam-server/src")

(defn starts? [line]
  (and
   true
   (some? (re-find #"^(?=\S).*" line))))

(defn message-key [line]
  (last (re-find #"(.*)=" line)))

(defn deprecated? [key new-keys]
  (not (contains? new-keys key)))

(defn file-to-keys [path]
  (->> (slurp path)
       (clojure.string/split-lines)
       (filter starts?)
       (map message-key)))

(defn in-only-this [old-keys new-keys]
    (filter
     (fn [old-key]
       (deprecated? old-key new-keys))
     old-keys))

(defn deprecated-keys []
  (in-only-this (set(file-to-keys old-path)) (set(file-to-keys new-path))))

(defn errored? [result]
  (and
   (not (= 0 (:exit result)))
   (not (= "" (:err result)))))

(defn deprecated-used? [result]
  (and
   (= 0 (:exit result))
   (= "" (:err result))))

(defn -main[]
  (println "Only in French")
  (doseq [key (in-only-this (set (file-to-keys french-path)) (set (file-to-keys new-path)))]
    (println key))
  (println "Only in English")
  (doseq [key (in-only-this (set (file-to-keys new-path)) (set (file-to-keys french-path)))]
    (println key)))

(defn -not-main []
  (println "************************************************************")
  (doseq [key (deprecated-keys)]
    (let [grep-command (str "grep -R \"" key "\" .")
          result (sh "bash" "-c" grep-command :dir src-path)]
      (cond
        (errored? result)
        (do
          (println (str "Errored: " grep-command))
          (println result))
        (deprecated-used? result)
        (do
          (println (str "Deprecated key found: " grep-command))
          (println (:out result))))))
  "Done.")
