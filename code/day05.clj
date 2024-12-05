(ns code.day05
  (:require
   [clojure.string :as str]))

(def input (slurp "inputs/day05.in"))

(def testin
  "47|53
97|13
97|61
97|47
75|29
61|13
75|53
29|13
97|29
53|29
61|53
97|53
61|29
47|13
75|47
97|75
47|61
75|61
47|29
75|13
53|13

75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47")

(defn debug [x]
  (println x)
  x)

(defn parse [data]
  (let[splitdata (str/split data #"\n\n")
       ord (str/split-lines (nth splitdata 0))
       ll (str/split-lines (nth splitdata 1))]
   {:ord (map #(map Integer/parseInt (str/split  % #"\|"))  ord)
    :ll (map #(map Integer/parseInt (str/split % #",")) ll)}
  ))

(def parsedInput (parse input))
(def parsedTest (parse testin))

(defn sum [x]
  (reduce + x))

(defn in-ord [ord elem1 elem2]
  (every? #(not (and (= (nth % 0) elem2) (= (nth % 1) elem1))) ord))

(defn check-ord [lst ord]
  (if (empty? lst)
    true 
    (and (every? #(in-ord ord (first lst) %) (rest lst)) (check-ord (rest lst) ord))))

(defn solve1 [in]
  (let [ord (get in :ord)
        ll (get in :ll)]
    (sum (map #(if (check-ord % ord) (nth % (quot (count %) 2)) 0) ll))))

(defn solve2 [in]
  (let [ans1 (solve1 in)
        ord (get in :ord)
        ll (get in :ll)]
    (- (sum (map #(nth (sort (partial in-ord ord) %) (quot (count %) 2)) ll)) ans1)))

;;(println parsedTest)
;;(println (solve1 parsedTest))
(println (solve1 parsedInput))
;;(println (solve2 parsedTest))
(println (solve2 parsedInput))
