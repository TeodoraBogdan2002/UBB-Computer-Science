# Solve the problem from the second set here
# 10. The palindrome of a number is the number obtained by reversing the
# order of its digits. For a given natural number n, determine its palindrome.
def palindrome_nr(x):
    p = 0
    while x > 0:
        dig = x % 10
        p = p * 10 + dig
        x = x // 10
    return p


def input_fct():
    n = int(input('Enter n:'))
    return n


def main_prb10():
    n = input_fct()
    return palindrome_nr(n)


print(main_prb10())
