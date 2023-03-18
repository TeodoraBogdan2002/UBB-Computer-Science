"""
5. The sequence a = a1, ..., an with distinct integer numbers is given. Determine all subsets of elements having the sum
 divisible by a given n.
"""


def sum_of_numbers(list_numbers):
    """
    :param list_numbers:
    :return: The sum of the element of the list of numbers
    """
    sum = 0
    for nr in list_numbers:
        sum = sum + nr

    return sum


def recursiveBacktr(A, subset, index, nn):
    if sum_of_numbers(subset) % nn == 0:
        print(*subset)
    for i in range(index, len(A)):
        subset.append(A[i])
        recursiveBacktr(A, subset, i + 1, nn)
        subset.pop(-1)

    return


# below function returns the subsets of vector A.
def subsets(A, nn):
    subset = []
    index = 0
    recursiveBacktr(A, subset, index, nn)

def iterativeBacktr(S:list, n):
    # `N` stores the total number of subsets
    N = int(pow(2, len(S)))

    # sort the set
    S.sort()

    # generate each subset one by one
    for i in range(N):
        s = []
        subset = []
        # check every bit of `i`
        for j in range(len(S)):
            # if j'th bit of `i` is set, append `S[j]` to the subset
            if i & (1 << j):
                subset.append(S[j])

        # insert the subset into the set
        for ind in range(len(subset)):
            s.append(subset[ind])
        if sum_of_numbers(s)%n==0 and len(s)!=0:
            print(s)


# def iterativeBacktr1(setOfNumbers:list, n):
#     totalNumberSets = 2 ** (len(setOfNumbers))
#     binarySize = len('{0:b}'.format(totalNumberSets - 1))
#     binaryMask = '{0:0' + str(binarySize) + 'b}'
#     powers = []
#     for index in range(totalNumberSets):
#         combination = binaryMask.format(index)
#         subSet = []
#         for i in range(len(combination)):
#             if combination[i] == '1':
#                 subSet += [setOfNumbers[i]]
#         powers.append(subSet)
#     if sum_of_numbers(powers)%n==0:
#         return powers


if __name__ == "__main__":
    li_numbers = [1,2,3,4]
    nn = int(input("n: "))
    # subsets(li_numbers, nn)
    iterativeBacktr(li_numbers,nn)


# Driver Code

# find the subsets of below vector.
# array = [1, 2, 3, 4, 5, 6]
# nn = 2
# res will store all subsets.
# O(2 ^ (number of elements inside array))
# because at every step we have two choices
# either include or ignore.
# subsets(array, nn)
