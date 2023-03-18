'''
Maximize the profit when selling a rod of length n. The rod can be cut into pieces of integer lengths and pieces can be
sold individually. The prices are known for each possible length. For example, if rod length n = 7, and the price array
is price = [1, 5, 8, 9, 10, 17, 17] (the price of a piece of length 3 is 8), the maximum profit is 18, and is obtained
by cutting the rod into 3 pieces, two of length two and one of length 3. Display the profit and the length of rod
sections sold to obtain it.
1. naive, non-optimized version
2.dynamic programming version
'''


# 1. naive, non-optimized version
import sys


def rodCut(price, n):
    # base case
    if n == 0:
        return 0

    maxValue = -sys.maxsize
    for i in range(1, n + 1):

        # rod of length `i` has a cost `price[i-1]`
        cost = price[i - 1] + rodCut(price, n - i)

        if cost > maxValue:
            maxValue = cost

    return maxValue


# 2. Dynamic programming version:

INT_MIN = -32767

def cutRod(price, n):
    val = [0 for x in range(n + 1)]
    val[0] = 0

    # Build the table val[] in bottom up manner and return
    # the last entry from the table
    for i in range(1, n + 1):
        max_val = INT_MIN
        for j in range(i):
            max_val = max(max_val, price[j] + val[i - j - 1])
        val[i] = max_val

    return val[n]


if __name__ == "__main__":
    arr = [1, 5, 8, 9, 10, 17, 17,20]
    n = 8
    print("Maximum Obtainable Value is ", str(cutRod(arr, n)))


    # rod length

    print('Profit is', rodCut(arr, n))
