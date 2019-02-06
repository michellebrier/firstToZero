package firstToZero;

/**
* @author Michelle Brier
*/

import java.util.ArrayList;

public class Solver extends Player {
	static enum Value {
		WINNING, LOSING;
	}

	Solver() {
		super(0);
		_position = null;
	}

	void setPosition(Position pos) {
		_position = pos;
	} 

	Position getPosition() {
		return _position;
	}

	ArrayList<Position> generateMoves() {
		ArrayList<Position> ret = new ArrayList<>();
		int currPosition = _position.getPosInt();
		Position takeOne = new Position(currPosition - 1);
		ret.add(takeOne);
		if (_position.getPosInt() - 2 >= 0) {
			Position takeTwo = new Position(currPosition - 2);
			ret.add(takeTwo);
		}
		return ret;
	}

	void printValue(Value val) {
		if (val == Value.WINNING) {
			System.out.println("Win");
		} else {
			System.out.println("Lose");
		}
	}

	void printPositionValues() {
		System.out.println("===> CPU VALUES FOR POSITIONS 0 - 10:");
		for (int i = 0; i < 11; i++) {
			System.out.print(i + ": ");
			printValue(gameOverValue(new Position(i)));
		}
		System.out.println("===> END");
	}

	Value gameOverValue(Position pos) {
		int curr = pos.getPosInt();

		if (curr == 0) {
			return Value.LOSING;
		}

		Value one = gameOverValue(new Position(curr - 1));
		Value two = Value.LOSING;
		boolean twoBranches = false;
		if (curr > 1) {
			two = gameOverValue(new Position(curr - 2));
			twoBranches = true;
		}

		if ((twoBranches && (one == Value.LOSING || two == Value.LOSING))
			|| (!twoBranches && one == Value.LOSING)) {
			return Value.WINNING;
		}
		return Value.LOSING;
	}

	Value solve(Position pos) {
		ArrayList<Position> moves = generateMoves();
		for (Position move : moves) {
			Value moveVal = gameOverValue(move);
			if (moveVal == Value.LOSING) {
				return Value.WINNING;
			}
		}
		return Value.LOSING;
	}

	Position myMove() {
		if (solve(_position) == Value.LOSING) {
			// lose slowly
			return new Position(1);
		} else {
			ArrayList<Position> moves = generateMoves();
			// other player's value
			if (gameOverValue(moves.get(0)) == Value.LOSING) {
				return new Position(1);
			} else {
				return new Position(2);
			}
		}
	}

	private Position _position;
	private int _id;
}
