from random import random, shuffle

def learn(pathToText):
    whitelist = set('abcdefghijklmnopqrstuvwxy ')
    t = list(''.join(filter(whitelist.__contains__, ' '.join(open(pathToText, 'rU').read().lower().split())))) + [' ']
    d = dict()
    letters = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 't', 's', 'u', 'v', 'w', 'x', 'y', 'z', ' ']
    for l in letters:
        d[l] = dict()
        for ll in letters:
            d[l][ll] = 0
        d[l]['sum'] = 0
    d['sum'] = 0
    

    for i in range(0, len(t) - 1):
        if t[i] in letters and t[i + 1] in letters:
            d[t[i]][t[i + 1]] += 1
            d[t[i]]['sum'] += 1
            d['sum'] += 1
    return d

def makeWord(d):
    total = d['sum']
    word = ' '
    nextLetter = ''
    while (nextLetter != ' '):
        word += nextLetter
        howManySteps = int(random() * (d[word[-1]]['sum']))
        keys = d[word[-1]].keys()
        shuffle(keys)
        while (howManySteps > 0):
            if (keys[0] == 'sum'):
                keys = keys[1:]
            howManySteps -= d[word[-1]][keys[0]]
            nextLetter = keys[0]
            keys = keys[1:]
    return word[1:]

def makeSentence(pathToText):
    d = learn(pathToText)
    t = open(pathToText).read().split('.')[1:]
    t = [len(i) for i in t]
    avg = sum(t) / float(len(t))
    length = int(avg - 2 + random() * 4)
    sentence = []
    for i in xrange(length):
        sentence += [makeWord(d)]
    return ' '.join(sentence)
            
        
