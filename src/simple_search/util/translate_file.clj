(ns simple-search.util.translate-file
  (:require [clojure.string :as str]
            [me.raynes.fs :as fs]))

(defn line->map
  "Translates a single line from Psinger's format to a map."
  [line]
  (let [parts (str/split line #",")]
    {:value (bigint (get parts 1))
     :weight (bigint (get parts 2))}))

(defn translate-single-instance
  "Translate a single knapsack instance from Pisinger's format to an collection of maps."
  [text]
  (let [lines (str/split-lines text)
        name (first lines)
        item-lines (filter (partial re-find #",") lines)]
    {:name name
     :items (map line->map item-lines)}))

(def instance-separator
  #"-----

")

(defn translate-instances
  "Translate a collection of knapsack instances from Pisinger's format to a collection
   of collection of maps."
  [text]
  (let [parts (str/split text instance-separator)]
    (map translate-single-instance parts)))

(defn swap-file-extension
  "Replace the `.txt` extension with `.clj` on the given file name."
  [filename]
  (str (fs/base-name filename ".txt") ".clj"))
