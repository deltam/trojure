(defproject trojure "1.0.0-SNAPSHOT"
  :description "Trojure - tropy on clojure"
  :namespaces [trojure.core]
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [compojure "0.4.1"]
                 [hiccup "0.2.6"]
                 [ring/ring-servlet "0.2.1"]
                 [appengine "0.4-SNAPSHOT"]
                 [am.ik/clj-gae-ds "0.3.0-SNAPSHOT"]
                 ]
  :dev-dependencies [[leiningen/lein-swank "1.2.0-SNAPSHOT"]]
  :compile-path "war/WEB-INF/classes"
  :library-path "war/WEB-INF/lib"
  )
