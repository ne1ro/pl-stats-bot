(ns web.service
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as st]
            [clojure.string :as str]
            [compojure.core :refer :all]
            [morse.handlers :as h]
            [pl-stats-bot.messenger
             :refer [send-message send-photo]]
            [pl-stats-bot.use-cases :refer [PlStatsBot]]))

(s/def ::messenger map?)
(s/def ::use-cases map?)

(def help-message ^:private
  "Type `/jobs-stats :language` to the jobs stats for programming language
   :language")

(defn- start [messenger {{id :id :as chat} :chat
                         {first-name :first_name} :from}]
  (println "Bot joined new chat: " chat)
  (send-message messenger
                (str "Welcome, "
                     first-name
                     " ! Type /help to see more details.") id))

(defn- help [messenger {{id :id :as chat} :chat}]
  (println "Help was requested in " chat)
  (send-message messenger help id))

(s/fdef get-bot :args
  (s/cat :messenger ::messenger :use-cases ::use-cases))
(defn get-bot [messenger use-cases]
  (h/defhandler bot-handler
    (h/command-fn "start" (partial start messenger))
    (h/command-fn "help" (partial help messenger))
    (h/message message (println "Intercepted message:" message))))

(st/instrument)
