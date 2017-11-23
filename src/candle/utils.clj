(ns candle.utils
  (:import com.googlecode.lanterna.TextColor
           com.googlecode.lanterna.SGR))


;; Changing this namespace to "candle.colors" may make it more intuitive for
;; other people to use.


(defn ansi-color
  [color-k]
  (let [color-s (get {:black "BLACK"
                      :blue "BLUE"
                      :cyan "CYAN"
                      :green "GREEN"
                      :magenta "MAGENTA"
                      :red "RED"
                      :white "WHITE"
                      :yellow "YELLOW"
                      :default "DEFAULT"}
                     color-k)]
    (eval
     (symbol
      (str "com.googlecode.lanterna.TextColor$ANSI/" color-s)))))

(defn rand-ansi
  []
  (ansi-color (rand-nth [:black :blue :cyan :green
                         :magenta :red :white :yellow])))

(defn rgb
  [r g b]
  (new com.googlecode.lanterna.TextColor$RGB r g b))


(defn sgr-style
  [style-k]
  (get {:bold SGR/BOLD}
       style-k))
