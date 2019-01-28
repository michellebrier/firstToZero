package firstToZero;

/** 
@author Michelle Brier
**/

import java.util.Scanner;

import static firstToZero.Game.State.*;
import firstToZero.*;

class Game {
	static enum State {
		SETUP, PLAYING;
	}

	Game() {
		_piecesLeft = 10;
		_state = SETUP;
	}

	void process() {
		Player one, two;
		one = two = null;

		while (true) {
			if (_state == SETUP) {
				System.out.println("====================================================");
				System.out.println("Be the first one to get to zero!");
				_scanner = new Scanner(System.in);
				_state = PLAYING;
				_piecesLeft = 10;
			}

			one = new Player(this, 1);
			one.setPosition(new Position(10));
			two = new Player(this, 2);

			_currPlayer = one;

			while (_state != SETUP && !gameOver()) {
				Position move;
				if (_currPlayer.getId() == 1) {
					move = one.myMove(_scanner);
					_currPlayer = two;
				} else {
					move = two.myMove(_scanner);
					_currPlayer = one;
				}
				doMove(move);
			}

			if (_state == PLAYING) {
				reportWinner();
			}

			_state = SETUP;
		}
	}

	void doMove(Position move) {
		_piecesLeft -= move.getPosInt();
		_currPlayer.setPosition(new Position(_piecesLeft));
		System.out.println(_piecesLeft + " pieces left on the board.");
	}

	boolean gameOver() {
		return _piecesLeft == 0;
	}

	void reportWinner() {
		String msg = "Player 1 wins.";
		if (_currPlayer.getId() == 1) {
			msg = "Player 2 wins.";
		}
		System.out.println("====================================================");
		System.out.println(msg);
		System.out.println("Play again!");
	}

	private Scanner _scanner;
	private State _state;
	private int _piecesLeft;
	private Player _currPlayer;
}
