package joky02.bobing.game;

import joky02.bobing.schema.Dice;
import joky02.bobing.schema.Player;
import joky02.bobing.schema.Prize;
import joky02.bobing.schema.PrizeRecord;

import java.util.ArrayList;
import java.util.Arrays;

public class GameStateMachine {

	public Dice[] dices;
	Player[] players;
	Prize[] prizes;
	public ArrayList<PrizeRecord> prizeRecords;

	public Player currentPlayer;
	private int currentPlayerId;
	private final PrizeRecord CURRENT_CHAMPION;


	public GameStateMachine(Player[] players, Prize[] prizes) {
		dices = Dice.initArray();
		this.players = players;
		this.prizes = prizes;
		currentPlayerId = -1;
		CURRENT_CHAMPION = new PrizeRecord(new Player(0), 0);
		prizeRecords = new ArrayList<>();
	}

	private boolean isGameEnd() {
		return Arrays.stream(prizes).filter(p -> !Prize.isChampion(p.level)).noneMatch(p -> p.count > 0);
	}

	/**
	 * @return Whether the game ends.
	 */
	public boolean nextRound() {

		currentPlayerId = (currentPlayerId + 1) % players.length;
		currentPlayer = players[currentPlayerId];
		Arrays.stream(dices).forEach(Dice::rotate);

		int result = Prize.fromDices(dices);

		if (result > 0) {
			if (Prize.isChampion(result)) {
				if (result > CURRENT_CHAMPION.prize) {
					CURRENT_CHAMPION.player = currentPlayer;
					CURRENT_CHAMPION.prize = result;
				}

				if (result == 9) {
					prizeRecords.forEach(pr -> pr.player = currentPlayer);
					Arrays.stream(prizes).forEach(p -> {
						while (p.count > 0) {
							prizeRecords.add(new PrizeRecord(currentPlayer, p.level));
							p.count--;
						}
					});
				}
			} else {
				if (prizes[result - 1].count > 0) {
					prizes[result - 1].count--;
					prizeRecords.add(new PrizeRecord(currentPlayer, result));
				}
			}
		}



		if (isGameEnd()) {
			if (CURRENT_CHAMPION.prize > 0)
				prizeRecords.add(CURRENT_CHAMPION);
			return true;
		} else return false;
	}
}
