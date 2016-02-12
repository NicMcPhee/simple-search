(ns simple-search.experiment
  (:require [simple-search.core :as core])
  (:use simple-search.knapsack-examples.knapPI_11_20_1000
        simple-search.knapsack-examples.knapPI_13_20_1000
        simple-search.knapsack-examples.knapPI_16_20_1000
        simple-search.knapsack-examples.knapPI_16_200_1000))

(defmacro fn-name
  [f]
  `(-> ~f var meta :name str))

(defn run-experiment
  [searchers scorers problems num-replications max-evals]
  (doseq [searcher searchers
          scorer scorers
          p problems
          n (range num-replications)]
    (let [result (searcher scorer p max-evals)]
      (println searcher scorer n (:score result)))))

(run-experiment [(partial core/hill-climber core/mutate-answer)]
                [core/score core/penalized-score]
                [knapPI_13_20_1000_4] 5 1000)
