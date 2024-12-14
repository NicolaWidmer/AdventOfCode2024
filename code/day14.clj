(ns code.day14
  (:require
   [clojure.string :as str]))

(def input (slurp "inputs/day14.in"))

(def testin
  "p=0,4 v=3,-3
p=6,3 v=-1,-3
p=10,3 v=-1,2
p=2,0 v=2,-1
p=0,0 v=1,3
p=3,0 v=-2,-2
p=7,6 v=-1,-3
p=3,0 v=-1,-2
p=9,3 v=2,3
p=7,3 v=-1,2
p=2,4 v=2,-3
p=9,5 v=-3,-3")

(defn debug [x]
  (println x)
  x)

(defn parse [data]
  (->> data
       (str/split-lines)
       ;;(map Integer/parseInt)
       ;;(map #(map Integer/parseInt (str/split % #" ")))))
       (map #(map Integer/parseInt (rest (re-matches #"p=(\-?\d+),(\-?\d+) v=(\-?\d+),(\-?\d+)"  %))))))

(def parsedInput (parse input))
(def parsedTest (parse testin))

(defn sum [x]
  (reduce + x))

(defn solve-one [[x y dx dy]]
  (let [dim1 101
        dim2 103
        rounds 100
        final-x (mod (+ x (* rounds dx)) dim1)
        final-y (mod (+ y (* rounds dy)) dim2)
        x-above (< final-x (quot dim1 2))
        x-below (> final-x (quot dim1 2))
        y-above (< final-y (quot dim2 2))
        y-below (> final-y (quot dim2 2))
        ]
    (if (and x-below y-below)
      1
      (if (and x-below y-above)
        2
        (if (and x-above y-below)
          3
          (if (and x-above y-above)
            4
            0))))))

(defn how-many? [lst x]
  (count (filter #(= x %) lst)))

(defn solve1 [in]
  (let [curans (map solve-one in)]
    (* (how-many? curans 1) (how-many? curans 2) (how-many? curans 3) (how-many? curans 4))))

(defn steps [[x y dx dy] rounds]
  (let [dim1 101
       dim2 103
       final-x (mod (+ x (* rounds dx)) dim1)
       final-y (mod (+ y (* rounds dy)) dim2)]
    (vector final-x final-y)))

(defn line-to-str [cells x dim2]
  (apply str (for [y (range dim2)]
                         (if (contains? cells [x y]) "#" "."))))

(defn cells-to-string [cells]
  (let [dim1 101
        dim2 103]
  (for [x (range dim1)]
    (line-to-str cells x dim2))))

(defn print-g [grid]
  (map println grid))

(defn solve2 [in]
  (print-g (cells-to-string (set (map #(steps % 6876) in)))))

(println parsedTest)
;;(println (solve1 parsedTest))
(println (solve1 parsedInput))
;;(println (solve2 parsedTest))
(count (solve2 parsedInput))