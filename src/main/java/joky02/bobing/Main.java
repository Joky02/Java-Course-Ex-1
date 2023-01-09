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

		System.out.print("请输入玩家人数：");
		Player[] players = new Player[in.nextInt()];
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player(i + 1);
		}

		Prize[] prizes = new Prize[5];

		for (int i = 1; i <= 5; i++) {
			System.out.print("请输入" + Prize.toString(i) + "奖的数量：");
			prizes[i - 1] = new Prize(i, in.nextInt());
		}

		gameStateMachine = new GameStateMachine(players, prizes);
	}

	public static void main(String[] args) {

		init();

		for (; ; ) {
			int status = gameStateMachine.nextRound();

			System.out.println("现在到玩家 " + gameStateMachine.currentPlayer.id + " 丢骰子");
			pressEnterToContinue();
			System.out.println("你丢的骰子是：");
			Arrays.stream(gameStateMachine.dices).forEach(d -> System.out.print(d.pip + " "));
			System.out.println();
			if (status == 0) {
				System.out.println("很遗憾，没有获奖");
			} else {
				System.out.println("恭喜玩家 " + gameStateMachine.currentPlayer.id + "，获得了" + Prize.toString(Prize.fromDices(gameStateMachine.dices)) + "奖");
				if (status == 2) {
					System.out.println("游戏结束！");
					gameStateMachine.prizeRecords.forEach(pr -> System.out.println("玩家 " + pr.player.id + " 获得了" + Prize.toString(pr.prize) + "奖"));
					break;
				}
			}
			System.out.println();
		}
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	private static void pressEnterToContinue() {

		System.out.print("请按任意键继续...");
		try {
			System.in.read();
		} catch (Exception ignored) {
		}
	}
}
