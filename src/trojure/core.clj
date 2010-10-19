(ns trojure.core
  (:gen-class)
    (:use [compojure.core]
          [hiccup.core]
          [hiccup.form-helpers]
          [ring.util.response :only (redirect)]
          [ring.adapter.jetty :only (run-jetty)]
          [clojure.contrib.duck-streams :only (reader writer)]
          [clojure.contrib.seq-utils :only (rand-elt)]))

(defn rand-id []
  (str (rand-int 999999999)))

(defstruct entry :id :text)
(def entries (ref []))

(defn add-entry [text]
  (let [id (rand-id)]
    (dosync (alter entries conj (struct entry id text)))))

;; test data
(def log_file "log.txt")
(add-entry "hello world")
(add-entry "clojure さいこうじゃー")
(add-entry "Perlはぺるるとよむ")
(add-entry "trojure = tropy + clojure")

(defn file->sexp-list []
  (let [r (java.io.PushbackReader. (reader log_file))]
    (take-while identity
      (repeatedly #(or (read r false false) (.close r))))))

(defn out-log []
  (with-open [w (writer log_file)]
      (doseq [et @entries]
        (binding [*out* w] (prn et)))))


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

(defn update-entry [id text]
  (html-doc ""
            (form-to [:put "/update"]
                     (text-area {:rows 10 :cols 50} :text text)
                     (hidden-field :id id)
                     (submit-button "Update"))))

(defn create-action [id text]
  (let [new-entry (struct entry id text)]
    (dosync
     (alter entries conj new-entry))))

(defn update-action [id text]
  (println id text))

(defroutes main-routes
  (GET "/" []
       (main-body (rand-elt @entries)))
  (GET "/entry/:id" [id]
       (main-body (get-entry id)))
  (GET "/new" []
       (create-entry))
  (POST "/save" [text]
        (let [id (rand-id)]
          (create-action id text)
          (out-log)
          (redirect (str "/entry/" id))))
  (GET "/edit/:id" [id]
       (update-entry id ((get-entry id) :text)))
  (PUT "/update" [id text]
       (update-action id text)
       (redirect (str "/entry/" id)))
  (ANY "*" []
       {:status 404, :body "<h1>Page not found</h1>"}))


(run-jetty main-routes {:port 8080})


