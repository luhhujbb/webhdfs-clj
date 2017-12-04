(ns webhdfs-clj.util
  (:use [clojure.java.io :as io])
  (:import [java.io PushbackReader]))

(def ^:private cfg-state (atom nil))

(defn octal->human
  [octal]
    (let [read (if (#{"4" "5" "6" "7"} (str octal))
                "r"
                "-")
          write (if (#{"2" "3" "6" "7"} (str octal))
                    "w"
                    "-")
          execute (if (#{"1" "3" "5" "7"} (str octal))
                    "x"
                    "-")]
    (str read write execute)))

(defn octals->human
  [octals]
  (apply str (doall (map octal->human (str octals)))))

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
