"""
Use functions to: read a complex number from the console, write a complex number to the console, implement each required
functionality.
Implement a menu-driven console application that provides the following functionalities:

1.Read a list of complex numbers (in z = a + bi form) from the console.
2.Display the entire list of numbers on the console.
3.Display on the console the sequence, subarray or numbers required by the properties that were assigned to you. Each
  student will receive one property from Set A and another one from Set B.
5.Exit the application.
"""
import math
from logging import exception

""""-----------------------------------  LIST VERSION   ----------------------------------------"""


def create_complex_number_l(a=0, b=0):
    """
    Creates the complex number in a list
    :param a: the real part
    :param b: the imaginary part
    :return: a list made the two coordinates
    """
    return [a, b]


def get_realpart_l(complexnr):
    """
        Returns the real part of the complex number
        :param complexnr: number on the list
        :return: the real port of the complex number
        """
    return complexnr[0]


def get_imgpart_l(complexnr):
    """
    Returns the imaginary part of the complex number
    :param complexnr: number on the list
    :return: the imaginary port of the complex number
    """
    return complexnr[1]


def generate_numbers_list():
    """
    Display the list of numbers
    :return: the list of complex numbers
    """
    return [create_complex_number_l(13, 1),
            create_complex_number_l(3, 11),
            create_complex_number_l(33, 1),
            create_complex_number_l(0, 4),
            create_complex_number_l(1, -2),
            create_complex_number_l(2, 2),
            create_complex_number_l(3, 9),
            create_complex_number_l(4, 1),
            create_complex_number_l(5, 6),
            create_complex_number_l(5, 4),
            create_complex_number_l(9, 9),
            create_complex_number_l(1, -4)]


def add_number_l(number_list, real, imag):
    number = create_complex_number_l(real, imag)
    number_list.append(number)


"""--------"""


def add_number_ui_l(number_list):
    realpart = int(input('Real part of the number: '))
    imgpart = int(input('Imaginary part of the number: '))

    add_number_l(number_list, realpart, imgpart)


def show_all_numbers(nrlist):
    for nr in nrlist:
        # print('z= ' + str(complex(get_realpart(nr), get_imgpart(nr))))
        if get_realpart_l(nr) == 0:
            print('z= ' + str(get_imgpart_l(nr)) + 'i')
        elif get_imgpart_l(nr) < -1 or -1 < get_imgpart_l(nr) < 0:
            print('z= ' + str(get_realpart_l(nr)) + str(get_imgpart_l(nr)) + 'i')
        elif get_imgpart_l(nr) == -1:
            print('z= ' + str(get_realpart_l(nr)) + '-i')
        elif get_imgpart_l(nr) == 0:
            print('z= ' + str(get_realpart_l(nr)))
        elif get_imgpart_l(nr) == 1:
            print('z= ' + str(get_realpart_l(nr)) + '+i')
        else:
            print('z= ' + str(get_realpart_l(nr)) + '+' + str(get_imgpart_l(nr)) + 'i')


""""-----------------------------------  DICTIONARY VERSION   ----------------------------------------"""


def new_complex_nr(real, imaginary):
    return {"real": real, "imaginary": imaginary}


def get_real(complex_nr):
    return complex_nr["real"]


def get_imaginary(complex_nr):
    return complex_nr["imaginary"]


def add_number(complex_nr: list, new_c):
    if new_c in complex_nr:
        return 1
    complex_nr.append(new_c)
    return 0


def get_sequence(listComplex, starting_position, final_position):
    list = []
    for x in range(starting_position, final_position + 1):
        list.append(listComplex[x])
    return list


def no_modulus(number):
    r = get_real(number)
    im = get_imaginary(number)
    return math.sqrt(r * r + im * im)


def equal(complex_number, other_complex_number):
    """
    This function computes whether two complex numbers are equal
    :param complex_number: a complex number
    :param other_complex_number: another complex number
    :return: True if the given complex numbers are equal
             False otherwise
    """
    if get_real(complex_number) == get_real(other_complex_number) and get_imaginary(complex_number) == get_imaginary(
            other_complex_number):
        return True
    else:
        return False


def has_distinct_numbers(sequence):
    """
    This function computes whether a list has only distinct elements or not
    :param sequence: a given list
    :return: True if all the elements are distinct
             False otherwise
    """
    for i in range(len(sequence) - 1):
        for j in range(i + 1, len(sequence)):
            if equal(sequence[i], sequence[j]):
                return False
    return True


def get_longest_distinct_sequence(complex_numbers):
    """
    This function computes the longest sequence of complex numbers formed only by distinct numbers
    :param complex_numbers: list - the list of complex numbers
    :return: sequence: list - desired longest sequence of distinct complex numbers
    """
    best_sequence = []  # best overall sequence
    for i in range(len(complex_numbers)):
        for j in range(i, len(complex_numbers)):
            sequence = complex_numbers[i:j + 1]
            if has_distinct_numbers(sequence):
                if len(sequence) > len(best_sequence):
                    best_sequence = sequence[:]
    return best_sequence


def lis(arr):
    n = len(arr)

    # Declare the list (array) for LIS and initialize LIS
    # values for all indexes
    lis = [1] * n

    prev = [0] * n
    for i in range(0, n):
        prev[i] = i
    for i in range(1, n):
        for j in range(0, i):
            if no_modulus(arr[i]) > no_modulus(arr[j]) and lis[i] < lis[j] + 1:
                lis[i] = lis[j] + 1
                prev[i] = j

    maximum = 0
    idx = 0

    # Pick maximum of all LIS values
    for i in range(n):
        if maximum < lis[i]:
            maximum = lis[i]
            idx = i
    # print(idx)

    seq = [arr[idx]]
    # print(seq)
    while idx != prev[idx]:
        idx = prev[idx]
        seq.append(arr[idx])
        # print(idx)
    # for ii in prev:
    #     print(ii)

    return (maximum, reversed(seq))


# def display_incr_s(complex_numbers):
#     sequence = lis(complex_numbers)
#     print_list(sequence)


def generate_numbers():
    """
    Display the list of numbers
    :return: the list of complex numbers
    """
    return [new_complex_nr(33, 1),
            new_complex_nr(3, 11),
            new_complex_nr(33, 1),
            new_complex_nr(33, 1),
            new_complex_nr(1, 1),
            new_complex_nr(2, 2),
            new_complex_nr(3, 9),
            new_complex_nr(50, 50)]
    # return [new_complex_nr(33, 1),
    #         new_complex_nr(1, 1),
    #         new_complex_nr(3, 11),
    #         new_complex_nr(3, 9),
    #         new_complex_nr(15, 15),
    #         new_complex_nr(50, 50)]
"""-------------------------------------------------------------------------"""

def to_str(complex_nr):
    if get_real(complex_nr) == 0:
        return str(get_imaginary(complex_nr)) + 'i'
    elif get_imaginary(complex_nr) < -1 or -1 < get_imaginary(complex_nr) < 0:
        return str(get_real(complex_nr)) + str(get_imaginary(complex_nr)) + 'i'
    elif get_imaginary(complex_nr) == -1:
        return str(get_real(complex_nr)) + '-i'
    elif get_imaginary(complex_nr) == 0:
        return str(get_real(complex_nr))
    elif get_imaginary(complex_nr) == 1:
        return str(get_real(complex_nr)) + '+i'
    else:
        return str(get_real(complex_nr)) + '+' + str(get_imaginary(complex_nr)) + 'i'
    # return str(complex_nr["real"]) + "+i*(" + str(complex_nr["imaginary"]) + ")"


def add_number_ui(number_list):
    realpart = int(input('Real part of the number: '))
    imgpart = int(input('Imaginary part of the number: '))
    new_c = new_complex_nr(realpart, imgpart)

    add_number(number_list, new_c)


def print_menu():
    print("1. Add number in the list")
    print("2. Show all numbers")
    print("3. The longest sequence that observes a given property")
    print("4. Exit")


def print_menu2():
    print(" 1. Length and elements of a longest subarray of distinct numbers.")
    print(" 9. The length and elements of a longest increasing subsequence, when considering each number's modulus")


def print_list(listComplex):
    for x in range(0, len(listComplex)):
        print('z' + str(x) + ' = ' + to_str(listComplex[x]))


def display_longest_distinct_sequence(complex_numbers):
    """
    This function displays the longest sequence of complex numbers formed only by distinct numbers
    :param complex_numbers: list - the list of complex numbers
    :return: nothing
    """
    sequence = get_longest_distinct_sequence(complex_numbers)
    print("Length of sequence: " + str(len(sequence)))
    print_list(sequence)


def longest_sequence_of_increasing_modulus(complex_list):
    ans = lis(complex_list)
    print("Length of lis is", ans[0])
    print("The longest sequence is", ", ".join(to_str(x) for x in ans[1]))

def start():
    number_list = generate_numbers()

    while True:
        print_menu()
        option = input("Enter option =")
        if option == '1':
            add_number_ui(number_list)
        elif option == '2':
            print_list(number_list)
        elif option == '3':
            print_menu2()
            option2 = input(" Enter option =")
            if option2 == '1':
                display_longest_distinct_sequence(number_list)
            elif option2 == '2':
                longest_sequence_of_increasing_modulus(number_list)
            else:
                print("Invalid option!")
        elif option == '4':
            return
        else:
            print("Invalid option!")


start()


