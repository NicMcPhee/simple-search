(ns simple-search.util.translate-file
  (:require [me.raynes.fs :as fs]))

;; "knapPI_11_10_1000_1
;; n 10
;; c 970
;; z 1428
;; time 0.00
;; 1,171,873,0
;; 2,918,594,0
;; 3,408,264,0
;; 4,714,462,1
;; 5,510,330,0
;; 6,114,582,0
;; 7,76,388,0
;; 8,57,291,0
;; 9,204,132,0
;; 10,1020,660,0
;; -----

;; "

(defn line->map
  "Translates a single line from Psinger's format to a map."
  [line]
  (let [parts (clojure.string/split line #",")]
    {:value (bigint (get parts 1))
     :weight (bigint (get parts 2))}))

(defn translate-single-instance
  "Translate a single knapsack instance from Pisinger's format to an array of maps."
  [text]
  (let [lines (filter (partial re-find #",")
                      (clojure.string/split-lines text))]
    (map line->map lines)))

(defn swap-file-extension
  "Replace the `.txt` extension with `.clj` on the given file name."
  [filename]
  (str (fs/base-name filename ".txt") ".clj"))
