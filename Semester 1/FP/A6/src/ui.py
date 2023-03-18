"""
  User interface module
"""

from functions import create_expenses, add_expense, get_apart_nr, get_bill_type, get_amount, sum_amounts, \
    remove_expense, remove_expense_type, \
    test_add, test_create, test_remove_expense, replace_a


def add_expenses_command(expense_list, command_params):
    expenses_tokens = command_params.split(";")
    type_list = ["water", "heating", "gas", "electricity", "other"]
    for tokens in expenses_tokens:
        try:
            ok = 1
            apart_nr, bill_type, amount = tokens.split()
            expense = create_expenses(int(apart_nr.strip()), bill_type.strip(), int(amount.strip()))
            if bill_type not in type_list:
                ok = 0
            if ok == 1:
                add_expense(expense_list, expense)
            elif ok == 0:
                print("We don't have this type of bill!")

        except ValueError as ve:
            print(str(ve))


def show_all_expenses(expense_list):
    try:
        for i in expense_list:
            print(str(get_apart_nr(i)), get_bill_type(i), str(get_amount(i)))
    except ValueError as ve:
        print(str(ve))


def show_expenses_command(expense_list, command_param):
    try:
        for i in expense_list:
            if get_apart_nr(i) == int(command_param):
                print(str(get_apart_nr(i)), get_bill_type(i), str(get_amount(i)))
    except ValueError as ve:
        print(str(ve))


def list_all_amounts_command(expense_list, command_parameters):
    signn, summ = command_parameters.split()
    signn.strip()
    summ.strip()
    try:
        for i in expense_list:
            if signn == '=' and sum_amounts(expense_list, get_apart_nr(i)) == int(summ):
                print(str(get_apart_nr(i)), get_bill_type(i), str(get_amount(i)))
            elif signn == '<' and sum_amounts(expense_list, get_apart_nr(i)) < int(summ):
                print(str(get_apart_nr(i)), get_bill_type(i), str(get_amount(i)))
            elif signn == '>' and sum_amounts(expense_list, get_apart_nr(i)) > int(summ):
                print(str(get_apart_nr(i)), get_bill_type(i), str(get_amount(i)))

    except ValueError as ve:
        print(str(ve))


def remove_expense_command(expense_list, command_parameters):
    try:
        for i in expense_list:
            if get_apart_nr(i) == int(command_parameters):
                remove_expense(expense_list, int(command_parameters))
    except ValueError as ve:
        print(str(ve))


def remove_type_command(expense_list, command_param):
    try:
        val = remove_expense_type(expense_list,command_param)
        return val
    except ValueError as ve:
        print(str(ve))


def delete_apartment_range(expense_list, command_parameters):
    low, high = command_parameters.split('to')
    low.strip()
    high.strip()
    try:
        low = int(low)
        high = int(high)
        for index in range(low, high + 1):
            remove_expense(expense_list, int(index))
    except ValueError as ve:
        print(str(ve))


def replace_ui(expense_list, command_params):
    listt = replace_a(expense_list, command_params)
    for i in listt:
        print(str(get_apart_nr(i)), get_bill_type(i), str(get_amount(i)))


def filter_type(expense_list, command_params):
    to_filter=command_params
    for index in expense_list[::-1]:
        if index['type'] != str(to_filter):
            expense_list.remove(index)


def filter_amounts_smaller(expense_list, command_params):
    for i in expense_list:
        if get_amount(i) > int(command_params):
            remove_expense(expense_list, get_apart_nr(i))



def tests():
    test_e = []
    test_create()
    test_add(test_e)
    test_remove_expense(test_e)
