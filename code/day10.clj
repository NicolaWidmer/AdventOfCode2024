(ns code.day10
  (:require
   [clojure.string :as str]
   [clojure.set]))

(def input (slurp "inputs/day10.in"))

(def testin
  "89010123
78121874
87430965
96549874
45678903
32019012
01329801
10456732")

(defn debug [x]
  (println x)
  x)

(defn helper [x]
  (Character/digit x 10))

(defn parse [data]
  (->> data
       (str/split-lines)
       (map #(map helper %))))

(def parsedInput (parse input))
(def parsedTest (parse testin))

(defn sum [x]
  (reduce + x))

(defn dfs1 [i j cur grid n m]
  (let [ in-bound (and (<= 0 i) (<= 0 j) (< i m) (< j n))]
    (if (or (not in-bound) (not (= cur (nth (nth grid i) j))))
      #{}
      (if (= cur 9) 
        #{(vector i j)} 
        (clojure.set/union
             (dfs1 (+ i 1) j (+ cur 1) grid n m)
             (dfs1 (- i 1) j (+ cur 1) grid n m)
             (dfs1 i (+ j 1) (+ cur 1) grid n m)
             (dfs1 i (- j 1) (+ cur 1) grid n m)
             )))))

(defn dfs2 [i j cur grid n m]
  (let [in-bound (and (<= 0 i) (<= 0 j) (< i m) (< j n))]
    (if (or (not in-bound) (not (= cur (nth (nth grid i) j))))
      0
      (if (= cur 9)
        1
        (sum [(dfs2 (+ i 1) j (+ cur 1) grid n m) 
              (dfs2 (- i 1) j (+ cur 1) grid n m) 
              (dfs2 i (+ j 1) (+ cur 1) grid n m) 
              (dfs2 i (- j 1) (+ cur 1) grid n m)]
              )))))

(defn solve1 [in]
  (let [n (count in)
        m (count (first in))]
    (sum (for [i (range m) j (range n)] (count (dfs1 i j 0 in n m))))))

(defn solve2 [in]
  (let [n (count in)
        m (count (first in))]
    (sum (for [i (range m) j (range n)] (dfs2 i j 0 in n m)))))

;;(println parsedTest)
;;(println (solve1 parsedTest))
(println (solve1 parsedInput))
;;(println (solve2 parsedTest))
(println (solve2 parsedInput))
