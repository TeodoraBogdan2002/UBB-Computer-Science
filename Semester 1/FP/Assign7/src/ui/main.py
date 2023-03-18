from src.ui.uiFunctions import UI
from src.services.service import *
from src.repository.repo import *


def start():
    print("1. Use InMemory repo")
    print("2. Use text file repo")
    print("3. Use BinFile repo")
    option = int(input("Enter your option: "))
    while True:

        if option==1:
            repo=Repository()
            repo.generate_students()
            service = StudentService(repo)
            ui = UI(service)
            ui.run()
        elif option==2:
            repo = students_repo_text_file()
            service = StudentService(repo)
            ui = UI(service)
            ui.run()
        elif option==3:
            repo=student_repo_bin_file()
            # repo.generate_students_bin()
            service=StudentService(repo)
            ui=UI(service)
            ui.run()
        elif option==0:
            break
        else:
            print("Bad input")

if __name__=="__main__":
    start()