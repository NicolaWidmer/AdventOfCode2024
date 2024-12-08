(ns code.day08
  (:require
   [clojure.string :as str]
   [clojure.set]))

(def input (slurp "inputs/day08.in"))

(def testin
  "............
........0...
.....0......
.......0....
....0.......
......A.....
............
............
........A...
.........A..
............
............")

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

(defn is-freq [lst]
  (not (= \. (first lst))))

(defn compute-loc [x1 y1 x2 y2]
  (let [dx (- x1 x2)
        dy (- y1 y2)]
    (hash-set (vector (+ x1 dx) (+ y1 dy)) (vector (- x2 dx) (- y2 dy)))))

(defn compute-locs [in]
  (let [lst (second in)]
    (reduce clojure.set/union (for [l1 lst l2 lst] (if (= l1 l2) #{} (compute-loc (first l1) (second l1) (first l2) (second l2)))))))

;; not a generic solution. But the input is nice gcd(dx,dy) is always 1
(defn compute-loc2 [x1 y1 x2 y2]
  (let [dx (- x1 x2)
        dy (- y1 y2)
        s1 (for [i (range 50)] (vector (+ x1 (* i dx)) (+ y1 (* i dy))))
        s2 (for [i (range 50)] (vector (+ x1 (* (- i) dx)) (+ y1 (* (- i) dy))))]
    (set (concat s1 s2))))

(defn compute-locs2 [in]
  (let [lst (second in)]
    (reduce clojure.set/union (for [l1 lst l2 lst] (if (= l1 l2) #{} (compute-loc2 (first l1) (second l1) (first l2) (second l2)))))))

(defn is-in-bound? [x y n m] 
  (and (<= 0 x) (<= 0 y) (< x n) (< y m)))


(defn solve1 [in]
  (let [n (count (nth in 0))
        m (count in)
        locs (for [x (range n) y (range m)]
               (vector (nth (nth in y) x) x y))
        filter-locs (filter is-freq locs)
        freq-to-locs (reduce #(assoc %1 (first %2) (conj (get %1 (first %2) []) (rest %2))) {} filter-locs)]
    (count (filter #(is-in-bound? (first %) (second %) n m) (reduce clojure.set/union (map compute-locs freq-to-locs))))))

(defn solve2 [in]
  (let [n (count (nth in 0))
        m (count in)
        locs (for [x (range n) y (range m)]
               (vector (nth (nth in y) x) x y))
        filter-locs (filter is-freq locs)
        freq-to-locs (reduce #(assoc %1 (first %2) (conj (get %1 (first %2) []) (rest %2))) {} filter-locs)]
    (count (filter #(is-in-bound? (first %) (second %) n m) (reduce clojure.set/union (map compute-locs2 freq-to-locs))))))

;;(println parsedTest)
;;(println (solve1 parsedTest))
(println (solve1 parsedInput))
;;(println (solve2 parsedTest))
(println (solve2 parsedInput))
