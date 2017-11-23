(ns candle.terminal
  (:import
   com.googlecode.lanterna.terminal.Terminal
;;   com.googlecode.lanterna.terminal.swing.SwingTerminal
   com.googlecode.lanterna.terminal.swing.SwingTerminalFrame
;;   com.googlecode.lanterna.terminal.DefaultTerminalFactory
   com.googlecode.lanterna.terminal.swing.TerminalEmulatorAutoCloseTrigger
   com.googlecode.lanterna.TextColor
   com.googlecode.lanterna.SGR))


;;; ------------------- Terminal Layer ----------------------------


;; TODO: add an option to close on escape-keypress
(defn new-terminal
  []
  (SwingTerminalFrame.
   "testing"
   (into-array [TerminalEmulatorAutoCloseTrigger/CloseOnExitPrivateMode])))

(defn text-graphics
  [term]
  (.newTextGraphics term))

(defn rgb
  [r g b]
  (new com.googlecode.lanterna.TextColor$RGB r g b))

(defn start!
  [term]
  (.setVisible term true)
  (.enterPrivateMode term))

(defn stop!
  [term]
  (.setVisible term false)
  (.exitPrivateMode term)
  ;; We don't call this because we set the terminal to close automatically
  ;; when we exit private mode.
  #_(.close term))

(defn hide-terminal!
  [term]
  (.setVisible term false))

(defn unhide-terminal!
  [term]
  (.setVisible term true))


(defn move-to!
  [term x y]
  (.setCursorPosition term x y))

(defn move-relative!
  [term dx dy]
  (let [start (.getCursorPosition term)]
    (.setCursorPosition term (-> start
                                 (.withRelativeRow dy)
                                 (.withRelativeColumn dx)))))

(defn ansi-color
  [color-k]
  (let [color-s (get {:blue "BLUE"
                      :yellow "YELLOW"
                      :white "WHITE"
                      :black "BLACK"
                      :default "DEFAULT"}
                     color-k)]
    (eval
     (symbol
      (str "com.googlecode.lanterna.TextColor$ANSI/" color-s)))))


(defn set-background!
  [term & {:keys [ansi]
           :or {ansi :white}}]
  (.setBackgroundColor term (ansi-color ansi)))

(defn set-foreground!
  [term & {:keys [ansi]
           :or {ansi :white}}]
  (.setForegroundColor term (ansi-color ansi)))

(defn sgr-style
  [style-k]
  (get {:bold SGR/BOLD}
       style-k))

(defn set-style!
  [term style-k]
  (.enableSGR term (sgr-style style-k)))


;; TODO: figure out how to get the current cursor visibility, and combine 
;;       hide-cursor! and show-cursor! into a single toggle-cursor!
(defn hide-cursor!
  [term]
  (.setCursorVisible false))

(defn show-cursor!
  [term]
  (.setCursorVisible true))


(defn put-char!
  [term c]
  (.putCharacter term c))

(defn put-string!
  [graphics x y string & {:keys [style]}]
  (if style
    (.putString graphics x y string (sgr-style style))
    (.putString graphics x y string)))

(defn redraw!
  [term]
  (.flush term))

(defn write!
  [term char-or-string]
  (if (seq char-or-string)
    (put-string! term char-or-string)
    (put-char! term char-or-string))
  (redraw! term))

(defn clear!
  [term]
  (.clearScreen term)
  (redraw! term))


;;; --------------------- For Dev Testing ------------------------

;; (defonce term (new-terminal))
;; (defonce g (text-graphics term))
