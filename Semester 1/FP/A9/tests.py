import unittest

from termcolor import colored

from domain import Board, Circle, Player
from service import BoardService


class Tests(unittest.TestCase):
    def test_circle(self):
        circle1 = Circle("yellow")
        circle2 = Circle("magenta")
        self.assertEqual(circle1.color, "yellow")
        self.assertEqual(circle2.color, "magenta")
        self.assertEqual(str(circle1), colored('●', "yellow"))
        self.assertEqual(str(circle2), colored('●', "magenta"))

    def test_board(self):
        circle1 = Circle("yellow")
        circle2 = Circle("magenta")
        board = Board()
        board_service = BoardService()
        self.assertEqual(board.matrix[0][1], '●')
        self.assertEqual(str(board), ' | 1 | 2 | 3 | 4 | 5 | 6 | 7 |\n'
                                     ' - - - - - - - - - - - - - - -\n'
                                     ' | ● | ● | ● | ● | ● | ● | ● | \n'
                                     ' - - - - - - - - - - - - - - -\n'
                                     ' | ● | ● | ● | ● | ● | ● | ● | \n'
                                     ' - - - - - - - - - - - - - - -\n'
                                     ' | ● | ● | ● | ● | ● | ● | ● | \n'
                                     ' - - - - - - - - - - - - - - -\n'
                                     ' | ● | ● | ● | ● | ● | ● | ● | \n'
                                     ' - - - - - - - - - - - - - - -\n'
                                     ' | ● | ● | ● | ● | ● | ● | ● | \n'
                                     ' - - - - - - - - - - - - - - -\n'
                                     ' | ● | ● | ● | ● | ● | ● | ● | \n'
                                     ' - - - - - - - - - - - - - - -\n')
        board_service.move(board, circle1, 1)
        board_service.move(board, circle2, 2)
        self.assertEqual(board_service.is_game_won(board), False)
        board_service.move(board, circle1, 1)
        board_service.move(board, circle2, 3)
        self.assertEqual(board_service.is_game_won(board), False)
        board_service.move(board, circle1, 1)
        board_service.move(board, circle2, 4)
        self.assertEqual(board_service.is_game_won(board), False)
        board_service.move(board, circle1, 1)
        self.assertEqual(board_service.is_game_won(board), True)

        board2 = Board()
        board_service.move(board2, circle1, 1)
        board_service.move(board2, circle2, 1)
        board_service.move(board2, circle1, 2)
        board_service.move(board2, circle2, 2)
        board_service.move(board2, circle1, 3)
        board_service.move(board2, circle2, 1)
        board_service.move(board2, circle1, 4)
        self.assertEqual(board_service.is_game_won(board2), True)

    def test_player(self):
        player1 = Player("Teo", colored('●', 'yellow'))
        player2 = Player("Ana", colored('●', 'magenta'))
        self.assertEqual(player1.get_name, 'Teo')
        self.assertEqual(player2.get_circle, colored('●', 'magenta'))
        self.assertEqual(str(player1), "Teo is playing with circles of this color: {}".format(colored('●', 'yellow')))