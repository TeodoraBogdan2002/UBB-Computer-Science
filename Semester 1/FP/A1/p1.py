# Solve the problem from the first set here
"""
1. Generate the first prime number larger than a given natural number n
"""


def number_prime(x):
    a = int(x)
    if a == 0 or a == 1:
        return 0
    else:
        for d in range(2, int(a / 2)):
            if a % d == 0:
                return 0

    return 1


def input_fct():
    n = int(input('Enter n:'))
    return n


def main_prb1():
    n = input_fct()
    ok = 0
    while ok == 0:
        n = n + 1
        if number_prime(n) == 1:
            ok = 1

    return n


print(main_prb1())
