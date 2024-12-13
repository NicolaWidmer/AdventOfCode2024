(ns code.day13
  (:require
   [clojure.string :as str]))

(def input (slurp "inputs/day13.in"))

(def testin
  "Button A: X+94, Y+34
Button B: X+22, Y+67
Prize: X=8400, Y=5400

Button A: X+26, Y+66
Button B: X+67, Y+21
Prize: X=12748, Y=12176

Button A: X+17, Y+86
Button B: X+84, Y+37
Prize: X=7870, Y=6450

Button A: X+69, Y+23
Button B: X+27, Y+71
Prize: X=18641, Y=10279")

(defn debug [x]
  (println x)
  x)

(defn parse [data]
  (->> data
       (#(str/split % #"\n\n"))
       ;;(map Integer/parseInt)
       ;;(map #(map Integer/parseInt (str/split % #" ")))))
       (map #(map Integer/parseInt (rest (re-matches #"Button A: X\+(\d+), Y\+(\d+)\nButton B: X\+(\d+), Y\+(\d+)\nPrize: X=(\d+), Y=(\d+)"  %))))))

(def parsedInput (parse input))
(def parsedTest (parse testin))

(defn sum [x]
  (reduce + x))

(defn devisible? [a b]
  (and (not (= b 0)) (= (/ a b) (quot a b))))

(defn min-or-zero [lst]
  (if (empty? lst) 0 (apply min lst)))

(defn solve-one [[a c b d x y]]
  (let [det (- (* a d) (* c b))
        A (- (* x d) (* y b))
        B (- (* y a) (* x c))
        a-pos (and (devisible? x a) (devisible? y c) (= (quot x a) (quot y c)))
        b-pos (and (devisible? x b) (devisible? y d) (= (quot x b) (quot y d)))]
    (if (= 0 det)
      (min-or-zero (concat (if a-pos [(* 3 (quot x a))] []) (if b-pos [(quot x b)] [])))
      (if (and (devisible? A det) (devisible? B det) (<= 0 (quot A det)) (<= 0 (quot B det)))
        (+ (* (quot A det) 3) (quot B det)) 
        0))))

(defn solve-two [[a c b d x y]]
(solve-one [a c b d (+ x 10000000000000) (+ y 10000000000000)]))


(defn solve1 [in]
  (sum (map solve-one in)))

(defn solve2 [in]
  (sum (map solve-two in)))

;;(println parsedTest)
;;(println (solve1 parsedTest))
(println (solve1 parsedInput))
;;(println (solve2 parsedTest))
(println (solve2 parsedInput))
