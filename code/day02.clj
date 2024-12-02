(ns code.day02
  (:require
   [clojure.string :as str]))

(def input (slurp "inputs/day02.in"))

(def testin
  "7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9")

(defn debug [x]
  (println x)
  x)

(defn parse [data]
  (->> data
       (str/split-lines)
       ;;(map Integer/parseInt)
       (map #(map Integer/parseInt (str/split % #" ")))))

(def parsedInput (parse input))
(def parsedTest (parse testin))

(defn sum [x]
  (reduce + x))

(defn all [x]
  (every? identity x))

(defn any [x]
  (some identity x))

(defn all-but-one [x] 
  (if (empty? x) 
    [[]] 
    (conj (map #(conj % (first x)) (all-but-one (rest x))) (rest x))))

(defn valid-diff [a b]
  (let [absdiff (abs (- a b))]
    (and (< absdiff 4) (< 0 absdiff))))

(defn valid [lst]
  (and (all (map valid-diff lst (rest lst)))
       (or (= lst (sort lst)) (= lst (reverse (sort lst))))))

(defn valid2 [lst] 
  (any (map valid (all-but-one lst))))

(defn solve1 [in] 
  (sum (map #(if (valid %) 1 0) in)))

(defn solve2 [in]
  (sum (map #(if (valid2 %) 1 0) in)))

;;(println parsedTest)
(println (solve1 parsedInput))
(println (solve2 parsedInput))
;;(println (solve1 parsedTest))
;;(println (solve2 parsedTest))
