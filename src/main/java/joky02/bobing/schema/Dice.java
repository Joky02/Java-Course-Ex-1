package joky02.bobing.schema;

import java.util.Random;

public class Dice {
	public int pip;

	public void rotate() {
		pip = new Random().nextInt();
	}
}
