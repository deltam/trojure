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
       (redirect (str "/entry/" (:rid (rand-entry)))))
  (GET "/entry/:rid" [rid]
       (main-body (get-entry rid)))
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