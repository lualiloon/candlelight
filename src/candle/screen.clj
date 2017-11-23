(ns candle.screen
  (:import com.googlecode.lanterna.screen.TerminalScreen
           com.googlecode.lanterna.TextColor
           com.googlecode.lanterna.TextCharacter
           com.googlecode.lanterna.SGR)
  (:require [candle.terminal :as t]
            [candle.text-graphics :as g]
            [candle.utils :as u]))

;; TODO:
;;   We can't output directly to the screen, we have to create a text-graphics
;;   object and interface with that. Because of this, when a screen is created
;;   it should create both a screen and a graphics object (perhaps in a map).
;;   All functions would then take the map as input, and interact with the part
;;   it needs.
;;   However, it also needs to make sense to create more graphics objects on
;;   the existing screen and use them too.

(defn new-screen
  ([]
   (new-screen (t/new-terminal))) 
  ([term]
   (TerminalScreen. term)))

(defn start!
  [scr]
  (.startScreen scr))

(defn stop!
  [scr]
  (.stopScreen scr))

(defn hide-cursor!
  [scr]
  (.setCursorPosition scr nil))


(defn get-size
  [scr]
  (let [size (.getTerminalSize scr)
        width (.getColumns size)
        height (.getRows size)]
    [width height]))

(defn clear!
  [scr]
  (.clear scr))

(defn refresh!
  [scr]
  (.refresh scr))

;;; ------------------ For Dev Testing ---------------------

(defonce term (t/new-terminal))

(defonce scr (new-screen term))

(defonce graph (g/new-graphics scr))
