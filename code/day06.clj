(ns code.day06
  (:require
   [clojure.string :as str]))
(def input (slurp "inputs/day06.in"))

(def testin
  "....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#...")

(defn debug [x]
  (println x)
  x)

(defn parse [data]
  (->> data
       (str/split-lines)))

(def parsedInput (parse input))
(def parsedTest (parse testin))

(defn sum [x]
  (reduce + x))

(defn is-start [x y ll]
  (= (nth (nth ll y) x) \^))

(defn is-obst [x y ll]
  (= (nth (nth ll y) x) \#))

(defn is-obst2 [x y x-obst y-obst ll]
  (or (= (nth (nth ll y) x) \#)
      (and (= x x-obst) (= y y-obst))))

(defn find-start [ll]
  (let [n (count ll)
        m (count (first ll))]
  (first (filter #(is-start (nth % 0) (nth % 1) ll) (for [x (range m) y (range n)] (vector x y))))))

(def x-dir
  '(0 1 0 -1))

(def y-dir
  '(-1 0 1 0))

(defn step [loc ll]
  (let [n (count ll)
        m (count (first ll))
        x (nth loc 0)
        y (nth loc 1)
        dir (nth loc 2)
        next-x (+ x (nth x-dir dir))
        next-y (+ y (nth y-dir dir))
        in-bound (and (<= 0 next-x) (<= 0 next-y) (< next-x m) (< next-y n))]
    (if in-bound 
      (conj (if (is-obst next-x next-y ll) 
        (step (vector x y (mod (+ dir 1) 4)) ll) 
        (step (vector next-x next-y dir) ll)) (vector x y))
      (hash-set (vector x y)))
    ))

(defn has-loop? [loc s x-obst y-obst ll]
  (let [n (count ll)
        m (count (first ll))
        x (nth loc 0)
        y (nth loc 1)
        dir (nth loc 2)
        next-x (+ x (nth x-dir dir))
        next-y (+ y (nth y-dir dir))
        in-bound (and (<= 0 next-x) (<= 0 next-y) (< next-x m) (< next-y n))]
    (if (contains? s loc)
      true
    (if in-bound
      (if (is-obst2 next-x next-y x-obst y-obst ll)
              (has-loop? (vector x y (mod (+ dir 1) 4)) (conj s loc) x-obst y-obst ll)
              (has-loop? (vector next-x next-y dir) (conj s loc) x-obst y-obst ll))
      false))))


(defn solve1 [in]
  (let [start (find-start in)
        x (nth start 0)
        y (nth start 1)]
     (count (step (vector x y 0) in))
  ))

(defn solve2 [in]
  (let [start (find-start in)
        n (count in) 
        m (count (first in)) 
        x (nth start 0) 
        y (nth start 1)]
     (count (filter identity (for [i (range m) j (range n)] (has-loop? (vector x y 0) #{} i j in))))))




;;(println parsedTest)
;;(println (solve1 parsedTest))
(println (solve1 parsedInput))
;;(println (solve2 parsedTest))
(println (solve2 parsedInput))
