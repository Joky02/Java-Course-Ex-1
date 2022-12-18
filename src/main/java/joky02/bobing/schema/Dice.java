package joky02.bobing.schema;

import java.util.Random;

public class Dice {
	public int pip;

	public Dice() {
		pip = 0;
	}

	public static Dice[] initArray() {
		Dice[] dices = new Dice[6];

		for (int i = 0; i < dices.length; i++)
			dices[i] = new Dice();
		return dices;
	}

	public void rotate() {
		pip = new Random().nextInt(6) + 1;
	}
}
