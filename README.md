# candle

A Clojure wrapper around the Java Lanterna library, for terminal output.

## Usage

In your project.clj dependancies:

```clojure
[candle "0.1.2-SNAPSHOT"]
```

To use the screen layer:
```clojure
(ns my-example
  (:require 
    [candle.screen :as scr]
    [candle.utils :as u]))

;; The 'new-screen' initializes a screen and all it's related parts, and returns
;; them all in a single map. This map should be passed to all other functions in
;; the candle.screen namespace, generally as the first argument.
(defonce screen (scr/new-screen)) 

;; Open a window for the screen
(scr/start! screen)

;; In order to see anything on the screen (including the cursor), we must
;;   manually refresh it.
(scr/refresh! screen)
;; You must also refresh the screen each time you want to see changes that have
;; been made. You do not have to refresh after every change, only when you want
;; the changes to be displayed.

;; The cursor is pretty useless in the screen layer, as it doesn't move with
;; the text. Let's hide it.
(scr/hide-cursor! screen)

;; And refresh to show the change
(scr/refresh! screen)


;; Let's try writing something on our screen
(scr/write! screen 10 5 "Hello World!")

(scr/refresh! screen)


;; scr/write! also takes optional foreground and background colors
(scr/write! screen
            0 0 
            "A second hello." 
            :fg (u/rgb 55 255 100) 
            :bg (u/ansi-color :yellow))

(scr/refresh! screen)
;; (Note: ANSI yellow looks more like orange than yellow)


;; To resize the screen, drag the corner of the screen window like you would
;; with any other window on your desktop. You must call update-size! for the
;; screen to recognize the new size.
(scr/update-size! screen)


;; To close the screen
(scr/stop! screen)

;; scr/stop! doesn't remove any of the content, it just closes the window. You
;; can start the window again and it will open with the same content that was
;; there when you stopped it (but to see it you still must call scr/refresh!)
(scr/start! screen)
(scr/refresh! screen)


;; To clear everything from the screen
(scr/clear! screen)

(scr/refresh! screen)

;; Remember to stop the screen when you're done
(scr/stop! screen)
```

## License

Copyright © 2017 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
