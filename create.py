import sys

day = sys.argv[1]

f = open(f"code/day{day}.clj", "w")

code = f"""(ns code.day{day}
  (:require
   [clojure.string :as str]))

(def input (slurp \"inputs/day{day}.in\"))

(def testin
  \"\")

(defn debug [x]
  (println x)
  x)

(defn parse [data]
  (->> data
       (str/split-lines)
       ;;(map Integer/parseInt)
       (map #(map Integer/parseInt (rest (re-matches #\"(\\d+)   (\\d+)\"  %))))))

(def parsedInput (parse input))
(def parsedTest (parse testin))

(defn sum [x]
  (reduce + x))


(defn solve1 [in]
  )

(defn solve2 [in]
  )

(println (solve1 parsedInput))
(println (solve2 parsedInput))
;;(println (solve1 parsedInput))
;;(println (solve2 parsedInput))
"""
print(code)

f.write(code)
f.close()

f = open(f"inputs/day{day}.in", "w")