(ns teodorlu.iki.contrib
  (:require
   [clojure.edn :as edn]))

(defn report-latest
  "See what pages you created last"
  ([index] (report-latest index {}))
  ([index {:keys [n group-size]}]
   (let [n (or n 20)
         group-size (or group-size 10)
         items (->> index
                    (filter :created)
                    (sort-by :created)
                    (reverse)
                    (take n))]
     (doseq [group-items (partition group-size items)]
       (println)
       (doseq [{:keys [slug created _title] :as _item} group-items]
         (println (format "%-50s (%s)" slug created)))))))

(comment
  (defonce !teod-index (atom nil))
  (reset! !teod-index (edn/read-string (slurp "https://play.teod.eu/index/big.edn")))

  (report-latest @!teod-index)

  ;; perhaps I can create a nice TUI for this?
  ;; =iki tui= - then actions.
  )
