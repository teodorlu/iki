(ns teodorlu.iki.contrib.latest-changes
  (:require [clojure.edn :as edn]))

(def ^:private teod-play-index-path "https://play.teod.eu/index/big.edn")
(defn ^:private teod-play-index [] (edn/read-string (slurp teod-play-index-path)))

(defonce ^:private !index (atom nil))

(defn by-latest [index]
  (->> index
       (filter :created)
       (sort-by :created)
       reverse))

(defn report-latest
  ([index] (report-latest index {}))
  ([index {:keys [n group-size]}]
   (let [n (or n 20)
         group-size (or group-size 10)
         items (->> index by-latest (take n))]
     (doseq [group-items (partition group-size items)]
       (println)
       (doseq [{:keys [slug created _title] :as item} group-items]
         (println (format "%-50s (%s)" slug created)))
       ))))

(comment
  (format "%10s (%s)" "a title" "created")
  (take 10 @!index)

  (println (apply str (repeat 40 "\n")))

  (report-latest @!index)


  (do
    (println (apply str (repeat 20 "\n")))
    (println "---------------")
    (report-latest @!index {:n 50}))

  (clojure.repl/doc partition)

  )
