(ns trojure.forms
  (:use [hiccup.core :only [html escape-html]]
        [hiccup.form-helpers]
        [ring.util.response :only (redirect)]
        ))


(defn hdoc [rid body]
  (html [:h1 "Trojure - tropy on clojure"]
        [:div {:align "right"}
         [:a {:href "/new"} "Create "]
         [:a {:href (str "/edit/" rid)} "Edit "]
         [:a {:href "/"} "Random"]]
        [:div {:align "left"}
         [:blockquote {:style "border-style:solid"}
          body
          [:br]
          [:a {:href (str "/entry/" rid)} "Permalink"]]]
        [:div {:align "right"}
         "maked by " [:a {:href "http://twitter.com/deltam"} "@deltam"]]))


(defn main-body [entry]
  (let [text (escape-html (entry :text))]
    (hdoc (entry :rid)
          (html [:pre text]))))


(defn create-entry []
  (hdoc ""
        (form-to [:post "/save"]
                 (text-area {:rows 10 :cols 50} :text)
                 (submit-button "Post"))))


(defn update-entry [rid text]
  (hdoc ""
        (form-to [:put "/update"]
                 (text-area {:rows 10 :cols 50} :text text)
                 (hidden-field :rid rid)
                 (submit-button "Update"))))