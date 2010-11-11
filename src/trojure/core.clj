(ns trojure.core
  (:gen-class
   :extends javax.servlet.http.HttpServlet)
  (:use [compojure.core :only [defroutes GET POST PUT]]
        [ring.util.servlet :only (defservice)]
        [ring.util.response :only (redirect)]
        [trojure io forms])
  (:require [compojure.route :as route]))


(defroutes trojure-public
  (GET "/" []
       (main-body (rand-entry)))
;       (str (rand-entry)))
;       (main-body {:id 1 :text "test"}))
  (GET "/entry/:rid" [rid]
       (main-body (get-entry rid)))
;       (for [e (get-entries)] (str (e :text) "<br/>")))
  (GET "/new" []
       (create-entry))
  (POST "/save" [text]
        (let [new-rid (create-action text)]
          (redirect (str "/entry/" new-rid))))
  (GET "/edit/:rid" [rid]
       (update-entry rid ((get-entry rid) :text)))
  (PUT "/update" [rid text]
       (update-action rid text)
       (redirect (str "/entry/" rid))))

(defroutes trojure
  trojure-public
  (route/not-found "Page not Found")) ;404


(defservice trojure)