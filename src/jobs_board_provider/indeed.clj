(ns jobs-board-provider.indeed
  "Indeed.com adapter for the jobs provider"
  (:require [net.cgrand.enlive-html :as html]
            [clojure.string :as string]
            [pl-stats-bot.jobs-board-provider :refer [JobsBoardProvider]]))

(defn- fetch-query [url query]
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

(defrecord IndeedJobsBoardProvider [conf]
  JobsBoardProvider

  (get-jobs-stats [{url :url} language]
    (when-let [c (-> url (fetch-query language) doc->text parse-jobs-count)]
      {language c})))
