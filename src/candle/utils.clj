(ns candle.utils
  (:import com.googlecode.lanterna.TextColor))


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
