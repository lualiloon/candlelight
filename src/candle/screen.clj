(ns candle.screen
  (:import com.googlecode.lanterna.screen.TerminalScreen
           com.googlecode.lanterna.TextColor
           com.googlecode.lanterna.TextCharacter
           com.googlecode.lanterna.SGR)
  (:require 
   [candle.text-graphics :as g]
   [candle.utils :as u]
   [candle.terminal :as t]))


(defn new-screen
  ([]
   (new-screen (t/new-terminal))) 
  ([term]
   (let [screen (TerminalScreen. term)]
     {:terminal term
      :screen screen
      :graphics (g/new-graphics screen)})))

;; TODO: make sure t/start! is truly idempotent (so it doesn't matter if it was
;;       already started)
(defn start!
  [screen]
  (t/start! (:terminal screen))
  (.startScreen (:screen screen)))

(defn stop!
  [screen]
  (.stopScreen (:screen screen)))

(defn hide-cursor!
  [screen]
  (.setCursorPosition (:screen screen) nil))


(defn get-size
  [screen]
  (let [size (.getTerminalSize (:screen screen))
        width (.getColumns size)
        height (.getRows size)]
    [width height]))


(defn update-size!
  [screen]
  (.doResizeIfNecessary (:screen screen)))

(defn clear!
  [screen]
  (.clear (:screen screen)))

(defn refresh!
  [screen]
  (.refresh (:screen screen)))

(defn write!
  [screen x y string & {:keys [fg bg] :as colors}]
  (g/write! (:graphics screen) x y string :fg fg :bg bg))


;;; ------------------ For Dev Testing ---------------------

;; (defonce scr (new-screen))

