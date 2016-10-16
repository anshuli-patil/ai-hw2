package edu.usc.anshulip.ai.hw2;

public abstract class GameAction {
	public int row;
	public int col;
	public GameBoard board;

	public GameAction(int row, int col, GameBoard board) {
		this.row = row;
		this.col = col;
		this.board = board;
	}

	abstract void updateOwner();

	abstract void updateEmptyBlocks();

	abstract boolean checkValidity(PLAYER_TYPE player);

	abstract void reverse(PLAYER_TYPE player);

	public void execute(PLAYER_TYPE player) {
		board.nextPlayer = player;

		updateOwner();
		updateEmptyBlocks();

	}

	public static GameAction getGameAction(int row, int col, GameBoard board, ACTION_TYPE actionType) {
		GameAction action = null;
		if (actionType == ACTION_TYPE.STAKE) {
			action = new Stake(row, col, board);
		} else {
			action = new Raid(row, col, board);
		}
		return action;
	}
}
