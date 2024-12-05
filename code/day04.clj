(ns code.day04
  (:require
   [clojure.string :as str]))

(def input (slurp "inputs/day04.in"))

(def testin
  "MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX")

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

(defn get-or-star [x y ll]
  (nth (nth ll y []) x "*"))

(defn check1 [x y dx dy ll word]
  (if (empty? word) true (and (= (get-or-star x y ll) (first word)) (check1 (+ x dx) (+ y dy) dx dy ll (rest word)))))

(defn check-diag [chr1 chr2]
  (or (and (= chr1 \M) (= chr2 \S)) (and (= chr1 \S) (= chr2 \M))))

(defn check2 [x y ll]
  (let [mid (get-or-star x y ll)
        chr1 (get-or-star (- x 1) (- y 1) ll)
        chr2 (get-or-star (+ x 1) (- y 1) ll)
        chr3 (get-or-star (+ x 1) (+ y 1) ll)
        chr4 (get-or-star (- x 1) (+ y 1) ll)]
   (and (= mid \A) (check-diag chr1 chr3) (check-diag chr2 chr4))))

(defn solve1 [in]
  (let [n (count (nth in 0))
        m (count in)
        check-ll (for [x (range n) y (range m) dx (range -1 2) dy (range -1 2)]
                   (check1 x y dx dy in "XMAS"))]
    (count (filter identity check-ll))))

(defn solve2 [in]
   (let [n (count (nth in 0))
         m (count in)
         check-ll (for [x (range n) y (range m)]
                    (check2 x y in))]
     (count (filter identity check-ll))))

;;(println parsedTest)
;;(println (solve1 parsedTest))
(println (solve1 parsedInput))
;;(println (solve2 parsedTest))
(println (solve2 parsedInput))
