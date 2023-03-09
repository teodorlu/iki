(ns teodorlu.iki
  (:require
   [babashka.cli :as cli]
   [clojure.string :as str]))

(defn iki-help [{}]
  (println (str/trim "
The IKI CLI is not yet ready. Please stay put!
"))
  )

(defn list-pages [{}])

(declare dispatch-table)

(defn print-subcommands [{}]
  (println "usage: neil-quickadd <command>")
  (println "")
  (println "available commands:")
  (doseq [{:keys [cmds helptext]} dispatch-table]
    (let [helptext (if helptext (str "     ; " helptext)
                       "")]
      (println (str "  " "neil-quickadd " (str/join " " cmds) helptext)))))

(def dispatch-table
  [{:cmds []             :fn iki-help}
   {:cmds ["list-pages"] :fn list-pages}
   {:cmds ["help"]       :fn print-subcommands :helptext "Get help!"}])

(defn -main [& args]
  (cli/dispatch dispatch-table args))
