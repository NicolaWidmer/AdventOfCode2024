(ns code.day01
  (:require
   [clojure.string :as str]))

(def input (slurp "inputs/day01.in"))

(def testin
  "3   4
4   3
2   5
1   3
3   9
3   3")

(defn debug [x]
  (println x)
  x)

(defn parse [data]
  (->> data
       (str/split-lines)
       (map #(  map Integer/parseInt (rest ( re-matches #"(\d+)   (\d+)"  %))))))

(def parsedInput (parse input))
(def parsedTest (parse testin))

(defn sum [x]
  (reduce + x))

(defn part [ll ind]
  (map #(nth % ind) ll))

(defn count-occurrences [elem lst]
  (count (filter #(= % elem) lst)))


(defn solve1 [in]
  (let [part0 (part in 0)
        part1 (part in 1)]
    (sum (map #(abs (- %1 %2)) (sort part0) (sort part1)))))

(defn solve2 [in]
  (let [part0 (part in 0)
        part1 (part in 1)]
    (sum (map #(* % (count-occurrences % part1)) part0))))

(println (solve1 parsedInput))
(println (solve2 parsedInput))
;;(println (solve1 parsedInput))
;;(println (solve2 parsedInput))