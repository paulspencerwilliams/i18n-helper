(ns i18n-helper.cli
  (:require [i18n-helper.newcore :refer :all]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :as string])
  (:gen-class))

(def cli-options
  ;; An option with a required argument
  [["-m" "--messages-path MESSAGESPATH" "messages.properties directory path"
    :default (System/getProperty "user.dir")]
   ["-s" "--src-root-path SRCROOTPATH" "Project source root used for testing key usage"]])

(defn usage [options-summary]
  (->> ["A small utility to help wrangle message.properties files"
        ""
        "Usage: i8nn-helper [options] action"
        ""
        "Options:"
        options-summary
        ""
        "Actions:"
        "  report-missing              Report keys missing in all translations"
        "  report-translations-pending Report any translations pending"
        "  report-usage                Report usage of a specified key"
        ""]
       (string/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))

(defn validate-args
  "Validate command line arguments. Either return a map indicating the program
  should exit (with a error message, and optional ok status), or a map
  indicating the action the program should take and the options provided."
  [args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options) ; help => exit OK with usage summary
      {:exit-message (usage summary) :ok? true}
      errors ; errors => exit with description of errors
      {:exit-message (error-msg errors)}
      ;; custom validation on arguments
      (and (= 1 (count arguments))
           (#{"report-missing" "report-translations-pending" "report-usage"} (first arguments)))
      {:action (first arguments) :options options}
      :else ; failed custom validation => exit with usage summary
      {:exit-message (usage summary)})))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn -main [& args]
  (let [{:keys [action options exit-message ok?]} (validate-args args)]
    (if exit-message
      (exit (if ok? 0 1) exit-message)
      (case action
        "report-missing" (report-missing)
        "report-translations-pending" (report-translations-pending)
        "report-usage" (report-usage)))))
