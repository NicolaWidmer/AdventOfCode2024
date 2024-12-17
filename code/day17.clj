(ns code.day17
  (:require
   [clojure.string :as str]
   [clojure.math :as math]))

(def input (slurp "inputs/day17.in"))

(def testin
  "Register A: 2024
Register B: 0
Register C: 0

Program: 0,3,5,4,3,0")

(defn debug [x]
  (println x)
  x)

(defn parse [data]
  (let [split (str/split data #"\n\n")
        regs (map Integer/parseInt (rest (re-matches #"Register A: (\d+)\nRegister B: (\d+)\nRegister C: (\d+)"  (first split))))
        prog (map Integer/parseInt (str/split (second (str/split (second split) #": ")) #","))]
       (vector regs prog)))

(def parsedInput (parse input))
(def parsedTest (parse testin))

(defn sum [x]
  (reduce + x))

(defn operand [regs lst instr]
  (let [code (nth lst (+ 1 instr))]
    (if (< code 4) code 
        (if (< code 7) (nth regs (- code 4)) 0))))

(defn run-prog [[a b c] lst instr steps]
  (if (or (< instr (count lst)) (< 160 steps))
    (let [opc (nth lst instr)
          opr (operand [a b c] lst instr)
          ltr (nth lst (+ 1 instr))]
      (if (= 0 opc)
        (run-prog [(quot a (int (math/pow 2 opr))) b c] lst (+ 2 instr) (+ steps 1))
        (if (= 1 opc)
          (run-prog [a (bit-xor b ltr) c] lst (+ 2 instr) (+ steps 1))
          (if (= 2 opc)
            (run-prog [a (mod opr 8) c] lst (+ 2 instr) (+ steps 1))
            (if (= 3 opc)
              (if (= 0 a) (run-prog [a b c] lst (+ 2 instr) (+ steps 1)) (run-prog [a b c] lst ltr (+ steps 1)))
              (if (= 4 opc)
                (run-prog [a (bit-xor b c) c] lst (+ 2 instr) (+ steps 1))
                (if (= 5 opc)
                  (conj (run-prog [a b c] lst (+ 2 instr) (+ steps 1)) (mod opr 8))
                  (if (= 6 opc)
                    (run-prog [a (quot a (int (math/pow 2 opr))) c] lst (+ 2 instr) (+ steps 1))
                    (run-prog [a b (quot a (int (math/pow 2 opr)))] lst (+ 2 instr) (+ steps 1))))))))))
    []))

(defn is-pos? [prog a nums]
  (= (vec (take nums (rest (reverse prog)))) (rest (run-prog [a 0 0] prog 0 0))))

(defn all-potential [cur]
  (map #(+ (* 8 cur) %) (range 8)))

(defn find-next-pos [prog curs nums]
  (filter #(is-pos? prog % nums) (flatten (map all-potential curs))))

(defn solve1 [in]
  (let [regs (first in)
        prog (second in)]
    (clojure.string/join ","(reverse (run-prog regs prog 0 0)))))

(defn solve2 [in]
  (let [prog (second in)] 
    (apply min (reduce #(find-next-pos prog %1 %2) [0] (range (count prog))))))

;;(println (solve1 parsedTest))
(println (solve1 parsedInput))
;;(println (solve2 parsedTest))
(println (solve2 parsedInput))
