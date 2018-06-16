globals [
  basecolor
  goalcolor
  startcolor
  wallcolor
  done
  pf2found
  donedone
  superdone
  donewith3
]

patches-own [
  correct
  dist

  pf2done

  parent
  recursedone

  next
  path
]

to setup
  ca
  set superdone false
  set basecolor white
  set goalcolor red
  set startcolor green
  set wallcolor black
  set pf2found false
  set donewith3 false
  ask patches [
    set pcolor basecolor
    set path false
    set correct false
    set plabel-color black
    set dist 1000000
    set pf2done false
    set parent "none"
    set next false
  ]
  ask patches with [abs(pxcor) > 14 or abs(pycor) > 14] [
    set pcolor wallcolor
  ]
  set done false
  set donedone false
end

to chooseGoal
  if mouse-down? [
    ask patches with [pcolor = goalcolor] [
      set pcolor basecolor
    ]
    ask patch mouse-xcor mouse-ycor [
      if pcolor != wallcolor and pcolor != startcolor [
        set pcolor goalcolor
      ]
    ]
  ]
end

to drawWalls
  if mouse-down? [
    ask patch mouse-xcor mouse-ycor [
      if pcolor != goalcolor and pcolor != startcolor [
        set pcolor wallcolor
      ]
    ]
  ]
end

to eraseWalls
  if mouse-down? [
    ask patch mouse-xcor mouse-ycor [
      if pcolor != goalcolor and pcolor != startcolor [
        set pcolor basecolor
      ]
    ]
  ]
end

to chooseStart
  if mouse-down? [
    ask patches with [pcolor = startcolor] [
      set pcolor basecolor
    ]
    ask patch mouse-xcor mouse-ycor [
      if pcolor != goalcolor and pcolor != wallcolor [
        set pcolor startcolor
      ]
    ]
  ]
end

to pathFind1
  if count patches with [pcolor = startcolor or pcolor = goalcolor] != 2 [
    print "You need to set ONE start point and ONE end point first"
  ]
  ask patches with [pcolor = startcolor] [
    recurse(pxcor)(pycor)(0)
  ]
  ask patches with [pcolor = goalcolor] [
    backtrack
  ]
end

to recurse[x y distFrom]
  if done = true [
    stop
  ]
  ask patch x y [
    set dist distFrom
    set plabel dist
    ifelse pcolor = goalcolor [
      set correct true
      set done true
      stop
    ]
    [
      set pcolor yellow
      ifelse abs(pxcor) = 16 or abs(pycor) = 16 [
        set correct false
      ]
      [
        if [pcolor] of patch (x + 1) y != yellow and [pcolor] of patch (x + 1) y != wallcolor [
          recurse(x + 1)(y)(distFrom + 1)
        ]
        if [pcolor] of patch x (y + 1) != yellow and [pcolor] of patch x (y + 1) != wallcolor [
          recurse(x)(y + 1)(distFrom + 1)
        ]
        if [pcolor] of patch (x - 1) y != yellow and [pcolor] of patch (x - 1) y != wallcolor [
          recurse(x - 1)(y)(distFrom + 1)
        ]
        if [pcolor] of patch x (y - 1) != yellow and [pcolor] of patch x (y - 1) != wallcolor [
          recurse(x)(y - 1)(distFrom + 1)
        ]
      ]
    ]
  ]
end

to backtrack
  if dist = 0 [
    print "found!"
    set pcolor green
    stop
  ]
  let lowest 1000000
  ask neighbors4 with [pcolor = yellow or pcolor = green] [
    if dist < lowest [
      set lowest dist
    ]
  ]
  ask neighbors4 with [dist = lowest] [
    set pcolor blue
    backtrack
  ]
end

to clearPath
  ask patches with [pcolor = yellow or pcolor = blue] [
    set pcolor basecolor
  ]
  ask patches [
    set dist 1000000
    set plabel ""
    set pf2done false
    set done false
    set parent "none"
    set next false
  ]
  set pf2found false
  set donedone false
  set superdone false
  set donewith3 false
  ask patches [
    set recursedone false
  ]
end

to pathFind2
  if count patches with [pcolor = startcolor or pcolor = goalcolor] != 2 [
    print "You need to set ONE start point and ONE end point first"
  ]
  ask patches with [pcolor = green] [
    set dist 0
    set plabel dist
    set pf2done true
    set parent "none"
    ask neighbors4 with [pcolor != wallcolor] [
      set pcolor yellow
      set dist 1
      set plabel dist
    ]
  ]
  let distThing 2
  while [pf2found = false] [
    pf2action(distThing)
    set distThing distThing + 1
  ]
  ask patches with [pcolor = goalcolor] [
    pf2backtrack
  ]
end


to pf2Action[distFrom]
  ask patches with [pcolor = yellow and pf2done = false] [
    let myx pxcor
    let myy pycor
    ask neighbors4 with [pcolor != yellow and pcolor != wallcolor and dist != 0] [
      set parent patch myx myy
      set dist distFrom
      set plabel dist
      if pcolor = goalcolor [
        set pf2found true
        stop
      ]
      set pcolor yellow
    ]
    set pf2done true
  ]
end


to pf2backtrack
  if donedone != true [
    let myDist dist
    ask neighbors4 with [dist = myDist - 1] [
      if dist = 0 [
        set donedone true
        stop
      ]
      set pcolor blue
      pf2backtrack
    ]
  ]
end

to pathFind3
  if count patches with [pcolor = startcolor or pcolor = goalcolor] != 2 [
    print "You need to set ONE start point and ONE end point first"
  ]
  ask patches with [pcolor = startcolor] [
    set next true
  ]
  while [donewith3 = false] [
    ask patches with [next = true] [
      makechildren(pxcor)(pycor)
    ]
  ]
  ask patches with [next = true] [
    set next false
  ]
  ask patches with [pcolor = goalcolor] [
    set next true
  ]
  set donewith3 false
  while [donewith3 = false] [
    ask patches with [next = true] [
      colorpath
    ]
  ]
end

to makechildren [x y]
  ask neighbors4 [
    if pcolor = goalcolor [
      set parent patch x y
      set donewith3 true
      stop
    ]
    if parent = "none" and pcolor = basecolor [
      set pcolor yellow
      set parent patch x y
      set next true
    ]
  ]
  set next false
end

to colorpath
  ask parent [
    ifelse pcolor = startcolor [
      set donewith3 true
      stop
    ]
    [
      set pcolor blue
      set next true
    ]
  ]
  set next false
end

to setupmaze
  ca
  import-pcolors "maze.jpg"
  set superdone false
  set basecolor white
  set goalcolor red
  set startcolor green
  set wallcolor black
  set pf2found false
  set donewith3 false
  ask patches [
    set correct false
    set plabel-color black
    set dist 1000000
    set pf2done false
    set parent "none"
    set next false
  ]
  set done false
  set donedone false
end

; make patches own parent and recurse through parents to set color blue


to fakesnake
  ask one-of patches with [pcolor = basecolor] [
    set pcolor startcolor
  ]

  repeat 50 [

    ask one-of patches with [pcolor = basecolor] [
      set pcolor goalcolor
    ]

    ask patches with [pcolor = startcolor] [
      set next true
    ]
    while [donewith3 = false] [
      ask patches with [next = true] [
        makechildren(pxcor)(pycor)
      ]
    ]
    ask patches with [next = true] [
      set next false
    ]
    ask patches with [pcolor = goalcolor] [
      set next true
    ]
    set donewith3 false
    while [donewith3 = false] [
      ask patches with [next = true] [
        colorpath
      ]
    ]
    ask patches with [pcolor = yellow] [
      set pcolor basecolor
    ]
    ask patches with [pcolor = blue] [
      set path true
    ]
    clearpath
    while [count patches with [pcolor = goalcolor] != 0] [
      snakeback
      wait 0.08
    ]
    clearsnakepath
  ]
end

;to pathFind3
;  if count patches with [pcolor = startcolor or pcolor = goalcolor] != 2 [
;    print "You need to set ONE start point and ONE end point first"
;  ]
;  ask patches with [pcolor = startcolor] [
;    set next true
;  ]
;  while [donewith3 = false] [
;    ask patches with [next = true] [
;      makechildren(pxcor)(pycor)
;    ]
;  ]
;  ask patches with [next = true] [
;    set next false
;  ]
;  ask patches with [pcolor = goalcolor] [
;    set next true
;  ]
;  set donewith3 false
;  while [donewith3 = false] [
;    ask patches with [next = true] [
;      colorpath
;    ]
;  ]
;end

to snakeback
  ask patches with [pcolor = startcolor] [
    ask neighbors4 with [path = true or pcolor = goalcolor] [
      set pcolor startcolor
    ]
    set pcolor basecolor
    set path false
  ]
end

to clearsnakepath
  ask patches with [path = true] [
    set path false
  ]
end

@#$#@#$#@
GRAPHICS-WINDOW
474
10
1083
640
16
16
18.152
1
6
1
1
1
0
0
0
1
-16
16
-16
16
0
0
1
ticks
30.0

BUTTON
40
112
106
145
NIL
setup
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

BUTTON
20
190
124
223
NIL
chooseGoal
T
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

BUTTON
24
264
117
297
NIL
drawWalls
T
1
T
OBSERVER
NIL
D
NIL
NIL
1

BUTTON
22
303
118
336
NIL
eraseWalls
T
1
T
OBSERVER
NIL
E
NIL
NIL
1

BUTTON
19
226
124
259
NIL
chooseStart
T
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

BUTTON
192
158
286
191
NIL
pathFind1
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

BUTTON
25
342
113
375
NIL
clearPath
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

BUTTON
192
265
286
298
NIL
pathFind2
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

BUTTON
192
353
284
386
NIL
pathfind3
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

BUTTON
25
153
123
186
NIL
setupmaze
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

TEXTBOX
304
149
454
219
Does not work with maze. Also can usually find a solution pretty quuickly, but it is usually not the best solution
11
0.0
1

BUTTON
22
380
115
413
NIL
fakesnake
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

TEXTBOX
302
241
452
325
Uses a kind of distance-based rating system, so not very good and causes error that humans can ignore but that get machines angery
11
0.0
1

TEXTBOX
299
347
449
417
Finds (one of the) best solution(s) (I think), but can attempt a lot more spaces than pathFind1 thus making it slower sometimes
11
0.0
1

TEXTBOX
24
470
454
596
Some notes:\n1. For the maze, you need the maze image file and set the world size to 51x51\n2. See notes above buttons for speed-setting info\n3. Fake snake is fake snake\n4. See info for how the thingamabobs work, or try to figure out how the convoluted, uncommented, messy code works in the code tab\n\n5!! pf3 is the one you should be focusing on. The other 2 were kind of a build up to pf3. I also believe pf3 code is a little cleaner, so have fun with that
11
0.0
1

TEXTBOX
31
90
181
108
Use regular speed
11
0.0
1

TEXTBOX
208
93
438
135
Use a slower speed (1/3 of the way to regular works for me
11
0.0
1

TEXTBOX
1110
153
1260
171
no a* Dx
11
0.0
1

TEXTBOX
16
10
460
58
FOR MORE INFO GO TO INFO TAB (I shouldn't have to remind you xD)\nalso try fullscreen
13
0.0
1

@#$#@#$#@
# PathFinder (and friends)

## WHAT IS IT?

A program to display 3 pathfinding methods in 2d obstructable space

## HOW IT WORKS

Pathfind1:
6 steps each patch does:
	1. If I am the goal, let's backtrack!
	2. Set my dist to distFrom (basically how many steps away from the start patch
	3. Else if patch to my right is basecolor, ask them to turn yellow and do the 5 steps
	4. Else if patch to my top is basecolor, ask them to turn yellow and do the 5 steps
	5. Else if patch to my left is basecolor, ask them to turn yellow and do the 5 steps
	6. Else if patch to my bottom is basecolor, ask them to turn yellow and do the 5 steps
Once the goal patch is found, backtrack by recursion:
	1. If you are the startcolor, we're all done!
	2. Else ask the lowest of your 4 neighbors to turn blue and do these steps

Pathfind2:
Uses breadth-first search typpa method to solve.
	1. If you are goalcolor-ed, you're done
	2. Else, ask 4 neighbors to turn yellow and set dist to distFrom
Once the goal has been found, backtrack by recursion:
	Ask the goal:
	1. Ask your 4 neighbors
		1. If your dist = 0, we're done!
		2. Otherwise, if your dist = my dist - 1, set pcolor blue and follow steps.

Pathfind3!!!
	1. If you are goalcolor-ed, recurse back
	2. Else, tell your 4 neighbors:
		1. Set pcolor yellow
		2. You are my children. Follow steps 1-2
recurse back:
	start at patch with [pcolor = goalcolor]
	1. ask parent
		1. If you're startcolor, we're done
		2. Else do step 1


## HOW TO USE IT

Pretty much explains itself on the interface

## THINGS TO NOTICE

How do pf1 and pf2 differ? pf1 evaluates 1 neighbor at a time, causing the path to propograte right, then up, then left, then down. pf2 evaluates all 4 neighbors, then does the next step. This causes it to spread in a diamond shape

Why are there blobs with pf2? Well, that's a good question. I'm like 90% sure it has to do with a patch sometimes having 2 neighbors with dists one less than their own. Thus, they turn blue

## THINGS TO TRY

1. Change colors (should work, haven't tried yet)

## EXTENDING THE MODEL

1. Try an A* thing. Gl
2. Make the fakesnake a realsnake
3. Make the colors go away on fakesnake pls

## NETLOGO FEATURES

Did you know you can store a patch in a variable???

## RELATED MODELS

idk

## CREDITS AND REFERENCES

Joan Chirinos, 2018. Some contribution and guidance by Peter Brooks
@#$#@#$#@
default
true
0
Polygon -7500403 true true 150 5 40 250 150 205 260 250

airplane
true
0
Polygon -7500403 true true 150 0 135 15 120 60 120 105 15 165 15 195 120 180 135 240 105 270 120 285 150 270 180 285 210 270 165 240 180 180 285 195 285 165 180 105 180 60 165 15

arrow
true
0
Polygon -7500403 true true 150 0 0 150 105 150 105 293 195 293 195 150 300 150

box
false
0
Polygon -7500403 true true 150 285 285 225 285 75 150 135
Polygon -7500403 true true 150 135 15 75 150 15 285 75
Polygon -7500403 true true 15 75 15 225 150 285 150 135
Line -16777216 false 150 285 150 135
Line -16777216 false 150 135 15 75
Line -16777216 false 150 135 285 75

bug
true
0
Circle -7500403 true true 96 182 108
Circle -7500403 true true 110 127 80
Circle -7500403 true true 110 75 80
Line -7500403 true 150 100 80 30
Line -7500403 true 150 100 220 30

butterfly
true
0
Polygon -7500403 true true 150 165 209 199 225 225 225 255 195 270 165 255 150 240
Polygon -7500403 true true 150 165 89 198 75 225 75 255 105 270 135 255 150 240
Polygon -7500403 true true 139 148 100 105 55 90 25 90 10 105 10 135 25 180 40 195 85 194 139 163
Polygon -7500403 true true 162 150 200 105 245 90 275 90 290 105 290 135 275 180 260 195 215 195 162 165
Polygon -16777216 true false 150 255 135 225 120 150 135 120 150 105 165 120 180 150 165 225
Circle -16777216 true false 135 90 30
Line -16777216 false 150 105 195 60
Line -16777216 false 150 105 105 60

car
false
0
Polygon -7500403 true true 300 180 279 164 261 144 240 135 226 132 213 106 203 84 185 63 159 50 135 50 75 60 0 150 0 165 0 225 300 225 300 180
Circle -16777216 true false 180 180 90
Circle -16777216 true false 30 180 90
Polygon -16777216 true false 162 80 132 78 134 135 209 135 194 105 189 96 180 89
Circle -7500403 true true 47 195 58
Circle -7500403 true true 195 195 58

circle
false
0
Circle -7500403 true true 0 0 300

circle 2
false
0
Circle -7500403 true true 0 0 300
Circle -16777216 true false 30 30 240

cow
false
0
Polygon -7500403 true true 200 193 197 249 179 249 177 196 166 187 140 189 93 191 78 179 72 211 49 209 48 181 37 149 25 120 25 89 45 72 103 84 179 75 198 76 252 64 272 81 293 103 285 121 255 121 242 118 224 167
Polygon -7500403 true true 73 210 86 251 62 249 48 208
Polygon -7500403 true true 25 114 16 195 9 204 23 213 25 200 39 123

cylinder
false
0
Circle -7500403 true true 0 0 300

dot
false
0
Circle -7500403 true true 90 90 120

face happy
false
0
Circle -7500403 true true 8 8 285
Circle -16777216 true false 60 75 60
Circle -16777216 true false 180 75 60
Polygon -16777216 true false 150 255 90 239 62 213 47 191 67 179 90 203 109 218 150 225 192 218 210 203 227 181 251 194 236 217 212 240

face neutral
false
0
Circle -7500403 true true 8 7 285
Circle -16777216 true false 60 75 60
Circle -16777216 true false 180 75 60
Rectangle -16777216 true false 60 195 240 225

face sad
false
0
Circle -7500403 true true 8 8 285
Circle -16777216 true false 60 75 60
Circle -16777216 true false 180 75 60
Polygon -16777216 true false 150 168 90 184 62 210 47 232 67 244 90 220 109 205 150 198 192 205 210 220 227 242 251 229 236 206 212 183

fish
false
0
Polygon -1 true false 44 131 21 87 15 86 0 120 15 150 0 180 13 214 20 212 45 166
Polygon -1 true false 135 195 119 235 95 218 76 210 46 204 60 165
Polygon -1 true false 75 45 83 77 71 103 86 114 166 78 135 60
Polygon -7500403 true true 30 136 151 77 226 81 280 119 292 146 292 160 287 170 270 195 195 210 151 212 30 166
Circle -16777216 true false 215 106 30

flag
false
0
Rectangle -7500403 true true 60 15 75 300
Polygon -7500403 true true 90 150 270 90 90 30
Line -7500403 true 75 135 90 135
Line -7500403 true 75 45 90 45

flower
false
0
Polygon -10899396 true false 135 120 165 165 180 210 180 240 150 300 165 300 195 240 195 195 165 135
Circle -7500403 true true 85 132 38
Circle -7500403 true true 130 147 38
Circle -7500403 true true 192 85 38
Circle -7500403 true true 85 40 38
Circle -7500403 true true 177 40 38
Circle -7500403 true true 177 132 38
Circle -7500403 true true 70 85 38
Circle -7500403 true true 130 25 38
Circle -7500403 true true 96 51 108
Circle -16777216 true false 113 68 74
Polygon -10899396 true false 189 233 219 188 249 173 279 188 234 218
Polygon -10899396 true false 180 255 150 210 105 210 75 240 135 240

house
false
0
Rectangle -7500403 true true 45 120 255 285
Rectangle -16777216 true false 120 210 180 285
Polygon -7500403 true true 15 120 150 15 285 120
Line -16777216 false 30 120 270 120

leaf
false
0
Polygon -7500403 true true 150 210 135 195 120 210 60 210 30 195 60 180 60 165 15 135 30 120 15 105 40 104 45 90 60 90 90 105 105 120 120 120 105 60 120 60 135 30 150 15 165 30 180 60 195 60 180 120 195 120 210 105 240 90 255 90 263 104 285 105 270 120 285 135 240 165 240 180 270 195 240 210 180 210 165 195
Polygon -7500403 true true 135 195 135 240 120 255 105 255 105 285 135 285 165 240 165 195

line
true
0
Line -7500403 true 150 0 150 300

line half
true
0
Line -7500403 true 150 0 150 150

pentagon
false
0
Polygon -7500403 true true 150 15 15 120 60 285 240 285 285 120

person
false
0
Circle -7500403 true true 110 5 80
Polygon -7500403 true true 105 90 120 195 90 285 105 300 135 300 150 225 165 300 195 300 210 285 180 195 195 90
Rectangle -7500403 true true 127 79 172 94
Polygon -7500403 true true 195 90 240 150 225 180 165 105
Polygon -7500403 true true 105 90 60 150 75 180 135 105

plant
false
0
Rectangle -7500403 true true 135 90 165 300
Polygon -7500403 true true 135 255 90 210 45 195 75 255 135 285
Polygon -7500403 true true 165 255 210 210 255 195 225 255 165 285
Polygon -7500403 true true 135 180 90 135 45 120 75 180 135 210
Polygon -7500403 true true 165 180 165 210 225 180 255 120 210 135
Polygon -7500403 true true 135 105 90 60 45 45 75 105 135 135
Polygon -7500403 true true 165 105 165 135 225 105 255 45 210 60
Polygon -7500403 true true 135 90 120 45 150 15 180 45 165 90

sheep
false
15
Circle -1 true true 203 65 88
Circle -1 true true 70 65 162
Circle -1 true true 150 105 120
Polygon -7500403 true false 218 120 240 165 255 165 278 120
Circle -7500403 true false 214 72 67
Rectangle -1 true true 164 223 179 298
Polygon -1 true true 45 285 30 285 30 240 15 195 45 210
Circle -1 true true 3 83 150
Rectangle -1 true true 65 221 80 296
Polygon -1 true true 195 285 210 285 210 240 240 210 195 210
Polygon -7500403 true false 276 85 285 105 302 99 294 83
Polygon -7500403 true false 219 85 210 105 193 99 201 83

square
false
0
Rectangle -7500403 true true 30 30 270 270

square 2
false
0
Rectangle -7500403 true true 30 30 270 270
Rectangle -16777216 true false 60 60 240 240

star
false
0
Polygon -7500403 true true 151 1 185 108 298 108 207 175 242 282 151 216 59 282 94 175 3 108 116 108

target
false
0
Circle -7500403 true true 0 0 300
Circle -16777216 true false 30 30 240
Circle -7500403 true true 60 60 180
Circle -16777216 true false 90 90 120
Circle -7500403 true true 120 120 60

tree
false
0
Circle -7500403 true true 118 3 94
Rectangle -6459832 true false 120 195 180 300
Circle -7500403 true true 65 21 108
Circle -7500403 true true 116 41 127
Circle -7500403 true true 45 90 120
Circle -7500403 true true 104 74 152

triangle
false
0
Polygon -7500403 true true 150 30 15 255 285 255

triangle 2
false
0
Polygon -7500403 true true 150 30 15 255 285 255
Polygon -16777216 true false 151 99 225 223 75 224

truck
false
0
Rectangle -7500403 true true 4 45 195 187
Polygon -7500403 true true 296 193 296 150 259 134 244 104 208 104 207 194
Rectangle -1 true false 195 60 195 105
Polygon -16777216 true false 238 112 252 141 219 141 218 112
Circle -16777216 true false 234 174 42
Rectangle -7500403 true true 181 185 214 194
Circle -16777216 true false 144 174 42
Circle -16777216 true false 24 174 42
Circle -7500403 false true 24 174 42
Circle -7500403 false true 144 174 42
Circle -7500403 false true 234 174 42

turtle
true
0
Polygon -10899396 true false 215 204 240 233 246 254 228 266 215 252 193 210
Polygon -10899396 true false 195 90 225 75 245 75 260 89 269 108 261 124 240 105 225 105 210 105
Polygon -10899396 true false 105 90 75 75 55 75 40 89 31 108 39 124 60 105 75 105 90 105
Polygon -10899396 true false 132 85 134 64 107 51 108 17 150 2 192 18 192 52 169 65 172 87
Polygon -10899396 true false 85 204 60 233 54 254 72 266 85 252 107 210
Polygon -7500403 true true 119 75 179 75 209 101 224 135 220 225 175 261 128 261 81 224 74 135 88 99

wheel
false
0
Circle -7500403 true true 3 3 294
Circle -16777216 true false 30 30 240
Line -7500403 true 150 285 150 15
Line -7500403 true 15 150 285 150
Circle -7500403 true true 120 120 60
Line -7500403 true 216 40 79 269
Line -7500403 true 40 84 269 221
Line -7500403 true 40 216 269 79
Line -7500403 true 84 40 221 269

wolf
false
0
Polygon -16777216 true false 253 133 245 131 245 133
Polygon -7500403 true true 2 194 13 197 30 191 38 193 38 205 20 226 20 257 27 265 38 266 40 260 31 253 31 230 60 206 68 198 75 209 66 228 65 243 82 261 84 268 100 267 103 261 77 239 79 231 100 207 98 196 119 201 143 202 160 195 166 210 172 213 173 238 167 251 160 248 154 265 169 264 178 247 186 240 198 260 200 271 217 271 219 262 207 258 195 230 192 198 210 184 227 164 242 144 259 145 284 151 277 141 293 140 299 134 297 127 273 119 270 105
Polygon -7500403 true true -1 195 14 180 36 166 40 153 53 140 82 131 134 133 159 126 188 115 227 108 236 102 238 98 268 86 269 92 281 87 269 103 269 113

x
false
0
Polygon -7500403 true true 270 75 225 30 30 225 75 270
Polygon -7500403 true true 30 75 75 30 270 225 225 270

@#$#@#$#@
NetLogo 5.3.1
@#$#@#$#@
@#$#@#$#@
@#$#@#$#@
@#$#@#$#@
@#$#@#$#@
default
0.0
-0.2 0 0.0 1.0
0.0 1 1.0 0.0
0.2 0 0.0 1.0
link direction
true
0
Line -7500403 true 150 150 90 180
Line -7500403 true 150 150 210 180

@#$#@#$#@
0
@#$#@#$#@
