(ns webhdfs-clj.util
  (:use [clojure.java.io :as io])
  (:import [java.io PushbackReader]))

(def ^:private cfg-state (atom nil))

(defn init?
  []
  (nil? @cfg-state))

(defn cfg
  ([] @cfg-state)
  ([key] (get @cfg-state key)))

(defn base-url []
  (let [{:keys [host port]} (cfg)]
    (str "http://" host ":" port "/webhdfs/v1")))

;;Config loader
(def swap-cfg!
  (partial swap! cfg-state))

(defn reset-cfg!
  [config]
  (reset! cfg-state config))
