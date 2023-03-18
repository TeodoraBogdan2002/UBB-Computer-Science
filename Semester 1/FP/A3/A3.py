"""
Implement a menu-driven console application to help visualize the way sorting algorithms work. You will be given two of
the algorithms from the list below to implement (one from each set). When started, the program will print a menu with
the following options:

Generate a list of n random natural numbers. Generated numbers must be between 0 and 100.
Sort the list using the first algorithm.
Sort the list using the second algorithm.
Exit the program

Sorting algorithms:
Basic set: selection sort
Advanced set: strand sort

Update the program created for the previous assignment by adding the following three additional menu entries -- one for
best case, one for average case and the last one for worst case. When the user selects one of these, the program will
time and illustrate the runtime of the implemented algorithms by sorting 5 data structures, each having twice the number
of elements of the previous one. The elements of the data structure must be in accordance with the current case. See
the example below.
"""
from datetime import datetime
import timeit
import numpy as np


def selectionSort(array, size):
    """
    Time complexity:
       Best: O(n^2)
       Worst: O(n^2)
       Average: O(n^2)
       number of comparisons: (n-1)+(n-2)+..+1 = n(n-1)/2=~n^2
    """
    for step in range(size):
        min_idx = step

        for i in range(step + 1, size):

            # to sort in descending order, change > to < in this line
            # select the minimum element in each loop
            if array[i] < array[min_idx]:
                min_idx = i

        # put min at the correct position
        (array[step], array[min_idx]) = (array[min_idx], array[step])
    print("Ready")


def selectionS():
    SETUP_CODE = '''
from __main__ import selectionSort
'''

    TEST_CODE = '''
mylist = [x for x in range(8000)]
selectionSort(mylist, len(mylist))'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=1,
                          number=1)

    # printing minimum exec. time
    print('Selection sort time: {}'.format(min(times)))


def strand_sort1(inp):
    """
    Time complexity:
      Best case: O(n)
      Average case: O(n^2)
      Worst case: O(n^2)
    """
    copyy = inp
    output = strand(copyy)
    while len(copyy):
        output = merge(output, strand(copyy))


def strand(inp):
    element, sub = 0, [inp.pop(0)]
    while element < len(inp):
        if inp[element] > sub[-1]:
            sub.append(inp.pop(element))
        else:
            element += 1
    return sub


def merge(a, b):
    output = []
    e = 0
    while len(a) and len(b):
        if a[0] < b[0]:
            output.append(a.pop(0))
        else:
            output.append(b.pop(0))
    output += a
    output += b
    return output


def print_menu():
    print("1. sort the list using selection sort")
    print("2. sort the list using the strand sort")
    print("0. Exit")


def print_complexity_cases():
    print("1. Worst case")
    print("2. Average case")
    print("3. Best case")


def main():
    print_menu()

    while True:
        command = int(input("Your command: "))
        if command == 0:
            break
        elif command == 1:
            # my_listt = [75, 59, 39, 60, 27, 57]
            print_complexity_cases()

            command_complexity = int(input("Enter the case you want: "))
            if command_complexity == 1:
                # WORST CASE
                #    If we want to sort in ascending order and the array is in
                # descending order then, the worst case occurs.
                n = int(input("Enter the number of elements of the list"))
                my_list = []
                for i in range(n, 0, -1):
                    my_list.append(i)

                startTime = datetime.now()
                selectionSort(my_list, n)
                print("Time taken:", datetime.now() - startTime)

            elif command_complexity == 2:
                # AVERAGE CASE
                # It occurs when the elements of the array
                # are in jumbled order (neither ascending nor descending).
                n = int(input("Enter the number of elements of the list"))
                my_list = []
                for i in range(n):
                    my_list.append(i)
                np.random.shuffle(my_list)

                startTime = datetime.now()
                selectionSort(my_list, n)
                print("Time taken:", datetime.now() - startTime)

            elif command_complexity == 3:
                # BEST CASE
                # It occurs when the array is already sorted
                n = int(input("Enter the number of elements of the list"))
                my_list = []
                for i in range(n):
                    my_list.append(i)

                startTime = datetime.now()
                selectionSort(my_list, n)
                print("Time taken:", datetime.now() - startTime)
            else:
                print("Bad input")
            # step = int(input("Enter the number of steps: "))
            # selectionSort_1(my_listt, len(my_listt), step)
        elif command == 2:
            # my_listt = [75, 59, 39, 60, 27, 57]
            print_complexity_cases()

            command_complexity = int(input("Enter the case you want: "))
            if command_complexity == 1:
                # WORST CASE
                #    If we want to sort in ascending order and the array is in
                # descending order then, the worst case occurs.
                n = int(input("Enter the number of elements of the list"))
                my_list = []
                for i in range(n, 0, -1):
                    my_list.append(i)

                startTime = datetime.now()
                strand_sort1(my_list)
                print("Time taken:", datetime.now() - startTime)

            elif command_complexity == 2:
                # AVERAGE CASE : the list is in jumbled order (neither ascending nor descending)

                n = int(input("Enter the number of elements of the list"))
                my_list = []
                for i in range(n):
                    my_list.append(i)
                np.random.shuffle(my_list)

                startTime = datetime.now()
                strand_sort1(my_list)
                print("Time taken:", datetime.now() - startTime)

            elif command_complexity == 3:
                # BEST CASE
                # It occurs when the array is already sorted
                n = int(input("Enter the number of elements of the list"))
                my_list = []
                for i in range(n):
                    my_list.append(i)

                startTime = datetime.now()
                strand_sort1(my_list)
                print("Time taken:", datetime.now() - startTime)
            else:
                print("Bad input")

        else:
            print("Incorrect input!!!")


main()

# if __name__=="__main__":
#     selectionS()

