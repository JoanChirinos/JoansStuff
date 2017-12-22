##       __                     ________    _      _                 
##      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
## __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
##/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  ) 
##\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/  
##
##~Joan Chirinos, November 18, 2017

##This is a prime generator. It utilizes the reasoning that every number
##is a product of primes

import time
import matplotlib.pyplot as plt
import numpy as np

def primeGenPrint(maximum = -1):
    print 2
    primes = [2]
    count = 3
    while count < maximum or maximum == -1:
        prime = True
        for i in primes:
            if count % i != 0:
                continue
            else:
                prime = False
                break
        if prime:
            print count
            primes += [count]
            count += 2
        else:
            count += 2

def primeGenReturn(maximum = 0):
    start = time.clock()
    primes = [2]
    count = 3
    while count < maximum:
        prime = True
        for i in primes:
            if i > count ** 0.5:
                break
            elif count % i != 0:
                continue
            else:
                prime = False
                break
        if prime:
            primes += [count]
            count += 2
        else:
            count += 2
    print time.clock() - start
    return primes

def primeGenGraphHelper(maximum = 0):
    start = time.clock()
    primes = [2]
    count = 3
    while count < maximum:
        prime = True
        for i in primes:
            if i > count ** 0.5:
                break
            elif count % i != 0:
                continue
            else:
                prime = False
                break
        if prime:
            primes += [count]
            count += 2
        else:
            count += 2
    return time.clock() - start

def primeGenGraph(maximum = 50000, countBy = 1000):
    x = []
    y = []
    counter = countBy
    while maximum > counter:
        x += [counter]
        y += [primeGenGraphHelper(counter)]
        counter += countBy
    plt.plot(x, y)
    plt.show()
        
primeGenPrint(300)




    








    
