(ns code.day11
  (:require
   [clojure.string :as str]))

(def input (slurp "inputs/day11.in"))

(def testin
  "125 17")

(defn debug [x]
  (println x)
  x)

(defn parse [data]
  (map Integer/parseInt (str/split data #" ")))

(def parsedInput (parse input))
(def parsedTest (parse testin))

(defn sum [x]
  (reduce + x))

(defn first-half [lst]
  (take (quot (count lst) 2) lst))

(defn second-half [lst]
  (drop (quot (count lst) 2) lst))

(defn seq-to-int [lst]
  (if (empty? lst) 0
      (+ (Character/digit (first lst) 10) (* 10 (seq-to-int (rest lst))))))

(defn update-map [m num times]
  (assoc m num (+ times (get m num 0))))

(defn aux [s x times]
  (if (= 0 x)
    (update-map s 1 times)
  (if (even? (count (str x))) 
    (let [n1 (seq-to-int (reverse (first-half (str x))))
          n2 (seq-to-int (reverse (second-half (str x))))
          s1 (update-map s n1 times)]
      (update-map s1 n2 times))
    (update-map s (* x 2024) times))))

(defn rec [x rounds]
 (if (= 0 rounds)
  (sum (map second x))
   (rec (reduce #(aux %1 (first %2) (second %2)) {} x) (- rounds 1))))

(defn solve1 [in]
  (rec (map #(vector % 1) in) 25))

(defn solve2 [in]
  (rec (map #(vector % 1) in) 75))

;;(println parsedTest)
;;(println (solve1 parsedTest))
(println (solve1 parsedInput))
;;(println (solve2 parsedTest))
(println (solve2 parsedInput))
