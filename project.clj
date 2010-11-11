(defproject trojure "1.0.0-SNAPSHOT"
  :description "Trojure - tropy on clojure"
  :namespaces [trojure.core]
  :repositories {"maven.seasar.org" "http://maven.seasar.org/maven2"}
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [compojure "0.4.0"]
                 [am.ik/clj-gae-ds "0.2.1"]
                 [com.google.appengine/appengine-api-1.0-sdk "1.3.5"]
                 [ring/ring-core "0.2.3"]
                 [ring/ring-servlet "0.2.3"]
                 [ring/ring-jetty-adapter "0.2.3"]
                 [hiccup/hiccup "0.2.6"]
                 ]
  :dev-dependencies [[am.ik/clj-gae-testing "0.2.0-SNAPSHOT"]
                     [leiningen/lein-swank "1.2.0-SNAPSHOT"]]
  :compile-path "war/WEB-INF/classes"
  :library-path "war/WEB-INF/lib"
  )
