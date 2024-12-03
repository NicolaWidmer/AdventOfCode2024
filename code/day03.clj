(ns code.day03
  (:require
   [clojure.string :as str]))

(def input (slurp "inputs/day03.in"))

(def testin
  "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")
(def testin2
  "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")

(defn debug [x]
  ;;(println x)
  x)

(defn parse-muls [lst]
  (map Integer/parseInt (rest lst)))

(defn find-muls [s]
  (re-seq #"mul\((\d+),(\d+)\)" s))

(defn parse [data]
  (->> data
       (str/split-lines)
       (map #(map parse-muls (find-muls  %)))))

(defn parse-helper2 [lst]
  (map parse-muls (find-muls (first (str/split (debug lst) #"don\'t\(\)")))))

(defn parse2 [data]
  (->> data
       (str/split-lines)
       (map #(map parse-helper2 (debug (str/split % #"do\(\)"))))))

(def parsedInput (parse input))
(def parsedTest (parse testin))

(defn sum [x]
  (reduce + x))

(defn prod [x]
  (reduce * x))

(defn flatten-one-level [x]
  (apply concat x))

(defn flatten-two-level [x]
  (flatten-one-level (flatten-one-level x)))

(defn solve1 [in]
  (sum (map prod (flatten-one-level in))))

(defn solve2 [in]
  (sum (map prod (flatten-two-level in))))

;;(println parsedTest)
;;(println (solve1 parsedTest))
(println (solve1 parsedInput))
;;(println (parse2 testin2))
;;(println (solve2 (parse2 testin2)))
(println (solve2 (parse2 input)))
