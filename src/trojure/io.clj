(ns trojure.io
  (:use [clojure.contrib.seq :only (rand-elt)]
        [am.ik.clj-gae-ds.core]))


(defn- rnd-id []
  (rand-int 99999999))


(defn- to-map [entity]
  {:rid  (get-prop entity :rid)
   :text (get-prop entity :text)
   :date (get-prop entity :date)})


(defn add-entry [text]
  (let [entity (map-entity "Entry"
                           :rid (str (rnd-id))
                           :text text
                           :date (java.util.Date.))]
    (ds-put entity)
    (:rid (to-map entity))))


(defn get-entries []
  (map to-map (query-seq (q "Entry"))))


(defn rand-entry []
  (rand-elt (get-entries)))


(defn get-entry [rid]
  (some #(if (= rid (:rid %)) %)
        (map to-map (query-seq (q "Entry")))))


(defn create-action [text]
  (add-entry text))


(defn update-action [rid text]
  (println rid text))