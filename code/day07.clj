(ns code.day07
  (:require
   [clojure.string :as str]))

(def input (slurp "inputs/day07.in"))

(def testin
  "190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20")

(defn debug [x]
  (println x)
  x)

(defn parse [data]
  (->> data
       (str/split-lines)
       (map #(str/split % #": "))
       (map #(hash-map 
              :target (parse-long (first %)),
              :list (map parse-long (str/split (nth % 1) #" "))))))

(def parsedInput (parse input))
(def parsedTest (parse testin))

(defn sum [x]
  (reduce + x))

(defn rec [lst cur target]
  (if (empty? lst) (= cur target)
      (or (rec (rest lst) (+ cur (first lst)) target)
          (rec (rest lst) (* cur (first lst)) target))))

(defn has-match [cur]
  (let [target (get cur :target)
        lst (get cur :list)]
    (if (rec (rest lst) (first lst) target) target 0)))

(defn rec2 [lst cur target]
  (if (empty? lst) (= cur target)
      (or (rec2 (rest lst) (+ cur (first lst)) target) 
          (rec2 (rest lst) (* cur (first lst)) target) 
          (rec2 (rest lst) (parse-long (str cur (first lst))) target))))

(defn has-match2 [cur]
  (let [target (get cur :target)
        lst (get cur :list)]
    (if (rec2 (rest lst) (first lst) target) target 0)))

(defn solve1 [in]
  (sum (map has-match in)))

(defn solve2 [in]
  (sum (map has-match2 in)))

;;(println parsedTest)
;;(println (solve1 parsedTest))
(println (solve1 parsedInput))
;;(println (solve2 parsedTest))
(println (solve2 parsedInput))
