"""
  Program functionalities module
"""
import copy

APART_NR = 0
BILL_TYPE = 1
AMOUNT = 2


def create_expenses(apart_nr, bill_type, amount):
    """
     It creates a dictionary for each monthly expense for each apartment
    :param apart_nr: the number of the apartment
    :param bill_type: the type of the bill
    :param amount: the amount of money for each apartment for a type of bill
    :return: the monthly expense for each apartment
    """
    if apart_nr < 1 or amount < 1:
        raise ValueError('Cannot create expense using given data!')

    return {'apartment': apart_nr, 'type': bill_type, 'amount': amount}


def get_apart_nr(expense):
    return expense['apartment']


def get_bill_type(expense):
    return expense['type']


def get_amount(expense):
    return expense['amount']


def generate_expense():
    return [create_expenses(3, "water", 100),
            create_expenses(5, "gas", 340),
            create_expenses(6, "other", 150),
            create_expenses(9, "electricity", 500),
            create_expenses(15, "water", 250),
            create_expenses(16, "other", 120),
            create_expenses(18, "water", 300),
            create_expenses(20, "gas", 340)
            ]


def add_expense(expense_list, expense):
    """
    It adds a new expense
    :param expense_list: the list of expenses which are managed by Jane
    :param expense: the new expense
    :return:
    """
    expense_list.append(expense)


def remove_expense(expense_list, apart_nr):
    """
    It removes an expense which has the number of apartment given from the keyboard
    :param expense_list: the list of expenses
    :param apart_nr: the number of apartment we are looking for
    :return:
    """
    for i in expense_list:
        if get_apart_nr(i) == int(apart_nr):
            expense_list.remove(i)


def sum_amounts(expense_list, apart_nr):
    """
    The sum of all amounts for one apartment, which number is given from the keyboard
    :param expense_list:the list of expenses
    :param apart_nr:the apartment we are calculating the sum for
    :return:the sum of all amounts for the apartment
    """
    summ = 0
    for i in expense_list:
        if get_apart_nr(i) == int(apart_nr):
            summ = summ + int(get_amount(i))
    return summ


def remove_expense_type(expense_list, bill_type):
    """
    Removes the expenses that have the same type with the command given
    :param expense_list: the list of expenses
    :param bill_type: the type of bill that will be removed from the list
    :return:
    """
    found_expense=False
    to_filter = bill_type
    for index in expense_list[::-1]:
        if get_bill_type(index) == str(to_filter):
            found_expense=True
            expense_list.remove(index)

    return found_expense


def replace_a(expense_list, command_parameters):
    """
    replaces the the amount of a expense given with another amount
    :param expense_list: the list of expenses
    :param command_parameters: the parameters given from keyboard
    :return:
    """
    tokens = command_parameters.split()
    for i in range(0, len(expense_list)):
        if get_apart_nr(expense_list[i]) == int(tokens[0]) and get_bill_type(expense_list[i]) == str(tokens[1]):
             expense_list[i] = create_expenses(get_apart_nr(expense_list[i]), get_bill_type(expense_list[i]), int(tokens[3]))



def undo_operation(list_undone):
    """
    it undo the last operation
    :param list_undone:
    :return:
    """
    if len(list_undone) == 0:
        raise ValueError("The list is unmodified")
    new_list = copy.deepcopy(list_undone[len(list_undone) - 1])
    list_undone.pop()  # removes the last dictionary from the list
    return new_list, list_undone


def test_create():
    assert create_expenses(25, "other", 200) == {"apartment": 25, "type": "other", "amount": 200}
    assert create_expenses(26, "water", 20) == {"apartment": 26, "type": "water", "amount": 20}
    assert create_expenses(30, "gas", 210) == {"apartment": 30, "type": "gas", "amount": 210}


def test_add(test_expenses):
    add_expense(test_expenses, create_expenses(7, "gas", 300))
    assert test_expenses == [{"apartment": 7, "type": "gas", "amount": 300}]

    add_expense(test_expenses, create_expenses(7, "other", 200))
    assert test_expenses == [{"apartment": 7, "type": "gas", "amount": 300},
                             {"apartment": 7, "type": "other", "amount": 200}]

    add_expense(test_expenses, create_expenses(4, "other", 100))
    assert test_expenses == [{"apartment": 7, "type": "gas", "amount": 300},
                             {"apartment": 7, "type": "other", "amount": 200},
                             {"apartment": 4, "type": "other", "amount": 100}]


def test_remove_expense(test_e):
    remove_expense(test_e, 25)
    assert test_e == [{"apartment": 7, "type": "gas", "amount": 300},
                      {"apartment": 7, "type": "other", "amount": 200},
                      {"apartment": 4, "type": "other", "amount": 100}]

    remove_expense(test_e, 7)
    assert test_e == [{"apartment": 7, "type": "other", "amount": 200},{"apartment": 4, "type": "other", "amount": 100}]





