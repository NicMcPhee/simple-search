(ns simple-search.util.translate-file
  (:require [clojure.string :as str]
            [me.raynes.fs :as fs])
  (:import [java.io File]))

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
        capacity (bigint (second (str/split (get lines 2) #" ")))
        item-lines (filter (partial re-find #",") lines)]
    {:name name
     :capacity capacity
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
  "Replace the `.csv` extension with `.clj` on the given file name."
  [filename]
  (str (fs/base-name filename ".csv") ".clj"))

(def knapsack-examples-dir "src/simple_search/knapsack_examples")

(defn spit-item-set
  "Prints out an item set string to a Clojure variable defition, where
   the name of the map is the name of the variable, and the sequence items of
   the map is the value of the variable."
  [filename item-set]
  (spit filename (str "(def " (:name item-set) " {:capacity " (:capacity item-set)) :append true)
  (spit filename (str " :items '") :append true)
  (spit filename (pr-str (:items item-set)) :append true)
  (spit filename "})\n\n" :append true))

(defn -main
  "Translate all the knapsack instances in the given file from Pisinger's
   format and write them as a collection of named maps to a Clojure file."
  [filename]
  (when-not (fs/exists? knapsack-examples-dir)
    (fs/mkdir knapsack-examples-dir))
  (let [item-sets (translate-instances (slurp filename))
        clojure-filename (swap-file-extension filename)
        name-space (fs/base-name clojure-filename ".clj")
        full-filename (str knapsack-examples-dir File/separator (swap-file-extension filename))]
    (spit full-filename
          (str "(ns simple-search.knapsack-examples." name-space ")\n\n"))
    (doseq [item-set item-sets]
      (spit-item-set full-filename item-set))))
