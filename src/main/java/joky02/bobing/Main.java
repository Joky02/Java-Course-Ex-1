package joky02.bobing;

import joky02.bobing.game.GameStateMachine;
import joky02.bobing.schema.Player;
import joky02.bobing.schema.Prize;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
	static GameStateMachine gameStateMachine;
	static final Scanner in = new Scanner(System.in);

	private static void init() {

		System.out.println("Please Input the number of players");
		Player[] players = new Player[in.nextInt()];
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player(i + 1);
		}

		Prize[] prizes = new Prize[5];

		for (int i = 1; i <= 5; i++) {
			System.out.println("Please Input the number of " + Prize.toString(i));
			prizes[i - 1] = new Prize(i, in.nextInt());
		}

		gameStateMachine = new GameStateMachine(players, prizes);
	}

	public static void main(String[] args) {

		init();

		for (; ; ) {
			boolean isEnd = gameStateMachine.nextRound();
			System.out.println(gameStateMachine.currentPlayer.id +  ": " + Prize.fromDices(gameStateMachine.dices));
			Arrays.stream(gameStateMachine.dices).forEach(d -> 	System.out.print(d.pip + " "));
			System.out.println();

			if (isEnd) {
				gameStateMachine.prizeRecords.forEach(pr -> System.out.println(pr.player.id +  ": " +  pr.prize));
				break;
			}
		}
	}
}
