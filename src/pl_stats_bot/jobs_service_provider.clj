(ns pl-stats-bot.jobs-board-provider
  "Jobs board adapter")

(defprotocol JobsBoardProvider
  "Jobs board actions behaviour"

  (get-jobs-stats [conf search-term] "Returns jobs stats by the searching term"))
