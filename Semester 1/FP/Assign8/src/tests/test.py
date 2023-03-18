import unittest
from datetime import date

from src.exceptions import RepositoryException, StudentException, AssignmentException
from src.domain.domain import Student, Assignment, Grade
from src.validators.validator import StudentValidator


class TestStudent(unittest.TestCase):
    def test_student(self):
        student1 = Student("43kdss", "Brinza Alina", 911)
        self.assertEqual(student1.student_id, "43kdss")
        self.assertEqual(student1.name, "Brinza Alina")
        self.assertEqual(student1.group, 911)
        self.assertEqual(str(student1), "ID: 43kdss , Name: Brinza Alina, from group 911")

    def test_name_and_group_setter(self):
        student2 = Student("42kdss", "Brinza Alina", 911)
        student2.name = "Alina"
        student2.group = 912
        self.assertEqual(student2.name, "Alina")
        self.assertEqual(student2.group, 912)

    def test_equal(self):
        student3 = Student("123456", "Alina", 911)
        student4 = Student("654321", "Elena", 912)
        self.assertNotEqual(student3, student4)


class TestStudentValidator(unittest.TestCase):
    def test_student_validator_student_id(self):
        student1 = Student("3sew2e", "Brinza Alina", 911)
        StudentValidator.validate(student1)
        try:
            student1 = Student("3sew2", "Brinza Alina", 911)
            StudentValidator.validate(student1)
        except StudentException as se:
            self.assertEqual(str(se), "Student id must be a combination of 6 letters and numbers.")

    def test_student_validator_name(self):
        student1 = Student("3sew2e", "Brinza Alina", 911)
        StudentValidator.validate(student1)

        try:
            student1 = Student("3sew2e", "", 911)
            StudentValidator.validate(student1)
        except StudentException as se:
            self.assertEqual(str(se), "The input for the name is empty.")

        try:
            student1 = Student("3sew2e", "Brinz2 Alina", 911)
            StudentValidator.validate(student1)
        except StudentException as se:
            self.assertEqual(str(se), "Invalid student name!")

    def test_student_validator_group(self):
        student1 = Student("3sew2e", "Brinza Alina", 911)
        StudentValidator.validate(student1)

        try:
            student1 = Student("3sew2e", "Brinza Alina", 0)
            StudentValidator.validate(student1)
        except StudentException as se:
            self.assertEqual(str(se), "Invalid group!")


class TestAssignment(unittest.TestCase):
    def test_assignment(self):
        assignment1 = Assignment("MLE0020", "Algebra", date(2020, 11, 19))
        self.assertEqual(assignment1.assignment_id, "MLE0020")
        self.assertEqual(assignment1.description, "Algebra")
        self.assertEqual(assignment1.deadline, date(2020, 11, 19))
        self.assertEqual(str(assignment1), "Assignment MLE0020 -> description: Algebra with deadline: 2020-11-19")

    def test_description_and_deadline_setter(self):
        assignment2 = Assignment("MLE0002", "Mathematical Analysis", date(2020, 11, 20))
        assignment2.description = "algebra"
        assignment2.deadline = date(2020, 12, 25)
        self.assertEqual(assignment2.description, "algebra")
        self.assertEqual(assignment2.deadline, date(2020, 12, 25))

    def test_equal(self):
        assignment3 = Assignment("MLE5004", "CSA", date(2020, 11, 17))
        assignment4 = Assignment("MLE2004", "CAS", date(2020, 12, 14))
        self.assertNotEqual(assignment4, assignment3)


class TestGrade(unittest.TestCase):
    def test_grade(self):
        grade1 = Grade("13fr3e", "MLE0020", 10)
        self.assertEqual(grade1.student_id, "13fr3e")
        self.assertEqual(grade1.assignment_id, "MLE0020")
        self.assertEqual(grade1.grade, 10)
        self.assertEqual(str(grade1), "The grade for student 13fr3e for the assignment MLE0020 is 10.0")
        grade2 = Grade("123456", "MLE0000", 0)
        self.assertEqual(str(grade2), "Assigned to student 123456 the assignment MLE0000")

    def test_grade_setter(self):
        grade3 = Grade("123457", "MLE0000", 0)
        grade3.grade = 10
        self.assertEqual(grade3.grade, 10)

    def test_equal(self):
        grade4 = Grade("102020", "MLE0000", 9)
        grade5 = Grade("100000", "MLE0000", 9)
        self.assertNotEqual(grade5, grade4)
