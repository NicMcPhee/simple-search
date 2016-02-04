(ns simple-search.core-test
  (:use midje.sweet)
  (:use [simple-search.core])
  (:use simple-search.knapsack-examples.knapPI_13_20_1000))

(facts "about `included-items`"
       (included-items [] []) => []
       (included-items [5 8 9] [0 0 0]) => []
       (included-items [5 8 9] [1 1 1]) => [5 8 9]
       (included-items [5 8 9] [0 1 0]) => [8])

(defn zero-or-one?
  [x]
  (or (= x 0)
      (= x 1)))

(facts "about `random-answer`"
       (:instance (random-answer knapPI_13_20_1000_1))
             => knapPI_13_20_1000_1
       (count (:choices (random-answer knapPI_13_20_1000_1)))
             => (count (:items knapPI_13_20_1000_1))
       (:choices (random-answer knapPI_13_20_1000_1))
             => (has every? zero-or-one?)
       (:total-weight (random-answer knapPI_13_20_1000_1)) => (comp not neg?)
       (:total-value (random-answer knapPI_13_20_1000_1)) => (comp not neg?))

(facts "about `score`"
       (score {:total-weight 100
               :total-value 25
               :instance {:capacity 10}}) => 0
       (score {:total-weight 10
               :total-value 25
               :instance {:capacity 100}}) => 25)

(facts "about `add-score`"
       (add-score
        {:total-weight 100
         :total-value 25
         :instance {:capacity 10}}) => {:total-weight 100
                                        :total-value 25
                                        :instance {:capacity 10}
                                        :score 0}
       (add-score
        {:total-weight 10
         :total-value 25
         :instance {:capacity 100}}) => {:total-weight 10
                                         :total-value 25
                                         :instance {:capacity 100}
                                         :score 25})
