(ns webhdfs-clj.cli
  (:require [clojure.tools.cli :refer [parse-opts]]
            [webhdfs-clj.core :as wdfs]
            [clojure.tools.logging :as log]
            [clojure.java.io :as io])
  (:gen-class))

(def actions
  [:status :delete :copy-to-hdfs :copy-from-hdfs :mkdir :rmdir :append :chown])

(def cli-options
  [["-c" "--config CONFIG" "Config File"
    :default nil
    :parse-fn #(load-file %)
    :validate [#(map? %) "Must be an edn or clj file"]]
    [nil "--src FROM_PATH" "src path"
     :default "/"]
    [nil "--dest TO_PATH" "target path"
       :default "/"]
    [nil "--permission Permission" "Permission of files"
       :default nil]
    [nil "--replication Replication" "replication factor"
       :default "3"]
    [nil "--owner Owner" "owner"
     :default nil]
    [nil "--group Group" "group"
     :default nil]
    ["-r" "--recursive"
       :default "false"]
    [nil "--block-size" "File block-size"
    :parse-fn #(java.lang.Long/parseLong %)
     :default 134217728]
    ["-a" "--action ACTION" "Action"
       :default :status
       :parse-fn #(keyword %)
       :validate [(fn [x] (some #(= x %) actions)) (str "Must be in :" actions)]]
    ["-h" "--help"]])

(defn- clean-map
  [my-map]
  (mapcat identity (remove (comp nil? second) my-map)))

(defn -main [& args]
  (let [opts (parse-opts args cli-options)
        options (:options opts)]
  (if (:config options)
    (do
      (wdfs/init! (:config options))
      (condp = (:action options)
        :status (println (wdfs/get-file-status (:src options)))
        :copy-to-hdfs (apply
                        wdfs/create
                        (:dest options)
                        (io/file (:src options))
                        (clean-map {:permission (:permission options)
                                    :replication (:replication options)
                                    :block-size (:block-size options)}))
        :copy-from-hdfs (with-open [in (wdfs/open (:src options))]
                          (io/copy in (io/file (:dest options))))
        :append (wdfs/append
                  (:dest options)
                  (io/file (:src options)))
        :mkdir (apply wdfs/mkdir (:src options) (clean-map {:permission (:permission options)}))
        :delete (wdfs/delete (:src options))
        :chown (apply wdfs/set-owner (:src options) (clean-map {:owner (:owner options)
                                                                 :group (:group options)}))))
      (do
        (println (:summary opts))
        (println (:options opts))))))
