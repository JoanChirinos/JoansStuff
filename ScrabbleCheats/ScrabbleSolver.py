# TO RUN
# $ python ScrabbleSolver.py <dictionary csv> <rack>
# $ python ScrabbleSolver.py <dictionary csv> <word starts with> <rack>
# $ python ScrabbleSolver.py <dictionary csv> <rack> <word ends with>

# Note that a blank tile can be represented by a "!"


import sys
from time import sleep

def rackdict(rack):
    rackd = {}
    for i in rack:
        if i in rackd: rackd[i] += 1
        else: rackd[i] = 1
    return rackd

def readlines(rack, filename='sowpods.txt'):
    rackd = rackdict(rack)
    validwords = []
    with open(filename, 'rU', 100) as infile:
        for word in infile:
            # if len(word) > 7: continue
            for i in rackd:
                if i not in word: continue
            for letter in rackd:
                if word.count(letter) > rackd[letter]:
                    continue
            validwords.append(word)
    return validwords

def spellable(word, rack):
    word_count = rackdict(word)
    rack_count  = rackdict(rack)
    return all([word_count[letter] <= rack_count[letter] for letter in word])

def filereader(filename):
    return (word.strip() for word in open(filename))

score = {"a": 1, "c": 3, "b": 3, "e": 1, "d": 2, "g": 2, "f": 4, "i": 1, "h": 4, "k": 5, "j": 8, "m": 3, "l": 1, "o": 1, "n": 1, "q": 10, "p": 3, "s": 1, "r": 1, "u": 1, "t": 1, "w": 4, "v": 4, "y": 4, "x": 8, "z": 10}

def toscore(word):
    return sum([score[i] for i in word])

def cheat(filename):
    print len(sys.argv)
    begins = ends = False
    if len(sys.argv) == 4:
        if len(sys.argv[2]) == 1:
            beg = sys.argv[2]
            begins = True
            rack = sys.argv[3]+beg
        else:
            end = sys.argv[3]
            ends = True
            rack = sys.argv[2]+end
    else:
        rack = sys.argv[2]
        print 'rack: ' + str(rack)
    for i in range(len(rack)):
        if rack[i] == '!':
            print 'has blank'
            break
    rack = rack.lower()
    words = filereader(filename)
    scored = ((toscore(word), word) for word in words if set(word).issubset(set(rack)) and len(word)>1 and spellable(word, rack))
    for score, word in sorted(scored)[::]:
        if begins and not ends:
            if word[0] == beg:
                print str(score), '\t', word
        elif ends and not begins:
            if word[-1] == end:
                print str(score), '\t', word
        elif not ends and not begins:
            print str(score), '\t', word
            
print cheat(sys.argv[1])
