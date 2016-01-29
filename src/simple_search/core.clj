(ns simple-search.core
  (:use simple-search.knapsack-examples.knapPI_11_20_1000
        simple-search.knapsack-examples.knapPI_13_20_1000
        simple-search.knapsack-examples.knapPI_16_20_1000))

(defn random-choice
  [num-choices]
  (repeatedly num-choices #(rand-int 2)))

(:items knapPI_11_20_1000_1)

(random-choice (count (:items knapPI_11_20_1000_1)))
(random-choice (count (:items knapPI_11_20_1000_1)))
(random-choice (count (:items knapPI_11_20_1000_1)))

(defn sum-chosen-items
  [choices items]
  (let [applied-choices (map (fn [choice item]
                               (if (zero? choice)
                                 {:value 0 :weight 0}
                                 item))
                             choices items)
        total-value (reduce + (map :value applied-choices))
        total-weight (reduce + (map :weight applied-choices))]
    {:value total-value :weight total-weight}))

(sum-chosen-items (random-choice (count (:items knapPI_11_20_1000_1)))
                  (:items knapPI_11_20_1000_1))

(defn score
  [choices instance]
  (let [items (:items instance)
        sums (sum-chosen-items choices items)]
    (if (> (:weight sums) (:capacity instance))
      (- (:weight sums))
      (:value sums))))

(score (random-choice (count (:items knapPI_11_20_1000_1)))
       knapPI_11_20_1000_1)

(time (apply max (repeatedly 100000 #(score (random-choice (count (:items knapPI_11_20_1000_1)))
       knapPI_11_20_1000_1))))
