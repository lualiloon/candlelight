(ns candle.terminal
  (:import
   com.googlecode.lanterna.terminal.Terminal
   com.googlecode.lanterna.terminal.swing.SwingTerminalFrame
   com.googlecode.lanterna.terminal.swing.TerminalEmulatorAutoCloseTrigger)
  (:require
   [candle.utils :as u]))


;; NOTE: You can use text-graphics with the terminal.


;;; ------------------- Terminal Layer ----------------------------


;; TODO: add an option to close on escape-keypress
(defn new-terminal
  []
  (SwingTerminalFrame.
   "testing"
   (into-array [TerminalEmulatorAutoCloseTrigger/CloseOnExitPrivateMode])))


(defn start!
  [term]
  (.setVisible term true)
  (.enterPrivateMode term))

(defn stop!
  [term]
  (.setVisible term false)
  (.exitPrivateMode term)
  ;; We don't call (.close term) because we set the terminal to close
  ;; automatically when we exit private mode.
  )

(defn move-to!
  [term x y]
  (.setCursorPosition term x y))

(defn move-relative!
  [term dx dy]
  (let [start (.getCursorPosition term)]
    (.setCursorPosition term (-> start
                                 (.withRelativeRow dy)
                                 (.withRelativeColumn dx)))))


(defn set-background!
  [term & {:keys [color]
           :or {color (u/ansi-color :black)}}]
  (.setBackgroundColor term color))

(defn set-foreground!
  [term & {:keys [color]
           :or {color (u/ansi-color :white)}}]
  (.setForegroundColor term color))


(defn set-style!
  [term style-k]
  (.enableSGR term (u/sgr-style style-k)))


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
  [term string]
  (doseq [c string]
    (put-char! term c)))

(defn write!
  [term char-or-string]
  (if (seq char-or-string)
    (put-string! term char-or-string)
    (put-char! term char-or-string))
  (redraw! term))

(defn write-at!
  [term x y char-or-string]
  (move-to! term x y)
  (write! term char-or-string))

(defn redraw!
  [term]
  (.flush term))

(defn clear!
  [term]
  (.clearScreen term))


;;; --------------------- For Dev Testing ------------------------

;; (defonce term (new-terminal))


