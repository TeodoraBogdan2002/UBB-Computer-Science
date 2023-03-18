# Solve the problem from the third set here
"""
12. Determine the age of a person, in number of days. Take into account leap years, as well as the date of birth and current date (year, month, day).
    Do not use Python's inbuilt date/time functions.
"""


def count_leapyears(yr):  # it calculates the number of leap years until now
    return yr // 4


def leapYrNr(y1, y2):  # it calculates the number of leap years between two yers read from the keyboard
    y1 = y1 - 1
    nr1 = count_leapyears(y1)
    nr2 = count_leapyears(y2)
    return nr2 - nr1


def age(current_day, current_m, current_y, bd, bm, by, rr):
    md = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    y = current_y - by  # y represents the age in years
    if current_m < bm:  # if the number of the current mounth is smaller than the birthday mounth, then from age in
                        # years decreases 1, because the person has not yet turned y years
        y = y - 1
        m = 12 - (bm - current_m)  # how many mounths are between the current mounth and the birthday mounth
    else:
        m = current_m - bm  # how many mounths are between the current mounth and the birthday mounth

    if current_day < bd:
        m = m - 1
        d = md[current_m - 1] - (bd - current_day)  # how many days are since the bd day of last month
    else:
        d = current_day - bd + 1

    xx = y - rr
    final = d + m * 30 + xx * 365 + rr * 366
    return final


def main_prb12():
    byear = int(input("Enter the birthday year:"))
    bmounth = int(input("Enter the birthday month: "))
    bday = int(input("Ennter the birth day: "))
    pyear = int(input("Enter the current year:"))
    pmounth = int(input("Enter the current month: "))
    pday = int(input("Ennter the current day: "))
    rr = leapYrNr(byear, pyear)
    return age(pday, pmounth, pyear, bday, bmounth, byear, rr)


print(main_prb12())
