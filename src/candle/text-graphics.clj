(ns candle.text-graphics)


(defn new-graphics
  [term-or-scr]
  (.newTextGraphics term-or-scr))

(defn set-foreground!
  [graphics color]
  (.setForegroundColor graphics color))


(defn set-background!
  [graphics color]
  (.setBackgroundColor graphics color))


(defn put!
  [graphics x y string]
  (.putString graphics x y string))
