package firstToZero;

/**
* @author Michelle Brier
*/

import java.util.ArrayList;

public class Position {
	Position(int numPieces) {
		_numPieces = numPieces;
	}

	int getPosInt() {
		return _numPieces;
	}

	private int _numPieces;
}
