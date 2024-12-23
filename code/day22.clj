(ns code.day22
  (:require
   [clojure.string :as str]))

(def input (slurp "inputs/day22.in"))

(def testin
  "1
2
3
2024")

(defn debug [x]
  (println x)
  x)

(defn parse [data]
  (->> data
       (str/split-lines)
       (map Integer/parseInt)))

(def parsedInput (parse input))
(def parsedTest (parse testin))

(defn sum [x]
  (reduce + x))

(defn step1 [num]
  (mod (bit-xor num (* num 64)) 16777216))
(defn step2 [num]
  (mod (bit-xor num (quot num 32)) 16777216))
(defn step3 [num]
  (mod (bit-xor num (* num 2048)) 16777216))

(defn rec [cur steps]
  (if (= steps 0)
    cur
    (rec (step3 (step2 (step1 cur))) (- steps 1))))

(defn rec2 [cur steps]
  (if (= steps 0)
    '()
    (let [next (step3 (step2 (step1 cur)))]
      (conj (rec2 next (- steps 1)) (- (mod next 10) (mod cur 10))))))

(defn sum-for-target [occurs target]
  (sum (map #(get % target 0) occurs)))

(defn lst-to-occs [[cur difs]]
  (if (< (count difs) 4)
    {}
    (assoc (lst-to-occs [(+ cur (first difs)) (rest difs)]) (take 4 difs) (+ cur (sum (take 4 difs))))))


(defn solve1 [in]
  (sum (map #(rec % 2000) in)))


(defn solve2 [in]
  (let [lst (map vector (map #(mod % 10) in) (map #(rec2 % 2000) in))
        occurs (map lst-to-occs lst)]
    (apply max (for [i (range -9 10)
                    j (range -9 10)
                    k (range -9 10)
                    l (range -9 10)] (sum-for-target occurs [i j k l])))))

(println parsedTest)
;;(println (solve1 parsedTest))
(println (solve1 parsedInput))
(println (solve2 parsedTest))
(println (solve2 parsedInput))
