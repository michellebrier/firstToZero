package firstToZero;

/** 
@author Michelle Brier
**/

import java.util.Scanner;
import java.util.ArrayList;

import static firstToZero.Game.State.*;
import firstToZero.*;
import firstToZero.Solver.*;

class Game {
	static enum State {
		SETUP, PLAYING;
	}

	Game() {
		_state = SETUP;
	}

	void process() {
		Player one, two;
		Solver cpu;
		cpu = null;
		one = two = null;
		while (true) {
			if (_state == SETUP) {
				cpu = null;
				one = two = null;
				_cpuOn = false;
				_piecesLeft = 50;
				System.out.println("====================================================");
				System.out.println("Be the first one to get to zero!");
				_scanner = new Scanner(System.in);
				_state = PLAYING;
			}

			String decision = "";
			while (!decision.equals("y") && !decision.equals("n")) {
				checkQuit(decision);
				System.out.print("===> Play against CPU? (y/n): ");
				decision = _scanner.next();
			}
			if (decision.equals("y")) {
				cpu = new Solver(this, _piecesLeft);
				cpu.setPosition(new Position(_piecesLeft));
				_currPlayer = cpu;
				_cpuOn = true;
				String input = "";
				while (!input.equals("y") && !input.equals("n")) {
					checkQuit(input);
					System.out.println("===> Print game over values for all positions? (y/n): ");
					input = _scanner.next();
				}
				if (input.equals("y")) {
					cpu.printPositionValues();
				}
			} else {
				one = new Player(1);
				one.setPosition(new Position(_piecesLeft));
				_currPlayer = one;
			}

			two = new Player(2);

			while (_state != SETUP && !gameOver()) {
				Position move;
				if (_currPlayer.getId() == 1) {
					move = one.myMove(_scanner);
					while (!checkMove(move) ) {
						move = one.myMove(_scanner);
					}
					_currPlayer = two;
				} else if (_currPlayer.getId() == 2) {
					move = two.myMove(_scanner);
					while (!checkMove(move) ) {
						move = two.myMove(_scanner);
					}
					_currPlayer = _cpuOn ? cpu : one;
				} else {
					move = cpu.myMove();
					_currPlayer = two;
				}
				doMove(move);
			}

			if (_state == PLAYING) {
				reportWinner();
			}

			_state = SETUP;
		}
	}

	boolean checkMove(Position move) {
		int moveInt = move.getPosInt();
		if (moveInt != 1 && moveInt != 2) {
			System.out.println("===> Invalid move. You may only take 1 or 2 pieces a turn.");
			return false;
		}
		if (moveInt > _piecesLeft) {
			System.out.println("===> Invalid move. You can't take more than the number of remaining pieces.");
			String msg = _piecesLeft == 1 ? "===> There is " + _piecesLeft + " piece left on the board." : "===> There are " + _piecesLeft + " pieces left on the board.";
			System.out.println(msg);
			return false;
		}
		return true;
	}

	void doMove(Position move) {
		_piecesLeft -= move.getPosInt();
		// currPlayer is the next player at this point, but original player is still executing move
		_currPlayer.setPosition(new Position(_piecesLeft));
		if (_cpuOn && _currPlayer.getId() != 0) {
			String cpuMsg = move.getPosInt() == 1 ? "CPU takes " + move.getPosInt() + " piece." : "CPU takes " + move.getPosInt() + " pieces.";
			System.out.println(cpuMsg);
		}
		String msg = _piecesLeft == 1 ? "===> " + _piecesLeft + " piece left on the board." : "===> " + _piecesLeft + " pieces left on the board.";
		System.out.println(msg);
	}

	ArrayList<Position> validNextPositions(Position pos) {
		ArrayList<Position> ret = new ArrayList<>();
		ret.add(new Position(pos.getPosInt() - 1));
		if (pos.getPosInt() > 1) {
			ret.add(new Position(pos.getPosInt() - 2));
		}
		return ret;
	}

	boolean gameOver() {
		return _piecesLeft == 0;
	}

	boolean gameOver(Position pos) {
		return pos.getPosInt() == 0;
	}

	void reportWinner() {
		String msg = "Player 1 wins.";
		if (_currPlayer.getId() == 1 || _currPlayer.getId() == 0) {
			msg = "Player 2 wins.";
		} else if (_currPlayer.getId() == 2 && _cpuOn) {
			msg = "CPU wins.";
		}
		System.out.println("====================================================");
		System.out.println(msg);
		System.out.println("====================================================");
		System.out.println("Play again!");
	}

	void checkQuit(String input) {
		if (input.equals("q") || input.equals("quit")) {
			System.exit(0);
		}
	}

	private Scanner _scanner;
	private State _state;
	private int _piecesLeft;
	private Player _currPlayer;
	private boolean _cpuOn;
}
