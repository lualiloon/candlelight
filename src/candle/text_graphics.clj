(ns candle.text-graphics
  (:require [candle.utils :as u]))


(defn new-graphics
  [term-or-scr]
  (.newTextGraphics term-or-scr))

(defn set-foreground!
  [graphics color]
  (.setForegroundColor graphics color))

(defn set-background!
  [graphics color]
  (.setBackgroundColor graphics color))

#_(defn put-string!
  [graphics x y string & {:keys [style]}]
  (if style
    (.putString graphics x y string (sgr-style style))
    (.putString graphics x y string)))

(defn put!-
  [graphics x y string]
  (.putString graphics x y string))

(defn write!
  [graphics x y string
   & {:keys [fg bg]
      :or {fg (u/ansi-color :white)
           bg (u/ansi-color :black)}}]
  (set-foreground! graphics fg)
  (set-background! graphics bg)
  (put!- graphics x y string))
