package firstToZero;

/**
* @author Michelle Brier
*/

import java.util.Arrays;
import java.util.ArrayList;

public class Solver extends Player {
	Solver(Game game, int totalPieces) {
		super(0);
		_game = game;
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
		if (_game.gameOver(pos)) {
			return 0;
		} else if (_memoized[curr] != -1) {
			return _memoized[curr];
		}
		int positionVal = 0;
		ArrayList<Position> validPos = _game.validNextPositions(pos);
		for (int i = 0; i < validPos.size(); i++) {
			if (gameOverValue(validPos.get(i)) == 0) {
				positionVal = 1;
				break;
			}
		}
		_memoized[curr] = positionVal;
		return positionVal;
	}

	Position myMove() {
		if (gameOverValue(_position) == 0) {
			// lose slowly
			return new Position(1);
		} else {
			ArrayList<Position> validPos = _game.validNextPositions(_position);
			for (int i = 0; i < validPos.size(); i++) {
				// other player's value
				if (gameOverValue(validPos.get(i)) == 0) {
					return new Position(_position.getPosInt() - validPos.get(i).getPosInt());
				}
			}
			return null;
		}
	}

	private Position _position;
	private int _id;
	private int[] _memoized;
	private int _totalPieces;
	private Game _game;
}
