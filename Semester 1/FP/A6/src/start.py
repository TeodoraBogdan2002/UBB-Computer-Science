"""
  Start the program by running this module
"""
import copy
from functions import generate_expense, undo_operation, replace_a
from ui import add_expenses_command, show_all_expenses, show_expenses_command, list_all_amounts_command, \
    remove_expense_command, delete_apartment_range, remove_type_command, \
    filter_type, filter_amounts_smaller, tests

tests()


def star_command():
    expenses_list = generate_expense()
    list_undo = []

    while True:
        try:
            command = input("command> ")
            tokens = command.split(" ", maxsplit=1)
            command_word = tokens[0].lower() if len(tokens) > 0 else None
            command_params = tokens[1].lower() if len(tokens) == 2 else None
            print(command_word, command_params)
            listt = ["water", "heating", "gas", "electricity", "other"]

            if command_word == 'add':
                copy_of_list = copy.deepcopy(expenses_list)  # i make a copy of the list of dictionaries
                add_expenses_command(expenses_list, command_params)
                list_undo.append(copy_of_list)  # i put the copy in the list which i will use in undo operation,
                # a backup and i do this at every operation that modifies the list
            elif command_word == 'list':
                if command_params is None:
                    show_all_expenses(expenses_list)

                elif str(len(command_params.split())) == '1':
                    show_expenses_command(expenses_list, command_params)

                elif str(len(command_params.split())) == '2':
                    list_all_amounts_command(expenses_list, command_params)

            elif command_word == 'remove':
                if str(len(command_params.split())) == '1' and command_params.isdigit() is True:
                    copy_of_list = copy.deepcopy(expenses_list)
                    remove_expense_command(expenses_list, command_params)
                    list_undo.append(copy_of_list)

                elif str(len(command_params.split())) == '1' and command_params in listt:
                    copy_of_list = copy.deepcopy(expenses_list)

                    expense_found = remove_type_command(expenses_list, command_params)
                    # remove_type_command(expenses_list, command_params)
                    if not expense_found:
                        raise ValueError("Expense not found!")
                    else:
                        print("Removed the solicited expense!")
                    list_undo.append(copy_of_list)

                elif str(len(command_params.split())) == '1' and command_params not in listt:
                    print("we don't have this type of bill!")
                else:
                    copy_of_list = copy.deepcopy(expenses_list)
                    delete_apartment_range(expenses_list, command_params)
                    list_undo.append(copy_of_list)

            elif command_word == 'replace':
                copy_of_list = copy.deepcopy(expenses_list)
                replace_a(expenses_list, command_params)
                list_undo.append(copy_of_list)

            elif command_word == 'filter':
                copy_of_list = copy.deepcopy(expenses_list)
                filter_type(expenses_list, command_params)
                list_undo.append(copy_of_list)

            elif command_word == 'filter_a':
                copy_of_list = copy.deepcopy(expenses_list)
                filter_amounts_smaller(expenses_list, int(command_params))
                list_undo.append(copy_of_list)

            elif command_word == 'undo':
                expenses_list, list_undo = undo_operation(list_undo)

            elif command_word == 'exit':
                return
            else:
                print("Bad command!")
        except ValueError as ve:
            print(str(ve))
        except AttributeError as ae:
            print(str(ae))

star_command()
