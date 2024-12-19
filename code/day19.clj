(ns code.day19
  (:require
   [clojure.string :as str]))

(def input (slurp "inputs/day19.in"))

(def testin
  "r, wr, b, g, bwu, rb, gb, br

brwrr
bggr
gbbr
rrbgbr
ubwu
bwurrg
brgr
bbrgwb")

(defn debug [x]
  (println x)
  x)

(defn parse [data]
  (let [split  (str/split data #"\n\n")
        frags (str/split (first split) #", ")
        targets (str/split-lines (second split))]
    [frags targets]))

(def parsedInput (parse input))
(def parsedTest (parse testin))

(defn sum [x]
  (reduce + x))

(defn list-update-in [l i x]
  (let [newlist (take i l)
        newlist (concat newlist (list x))
        newlist (concat newlist (drop (+ 1 i) l))]
    newlist))

(defn foldl [f val coll]
  (if (empty? coll) val
      (foldl f (f val (first coll)) (rest coll))))

(defn dp-reduce [dp [i w] word]
  (if (= (nth dp i) 0)
    dp
    (if (= w (apply str  (take (count w) (drop i word))))
      (list-update-in dp (+ i (count w)) (+ (nth dp (+ i (count w))) (nth dp i)))
      dp)))

(defn is-pos [lst word]
  (let [n (count word)
        dp (repeat (+ n 1) 0)
        dp (list-update-in dp 0 1)]
    (foldl #(dp-reduce %1 %2 word ) dp (for [i (range n) w lst] [i w]))))


(defn solve1 [in]
  (let [frags (first in) 
        words (second in)]
    (count (filter #(not (= (last (is-pos frags %)) 0)) words))))

(defn solve2 [in]
  (let [frags (first in)
        words (second in)]
    (sum (map #(last (is-pos frags %)) words))))

(println parsedTest)
;;(println (solve1 parsedTest))
(println (solve1 parsedInput))
;;(println (solve2 parsedTest))
(println (solve2 parsedInput))
