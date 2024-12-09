(ns code.day09
  (:require
   [clojure.string :as str]))

(def input (slurp "inputs/day09.in"))

(def testin
  "2333133121414131402")

(defn debug [x]
  (println x)
  x)

(defn parse [data]
  (->> data
       (map #(Character/digit % 10))))

(def parsedInput (parse input))
(def parsedTest (parse testin))

(defn sum [x]
  (reduce + x))

(defn list-update-in [l i x]
  (let [newlist (take i l)
        newlist (concat newlist (list x))
        newlist (concat newlist (drop (+ 1 i) l))]
    newlist))

(defn euler-sum [start len]
  (+ (* start len) (quot (* len (- len 1)) 2)))

(defn rec1 [lst ind id]
  (let [n (count lst)
        last-id (+ id (quot n 2))
        last (nth lst (- n 1))]
    ;;(debug lst)
    ;;(println id)
    (if (< (count lst) 3)
      (* id (euler-sum ind (first lst)))
      (if (= (first lst) 0)
        (if (< (second lst) last)
          (+ (* last-id (euler-sum ind (second lst))) (rec1 (rest (rest (list-update-in lst (- n 1) (- last (second lst))))) (+ ind (second lst)) (+ id 1)))
          (+ (* last-id (euler-sum ind last)) (rec1 (list-update-in (reverse (rest (rest (reverse lst)))) 1 (- (second lst) last)) (+ ind last) id)))
        (+ (* id (euler-sum ind (first lst))) (rec1 (list-update-in lst 0 0) (+ ind (first lst)) id))))))


(defn solve1 [in]
  (rec1 in 0 0)
  )

(defn make-explixit [ind lst] 
  (if (= (count lst) 1)
    (repeat (first lst) ind)
    (concat (repeat (first lst) ind) (repeat (second lst) -1) (make-explixit (+ ind 1) (rest (rest lst))))
  ))

(defn make-explixit [ind lst]
  (if (= (count lst) 1)
    (vector (vector false (first lst) ind))
    (concat (vector (vector false (first lst) ind) (vector true (second lst) -1)) (make-explixit (+ ind 1) (rest (rest lst))))))

(defn is-free [[pro num ind]]
  (= ind -1))

(defn clean-up [lst zeros]
  (println lst)
  (println zeros)
  (if (empty? lst) []
      ( let [fst (first lst)]
       (if (is-free fst)
         (clean-up (rest lst) (+ zeros (second fst)))
         (if (= zeros 0)
           (concat (vector fst) (clean-up (rest lst) 0))
           (concat (vector (vector true zeros -1) fst) (clean-up (rest lst) 0))
         )))))

(defn step2 [cur-lst cur]
  (let [new (list-update-in cur 0 true)]
   (if (empty? cur-lst)
    (vector new) 
     (let [len (second cur)
           fst (first cur-lst)
           cur-len (second fst)]
       (if (and (is-free fst) (<= len cur-len))
         (concat (vector new (list-update-in fst 1 (- cur-len len))) (concat (rest cur-lst) (vector (vector true len -1))))
         (concat (vector fst) (step2 (rest cur-lst) cur)))
    ))))

(defn step [cur-lst cur]
  (clean-up (step2 cur-lst cur) 0))

(defn process [lst]
  (let [rev (reverse lst)] 
    (if (empty? rev)
      rev (let [fst (first rev)
                rst (rest rev)]
            (if (first fst) (concat (process (reverse rst)) (vector fst))
                (process (step2 (reverse rst) fst)))
           ))))

(defn sum2 [lst ind]
  (if (empty? lst) 0 
      (let [fst (first lst)]
        (if (is-free fst)
          (sum2 (rest lst) (+ ind (second fst)))
          (+ (* (nth fst 2) (euler-sum ind (second fst))) (sum2 (rest lst) (+ ind (second fst)))) )
        ))
  )

(defn find-loc [ind lst cur]
  (if (= (first lst) ind) -1
      ())
  )


(defn solve2 [in]
  (let [exp (make-explixit 0 in)] 
    ;;(process exp)
    (sum2 (process exp) 0)))

;;(println parsedTest)
;;(println (solve1 parsedTest))
(println (solve1 parsedInput))
;;(println (solve2 parsedTest))
(println (solve2 parsedInput))
