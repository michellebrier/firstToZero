package firstToZero;

/**
* @author Michelle Brier
*/

import java.util.Scanner;

public class Player {
	Player(int id) {
		_id = id;
		_position = null;
		_prompt = "Player " + Integer.toString(_id) + ": ";
	}

	int getId() {
		return _id;
	}

	void setPosition(Position pos) {
		_position = pos;
	} 

	Position myMove(Scanner scanner) {
		System.out.print(_prompt);
		String move = scanner.next();
		if (move.equals("q") || move.equals("quit")) {
			System.exit(0);
		}
		return new Position(Integer.parseInt(move));
	}

	private int _id;
	private Position _position;
	private String _prompt;
}
