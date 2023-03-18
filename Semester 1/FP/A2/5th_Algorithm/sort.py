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
"""
import random


def check_if_not_sorted(array):
    flag = 0
    i = 1
    while i < len(array):
        if array[i] < array[i - 1]:
            flag = 1
        i += 1
    return flag


def selectionSort_1(array, size, stepp):
    step = 0
    j = 0
    copyy = array.copy()
    nr_steps=0
    while step < size and j < size:
        min_idx = j

        for i in range(j + 1, size):
            # select the minimum element in each loop
            if copyy[i] < copyy[min_idx]:
                min_idx = i

        # put min at the correct position
        (copyy[j], copyy[min_idx]) = (copyy[min_idx], copyy[j])
        if check_if_not_sorted(copyy):
            nr_steps = nr_steps + 1
        if step % stepp == 0 and step < nr_steps:
            print(copyy)
        step = step + 1
        j = j + 1


def strand_sort(inp, step):
    copyy = inp
    output = strand(copyy)
    e = 1
    print(output)
    while len(copyy) and e <= step:
        print("-------------")
        output = merge(output, strand(copyy))
        e += 1
        print(output)
    # print(output)


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

    n = int(input("Enter the number of the random elements: "))
    randomlist = []

    for i in range(0, n):
        a = random.randint(0, 100)
        randomlist.append(a)
    print(randomlist)
    my_list = randomlist.copy()

    while True:
        command = int(input("Your command: "))
        if command == 0:
            break
        elif command == 1:
            # my_listt = [67, 64, 18, 79, 62, 62, 44]
            step = int(input("Enter the number of steps: "))
            selectionSort_1(my_list, len(my_list), step)
        elif command == 2:
            # my_listt = [75, 59, 39, 60, 27, 57]
            step = int(input("Enter the number of steps: "))
            strand_sort(my_list, step)


        else:
            print("Incorrect input!!!")


main()
