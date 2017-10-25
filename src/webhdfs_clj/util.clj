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

(defn switch-nn []
  []
  (when-let [hosts (cfg :hosts)]
    (if (= (cfg :host) (first hosts))
      (swap-cfg! assoc :host (last hosts))
      (swap-cfg! assoc :host (first hosts)))))

(defn reset-cfg!
  [config]
  (reset! cfg-state config))
