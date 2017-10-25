(defproject luhhujbb/webhdfs-clj "0.1.3"
            :jvm-opts [;"-Dsun.security.krb5.debug=true"
                       "-Djava.security.auth.login.config=conf/headless.conf"]
            :resource-paths ["conf"]
            :description "Clojure client library for WebHDFS Rest API"
            :url "https://github.com/luhhujbb/webhdfs-clj"
            :license {:name "MIT License"
                      :url "https://opensource.org/licenses/mit-license.php"}
            :dependencies [[clj-http-lite "0.3.0"]
                           [org.clojure/data.json "0.2.6"]
                           [org.clojure/tools.logging "0.4.0"]
                           [org.clojure/clojure "1.8.0"]])
