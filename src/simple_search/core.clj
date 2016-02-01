(ns simple-search.core
  (:use simple-search.knapsack-examples.knapPI_11_20_1000
        simple-search.knapsack-examples.knapPI_13_20_1000
        simple-search.knapsack-examples.knapPI_16_200_1000))

(defn random-choice
  [instance]
  (repeatedly (count (:items instance))
              #(rand-int 2)))

; (random-choice knapPI_16_20_1000_1)

(defn score
  [instance choice]
  (let [pairs (map vector choice (:items instance))
        kept-items (map second (filter #(= 1 (first %)) pairs))
        total-value (reduce + (map :value kept-items))
        total-weight (reduce + (map :weight kept-items))]
    (if (<= total-weight (:capacity instance))
      total-value
      (- total-weight))))

; (score knapPI_16_20_1000_1 (random-choice knapPI_16_20_1000_1))

(defn random-search
  [instance num-tries]
  (apply max (map (partial score instance)
                  (repeatedly num-tries #(random-choice instance)))))

; (time (random-search knapPI_16_200_1000_1 1000000))

(defn mutate
  [bits]
  (let [num-bits (count bits)]
    (map (fn [bit] (if (zero? (rand-int num-bits))
                     (- 1 bit)
                     bit))
         bits)))

; (mutate [0 1 1 1 0 0 0 1 0])

(defn hill-climbing
  [instance max-tries]
  (loop [current (random-choice instance)
         current-score (score instance current)
         num-tries 1]
    ; (println (str current-score " : " (pr-str current)))
    (if (>= num-tries max-tries)
      current-score
      (let [new (mutate current)
            new-score (score instance new)]
        (if (> new-score current-score)
          (recur new new-score (inc num-tries))
          (recur current current-score (inc num-tries)))))))

(time (hill-climbing knapPI_16_200_1000_1 1000000))
