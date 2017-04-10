(defproject i18n-helper "0.1.0-SNAPSHOT"
  :description "A small utility to help wrangle message.properties files"
  :url "https://github.com/paulspencerwilliams/i18n-helper"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.5"]]
  :plugins [[lein-bin "0.3.4"]]
  :bin { :name "i18n-helper" }
  :main i18n-helper.core)
