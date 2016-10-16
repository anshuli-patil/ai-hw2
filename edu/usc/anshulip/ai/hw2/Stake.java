package edu.usc.anshulip.ai.hw2;

/**
 * Move type where isolated board block can be conquered
 * @author anshulip
 *
 */
public class Stake extends GameAction {

	public Stake(int row, int col, GameBoard board) {
		super(row, col, board);
	}

	@Override
	void updateOwner() {
		PLAYER_TYPE player = board.nextPlayer;
		board.grid[row][col] = player.type;
	}

	@Override
	void updateEmptyBlocks() {
		board.emptyBlocks -= 1;
	}

	@Override
	boolean checkValidity(PLAYER_TYPE player) {
		return board.grid[row][col] == 0;
	}

	@Override
	void reverse(PLAYER_TYPE player) {
		board.grid[row][col] = 0;
		board.emptyBlocks += 1;
	}

	@Override
	public String toString() {
		return String.format("%c%d Stake", col + 'A', row + 1);

	}

}
