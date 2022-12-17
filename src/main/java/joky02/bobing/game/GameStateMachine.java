package joky02.bobing.game;

import joky02.bobing.schema.Dice;
import joky02.bobing.schema.Player;
import joky02.bobing.schema.Prize;
import joky02.bobing.schema.PrizeRecord;

import java.util.ArrayList;
import java.util.Arrays;

public class GameStateMachine {

	Dice[] dices;
	Player[] players;
	Prize[] prizes;
	ArrayList<PrizeRecord> prizeRecords;

	static int currentPlayer;
	static PrizeRecord currentChampion;


	public GameStateMachine(Player[] players, Prize[] prizes) {
		dices = new Dice[6];
		this.players = players;
		this.prizes = prizes;
		currentPlayer = 0;
		currentChampion = new PrizeRecord(new Player(0), 0);
	}

	private boolean isGameEnd() {
		return Arrays.stream(prizes).filter(p -> Prize.isChampion(p.level)).noneMatch(p -> p.count > 0);
	}

	/**
	 * @return Whether the game ends.
	 */
	public boolean nextRound() {

		Arrays.stream(dices).forEach(Dice::rotate);

		int result = Prize.fromDices(dices);

		if (result > 0) {
			if (result == 9) {
				prizeRecords.forEach(pr -> pr.player = players[currentPlayer]);
				Arrays.stream(prizes).forEach(p -> {
					while (p.count > 0) {
						prizeRecords.add(new PrizeRecord(players[currentPlayer], p.level));
						p.count--;
					}
				});
			} else if (Prize.isChampion(result)) {
				if (result > currentChampion.prize) {
					currentChampion.player = players[currentPlayer];
					currentChampion.prize = result;
				}
			} else {
				if (prizes[result - 1].count > 0) {
					prizes[result - 1].count--;
					prizeRecords.add(new PrizeRecord(players[currentPlayer], result));
				}
			}
		}


		currentPlayer = (currentPlayer + 1) % players.length;

		if (isGameEnd()) {
			if (currentChampion.prize > 0)
				prizeRecords.add(currentChampion);
			return true;
		} else return false;
	}
}
