"""
This is the validator module, with 2 validators: one for the student, one for the assignment
Validation for the grade is done in the ui and in the domain module class
"""
from src.exceptions import StudentException, AssignmentException


class StudentValidator:
    """
    Class for validating the student class
    """

    @staticmethod
    def validate(student):
        """
        Validates the student to have a valid id, valid name and valid group.
        :return: -
        """
        if len(str(student.name)) == 0:
            raise StudentException("The input for the name is empty.")
        if not student.name.replace(" ", "").isalpha():
            raise StudentException("Invalid student name!")
        if int(student.group) < 911 or int(student.group) > 917:
            raise StudentException("Invalid group!")

