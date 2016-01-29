(ns simple-search.util.translate-file-test
  (:use midje.sweet)
  (:use [simple-search.util.translate-file]))

(facts "about `line->map`"
       ((line->map "18,32,724,0") :value) => 32
       ((line->map "1,32,724,1") :weight) => 724)

(facts "about `swap-file-extension`"
       (fact "it replaces `.cvs` with `.clj` for simple file names"
             (swap-file-extension "data.csv") => "data.clj"
             (swap-file-extension "complex_file_name.csv") => "complex_file_name.clj"))

(def knapPI_11_10_1000_1 "knapPI_11_10_1000_1
n 10
c 460
z 1428
time 0.00
1,171,873,0
2,918,594,0
3,408,264,0
4,714,462,0
5,510,330,0
6,114,582,0
7,76,388,0
8,57,291,0
9,204,132,0
10,1020,660,0
-----

")

(def knapPI_11_20_1000_1 "knapPI_11_20_1000_1
n 20
c 970
z 1428
time 0.00
1,114,582,0
2,38,194,0
3,133,679,0
4,95,485,0
5,612,396,0
6,171,873,0
7,918,594,0
8,408,264,0
9,714,462,0
10,510,330,0
11,114,582,0
12,76,388,0
13,57,291,0
14,204,132,0
15,1020,660,0
16,816,528,0
17,190,970,0
18,510,330,0
19,114,582,0
20,714,462,0
-----

")

(facts "about `translate-single-instance`"
       (let [result_11_10_1000_1 (translate-single-instance knapPI_11_10_1000_1)
             result_11_20_1000_1 (translate-single-instance knapPI_11_20_1000_1)]
         (fact "it has the right name"
               (:name result_11_10_1000_1) => "knapPI_11_10_1000_1"
               (:name result_11_20_1000_1) => "knapPI_11_20_1000_1")
         (fact "it has the correct capacity"
               (:capacity result_11_10_1000_1) => 460
               (:capacity result_11_20_1000_1) => 970)
         (fact "it's vector has the right length"
               (count (:items result_11_10_1000_1)) => 10
               (count (:items result_11_20_1000_1)) => 20)
         (fact "it has the correct first item"
               (first (:items result_11_10_1000_1)) => {:value 171 :weight 873}
               (first (:items result_11_20_1000_1)) => {:value 114 :weight 582})
         (fact "it has the correct last item"
               (last (:items result_11_10_1000_1)) => {:value 1020 :weight 660}
               (last (:items result_11_20_1000_1)) => {:value 714 :weight 462})))

(facts "about `translate-instances`"
       (let [result_11_10_1000_1 (translate-single-instance knapPI_11_10_1000_1)
             result_11_20_1000_1 (translate-single-instance knapPI_11_20_1000_1)
             results (translate-instances (str knapPI_11_10_1000_1 knapPI_11_20_1000_1))]
         (fact "it has two resulting item sets"
               (count results) => 2)
         (fact "first is correct"
               (first results) => result_11_10_1000_1)
         (fact "second is correct"
               (second results) => result_11_20_1000_1)))
