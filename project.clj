(defproject webhdfs-clj "0.1.1"
            :jvm-opts [;"-Dsun.security.krb5.debug=true"
                       "-Djava.security.auth.login.config=conf/headless.conf"]
            :resource-paths ["conf"]
            :description "Clojure client library for WebHDFS Rest API"
            :dependencies [[clj-http-lite "0.3.0"]
                           [org.clojure/data.json "0.2.5"]
                           [org.clojure/tools.logging "0.3.1"]
                           [org.clojure/clojure "1.6.0"]])
