(ns jobs-board-provider.indeed
  "Indeed.com adapter for the jobs provider"
  (:require [net.cgrand.enlive-html :as html]
            [clojure.string :as string]))

(def url "https://www.indeed.com/jobs")

(defn- fetch-query [query]
  (-> url
      (str "?q=" query "&l=")
      java.net.URL.
      html/html-resource))

(defn- doc->text [doc]
  (some->> [:div#searchCountPages]
           (html/select doc)
           (map html/text)
           (string/join " ")
           (string/trim)))

(defn- parse-jobs-count [text]
  (some->> ""
           (string/replace text ",")
           (re-matches #"^.* (\d+) .*$")
           second
           Integer/parseInt))

(def fetch-pl-stats (comp parse-jobs-count doc->text fetch-query))
