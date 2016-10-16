package edu.usc.anshulip.ai.hw2;

/**
 * To identify (action, move-value) solution pair
 * @author anshulip
 *
 */
public class PlayerMove {
	GameAction action;
	int moveValue;

	PlayerMove(int moveValue, GameAction action) {
		this.action = action;
		this.moveValue = moveValue;
	}

	PlayerMove(PLAYER_TYPE player) {
		this.action = null;
		if (player == PLAYER_TYPE.MIN) {
			this.moveValue = Integer.MAX_VALUE;
		} else {
			this.moveValue = Integer.MIN_VALUE;
		}
	}
}
