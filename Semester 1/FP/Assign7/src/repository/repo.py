import pickle
from copy import deepcopy
from random import Random
import names
from src.domain.entity import Student


class RepositoryException(Exception):
    pass


class Repository:
    """This class implements the repository for our application
    """

    def __init__(self):
        """This constructor initializes a student repository
        :param n: number of initial students(default value 0)
        :type n: int
        """
        self.__students = {}
        self.__history = []

    def generate_students(self):
        rng = Random(23)
        for i in range(0, 10):
            id = i + 1
            name = names.get_first_name()
            group = rng.randint(1, 10)
            self.__students[id] = Student(id, name, group)

    def add_student(self, student):
        """This function adds a student to the repository
        raises RepositoryException if id is already in use
        :param student: student to be added
        :type student: Student
        :return: None
        """
        if student.id in self.__students:
            raise RepositoryException("Student id is already taken")
        self.__history.append(deepcopy(self.__students))
        self.__students[student.id] = student

    def get_students(self):
        """This function returns a list of the current students
        :return: list of students
        """

        student_list = []

        for student in self.__students.values():
            student_list.append(student)

        return student_list

    def filter(self, filter_function):
        """This function filters the current student dictionary using a filter function
        :param filter_function: the function with which to filter the students
        :return: None
        """
        self.__history.append(deepcopy(self.__students))
        new_students = {}
        for x in self.__students.values():
            if filter_function(x):
                new_students[x.id] = x
        self.__students = new_students

    def undo(self):
        """This function undoes the last operation which modified the internal structure
        raises RepositoryException if there is nothing to be undone
        :return: None
        """
        if len(self.__history) == 0:
            raise RepositoryException("Cannot undo any further")
        self.__students = self.__history[-1]
        self.__history.pop()


class students_repo_text_file(Repository):
    # this class inherits from car_repo
    # => has all the mathods and attributes in car_repo

    def __init__(self, file_name="students.txt"):
        # call superclass constructor
        super().__init__()
        # remember the name of the file we're working with
        self._file_name = file_name
        # load the cars from the file
        self._load_file()

    def _load_file(self):
        """
        Load the cars from a text file
        """
        # open a text file for reading
        # t - text file mode, r - reading
        lines = []

        try:
            fin = open(self._file_name, "rt")
            # each car should be on its own line
            lines = fin.readlines()
            # close the file when done reading
            fin.close()
        except IOError as err:
            print(err)

        for line in lines:
            current_line = line.split(",")
            new_stud = Student(int(current_line[0].strip()), current_line[1].strip(), int(current_line[2].strip()))
            # NOTE call super() so that we don't write the file we're reading from
            super().add_student(new_stud)

    def _save_file(self):
        """
        Save all cars to a text file
        """

        fout = open(self._file_name, "wt")

        for s in self.get_students():
            stud_string = str(s.id) + "," + s.name + "," + str(s.group) + "\n"
            fout.write(stud_string)

        fout.close()

    def add_student(self, new_stud: Student):
        super().add_student(new_stud)
        self._save_file()

    def filter(self, filter_function):
        super().filter(filter_function)
        self._save_file()

    def undo(self):
        super().undo()
        self._save_file()

class student_repo_bin_file(Repository):
    def __init__(self, file_name="students.bin"):
        super(student_repo_bin_file, self).__init__()
        self._file_name = file_name
        self._load_file()

    def add_student(self, new_stud: Student):
        super().add_student(new_stud)
        self._save_file()

    def _load_file(self):
        # self.generate_students_bin()
        fin = open(self._file_name, "rb")
        # rng = Random(23)
        # for i in range(0, 10):
        #     id = i + 1
        #     name = names.get_first_name()
        #     group = rng.randint(1, 10)
        #     self.add_student(Student(id, name, group))
        obj = pickle.load(fin)
        for c in obj:
            super().add_student(c)
        fin.close()

    def generate_students_bin(self):
        rng = Random(23)
        for i in range(0, 10):
            id = i + 1
            name = names.get_first_name()
            group = rng.randint(1, 10)
            self.add_student(Student(id, name, group))

    def _save_file(self):
        fout = open(self._file_name, "wb")
        pickle.dump(self.get_students(), fout)
        fout.close()

    def filter(self, filter_function):
        super().filter(filter_function)
        self._save_file()

    def undo(self):
        super().undo()
        self._save_file()