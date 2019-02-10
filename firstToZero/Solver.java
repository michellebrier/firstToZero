package firstToZero;

/**
* @author Michelle Brier
*/

import java.util.Arrays;

public class Solver extends Player {
	Solver(int totalPieces) {
		super(0);
		_position = null;
		_totalPieces = totalPieces;
		_memoized = new int[totalPieces + 1];
		Arrays.fill(_memoized, -1);
	}

	void setPosition(Position pos) {
		_position = pos;
	} 

	Position getPosition() {
		return _position;
	}

	void printValue(int val) {
		if (val == 1) {
			System.out.println("Win");
		} else {
			System.out.println("Lose");
		}
	}

	void printPositionValues() {
		System.out.println("===> CPU VALUES FOR POSITIONS:");
		for (int i = 0; i <= _totalPieces; i++) {
			System.out.print(i + ": ");
			printValue(gameOverValue(new Position(i)));
		}
		System.out.println("===> END");
	}

	int gameOverValue(Position pos) {
		int curr = pos.getPosInt();
		if (curr == 0) {
			return 0;
		} else if (_memoized[curr] != -1) {
			return _memoized[curr];
		}
		int one = gameOverValue(new Position(curr - 1));
		int two = curr > 1 ? gameOverValue(new Position(curr - 2)) : 1;
		int posVal = (one + two) < 2 ? 1 : 0;
		_memoized[curr] = posVal;
		return posVal;
	}

	Position myMove() {
		if (gameOverValue(_position) == 0) {
			// lose slowly
			return new Position(1);
		} else {
			// other player's value
			return gameOverValue(new Position(_position.getPosInt() - 1)) == 0 ? new Position(1) : new Position(2);
		}
	}

	private Position _position;
	private int _id;
	private int[] _memoized;
	private int _totalPieces;
}
