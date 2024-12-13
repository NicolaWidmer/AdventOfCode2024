(ns code.day12
  (:require
   [clojure.string :as str]))

(def input (slurp "inputs/day12.in"))

(def testin
  "AAAAAA
AAABBA
AAABBA
ABBAAA
ABBAAA
AAAAAA")

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

(defn in?
  "true if coll contains elm"
  [coll elm]
  (some #(= elm %) coll))

(defn dfs [g i j cur vis val size]
 (let [n (count g) 
       m (count (first g))
       in-bound (and (<= 0 i) (<= 0 j) (< i m) (< j n))
       has-vis (in? vis (vector i j))]
   (if in-bound 
     (if (= (nth (nth g i) j) cur) 
       (if has-vis 
         (vector vis val size) 
         (let [vis0 (conj vis (vector i j))
               size0 (+ size 1)
               a1 (dfs g (+ i 1) j cur vis0 val size0)
               vis1 (first a1)
               val1 (second a1)
               size1 (second (rest a1))
               a2 (dfs g (- i 1) j cur vis1 val1 size1)
               vis2 (first a2)
               val2 (second a2)
               size2 (second (rest a2))
               a3 (dfs g i (+ j 1) cur vis2 val2 size2)
               vis3 (first a3)
               val3 (second a3)
               size3 (second (rest a3))] 
           (dfs g i (- j 1) cur vis3 val3 size3))) 
       (vector vis (+ val 1) size)) 
     (vector vis (+ val 1) size))))

(defn aux [acc tup g]
  (let [i (first tup)
        j (second tup)
        vis (first acc)
        ans  (second acc)
        curans (dfs g i j (nth (nth g i) j) vis 0 0)]
    (vector (first curans) (+ ans (* (second curans) (second (rest curans)))))
    )
  )

(defn solve1 [in]
  (let [n (count in)
        m (count (first in))]
    (second (reduce #(aux %1 %2 in)  (vector #{} 0) (for [i (range m) j (range n)] (vector i j)))
  )))


(defn is-cur? [g i j cur]
  (let [n (count g) 
        m (count (first g))
        in-bound (and (<= 0 i) (<= 0 j) (< i m) (< j n))]
    (and in-bound (= cur (nth (nth g i) j)))
  ))

(defn check1 [g i j dx cur]
  (if (and (is-cur? g i (+ j 1) cur) (not (is-cur? g (+ i dx) j cur)) (not (is-cur? g (+ i dx) (+ j 1) cur)))
   1
   0))

(defn check2 [g i j dy cur]
  (if (and (is-cur? g (+ i 1) j cur) (not (is-cur? g i (+ j dy) cur)) (not (is-cur? g (+ i 1) (+ j dy) cur)))
    1
    0))

(defn check-pairs [g i j cur]
  (+ (check1 g i j 1 cur) 
     (check1 g i j -1 cur) 
     (check2 g i j 1 cur) 
     (check2 g i j -1 cur)))


(defn dfs2 [g i j cur vis val size pairs]
  (let [n (count g)
        m (count (first g))
        in-bound (and (<= 0 i) (<= 0 j) (< i m) (< j n))
        has-vis (in? vis (vector i j))]
    (if in-bound
      (if (= (nth (nth g i) j) cur)
        (if has-vis
          (vector vis val size pairs)
          (let [vis0 (conj vis (vector i j))
                size0 (+ size 1) 
                pairs0 (+ (check-pairs g i j cur) pairs)
                a1 (dfs2 g (+ i 1) j cur vis0 val size0 pairs0)
                vis1 (first a1)
                val1 (second a1)
                size1 (second (rest a1))
                pairs1 (nth a1 3)
                a2 (dfs2 g (- i 1) j cur vis1 val1 size1 pairs1)
                vis2 (first a2)
                val2 (second a2)
                size2 (second (rest a2))
                pairs2 (nth a2 3)
                a3 (dfs2 g i (+ j 1) cur vis2 val2 size2 pairs2)
                vis3 (first a3)
                val3 (second a3)
                size3 (second (rest a3))
                pairs3 (nth a3 3)]
            (dfs2 g i (- j 1) cur vis3 val3 size3 pairs3)))
        (vector vis (+ val 1) size pairs))
      (vector vis (+ val 1) size pairs))))

(defn aux2 [acc tup g]
  (let [i (first tup)
        j (second tup)
        vis (first acc)
        ans  (second acc)
        curans (dfs2 g i j (nth (nth g i) j) vis 0 0 0)]
    (vector (first curans) (+ ans (* (- (second curans) (nth curans 3)) (second (rest curans)))))))

(defn solve2 [in]
  (let [n (count in)
        m (count (first in))]
    (second (reduce #(aux2 %1 %2 in)  (vector #{} 0) (for [i (range m) j (range n)] (vector i j))))))


(println parsedTest)
;;(println (solve1 parsedTest))
(println (solve1 parsedInput))
;;(println (solve2 parsedTest))
(println (solve2 parsedInput))


