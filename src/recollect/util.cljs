
(ns recollect.util )

(defn =seq [xs ys]
  (if (empty? xs)
    (if (empty? ys) true false)
    (if (empty? ys)
      false
      (if (identical? (first xs) (first ys))
        (if (and (fn? (first xs)) (fn? (first ys)))
          (do (comment "functions changes designed to be ignored.") true)
          (recur (rest xs) (rest ys)))
        false))))

(defn type->int [x]
  (cond
    (number? x) 0
    (keyword? x) 1
    (string? x) 2
    (nil? x) 3
    :else (throw (js/Error. (str "Failed to compare, it's: " (pr-str x))))))

(defn compare-more [x y]
  (let [type-x (type->int x), type-y (type->int y)]
    (if (= type-x type-y) (compare x y) (compare type-x type-y))))

(defn literal? [x] (not (coll? x)))

(defn seq-add' [xs' ys] (if (empty? xs') ys (recur (rest xs') (cons (first xs') ys))))

(defn seq-add [xs ys] (seq-add' (reverse xs) ys))

(defn vec-add [xs ys] (if (empty? ys) xs (recur (conj xs (first ys)) (rest ys))))
