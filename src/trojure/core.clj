(ns trojure.core
    (:use [compojure.core]
          [hiccup.core]
          [hiccup.form-helpers]
          [ring.util.response :only (redirect)]
          [ring.adapter.jetty :only (run-jetty)]
          [clojure.contrib.seq-utils :only (rand-elt)]))

(defn rand-id []
  (str (rand-int 999999999)))

(defstruct entry :id :text)
(def entries (ref []))

(defn add-entry [text]
  (let [id (rand-id)]
    (dosync (alter entries conj (struct entry id text)))))

;; test data
(add-entry "hello world")
(add-entry "clojure さいこうじゃー")
(add-entry "Perlはぺるるとよむ")
(add-entry "trojure = tropy + clojure")

(defn get-entry [id]
  (some #(if (= id (% :id)) %) @entries))

(defn html-doc [id body]
  (html [:h1 "Trojure - tropy on clojure"]
        [:div {:align "right"}
         [:a {:href "/new"} "Create "]
         [:a {:href (str "/edit/" id)} "Edit "]
         [:a {:href "/"} "Random"]]
        [:div {:align "left"}
         [:blockquote {:style "border-style:solid"}
          body
          [:br]
          [:a {:href (str "/entry/" id)} "Permalink"]]]))

(defn main-body [entry]
  (let [text (entry :text)]
    (html-doc (entry :id)
              (html [:pre (entry :text)]))))

(defn create-entry []
  (html-doc ""
            (form-to [:post "/save"]
                     (text-area {:rows 10 :cols 50} :text)
                     (submit-button "Post"))))

(defn create-action [id text]
  (let [new-entry (struct entry id text)]
    (dosync
     (alter entries conj new-entry))))

(defroutes main-routes
  (GET "/" []
       (main-body (rand-elt @entries)))
  (GET "/entry/:id" [id]
       (main-body (get-entry id)))
  (GET "/new" []
       (create-entry))
  (GET "/edit/:id" [id]
        (println "edit id " id))
  (POST "/save" [text]
        (let [id (rand-id)]
          (create-action id text)
          (redirect (str "/entry/" id))))
  (ANY "*" []
       {:status 404, :body "<h1>Page not found</h1>"}))


(run-jetty main-routes {:port 8080})


