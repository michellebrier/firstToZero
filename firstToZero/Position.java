package firstToZero;

/**
* @author Michelle Brier
*/

import static firstToZero.Command.Type.*;
import java.util.ArrayList;

public class Position {
	// enum Status {
	// 	WINNING, LOSING;
	// }

	Position(int numPieces) {
		_numPieces = numPieces;
	}

	int getPosInt() {
		return _numPieces;
	}

	// Status calcStatus() {
	// 	if (_numPieces % 3 == 0) {
	// 		return LOSING;
	// 	}
	// 	return WINNING;
	// }

	ArrayList<Position> generateMoves() {
		ArrayList<Position> ret = new ArrayList<>();
		Position takeOne = new Position(_numPieces - 1);
		ret.add(takeOne);
		if (_numPieces - 2 >= 0) {
			Position takeTwo = new Position(_numPieces - 2);
			ret.add(takeTwo);
		}
		return ret;
	}

	private int _numPieces;
}
