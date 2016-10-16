package edu.usc.anshulip.ai.hw2;

import java.util.List;

/*
 * returns best action for each player method
 */
public class GamePlayer {

	/*
	 * returns a utility value for the MAX player
	 */
	public PlayerMove maxValue(GameBoard board, int alpha, int beta, int levelRemaining, boolean isAlphaBetaPruned) {
		if (board.terminalTest() || levelRemaining <= 0) {
			return new PlayerMove(board.utilityValue(), null);
		}

		// int v = Integer.MIN_VALUE;
		PlayerMove v = new PlayerMove(PLAYER_TYPE.MAX);

		List<GameAction> actions = board.getNextActions(PLAYER_TYPE.MAX);

		for (GameAction a : actions) {
			board.performAction(PLAYER_TYPE.MAX, a);

			int minVal = minValue(board, alpha, beta, levelRemaining - 1, isAlphaBetaPruned);

			a.reverse(PLAYER_TYPE.MAX);

			if (minVal > v.moveValue) {
				// v = minVal;
				v = new PlayerMove(minVal, a);
			}
			if (isAlphaBetaPruned) {
				if (v.moveValue >= beta) {
					return v;
				}

				if (v.moveValue > alpha) {
					alpha = v.moveValue;
				}
			}
		}

		return v;
	}

	/*
	 * returns a utility value for the MIN player
	 */
	private int minValue(GameBoard board, int alpha, int beta, int levelRemaining, boolean isAlphaBetaPruned) {
		if (board.terminalTest() || levelRemaining < 0) {
			return board.utilityValue();
		}

		int v = Integer.MAX_VALUE;

		List<GameAction> actions = board.getNextActions(PLAYER_TYPE.MIN);

		for (GameAction a : actions) {
			board.performAction(PLAYER_TYPE.MIN, a);

			int maxVal = maxValue(board, alpha, beta, levelRemaining - 1, isAlphaBetaPruned).moveValue;

			a.reverse(PLAYER_TYPE.MIN);

			if (maxVal < v) {
				v = maxVal;
			}
			if (isAlphaBetaPruned) {
				if (v <= alpha) {
					return v;
				}

				if (v < beta) {
					beta = v;
				}
			}

		}

		return v;
	}
}
