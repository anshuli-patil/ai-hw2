package edu.usc.anshulip.ai.hw2;

/**
 * Move type where at least one neighbor has to be of same piece type
 * conquers neighbors belonging to opponent
 * @author anshulip
 *
 */
public class Raid extends GameAction {

	static int N;

	// points gained by flipping opponent's location
	int increasedPoints = Integer.MIN_VALUE;

	// direction in which opponent was successfully flipped
	int[] opponentFlipped;

	public Raid(int row, int col, GameBoard board) {
		super(row, col, board);

		Raid.N = board.N;
		opponentFlipped = new int[4];
	}

	@Override
	void updateOwner() {
		PLAYER_TYPE player = board.nextPlayer;

		board.grid[row][col] = player.type;

		int enemyPlayer = (player.type * -1);

		// for each neighbor of the raid destination
		for (int i = 1; i <= 4; i++) {
			int row = getRaidDestinationRow(i, this.row);
			int col = getRaidDestinationCol(i, this.col);

			if (row != -1 && col != -1) {
				// convert the location belonging to the opposing player
				if (board.grid[row][col] == enemyPlayer) {
					board.grid[row][col] = player.type;

					// record flipped opponent to reverse
					opponentFlipped[i - 1] = 1;
				}
			}

		}

	}

	@Override
	void updateEmptyBlocks() {
		board.emptyBlocks -= 1;
	}

	@Override
	boolean checkValidity(PLAYER_TYPE playerType) {

		if (board.grid[row][col] != 0) {
			return false;
		}

		boolean foundNeighbor = false;
		// check that at least one neighbor location belongs to current player
		for (int i = 1; i <= 4; i++) {
			int row = getRaidDestinationRow(i, this.row);
			int col = getRaidDestinationCol(i, this.col);

			if (row != -1 && col != -1 && board.grid[row][col] == playerType.type) {
				foundNeighbor = true;
			}
		}

		return foundNeighbor;
	}

	private static int getRaidDestinationCol(int direction, int col) {
		int col1 = -1;

		if (direction == 1 || direction == 2) {
			col1 = col;
		} else if (direction == 3) {
			col1 = col + 1;
		} else if (direction == 4) {
			col1 = col - 1;
		}
		if (col1 < 0 || col1 >= N) {
			return -1;
		}
		return col1;
	}

	private static int getRaidDestinationRow(int direction, int row) {
		int row1 = -1;

		if (direction == 1) {
			row1 = row - 1;
		} else if (direction == 2) {
			row1 = row + 1;
		} else if (direction == 3 || direction == 4) {
			row1 = row;
		}
		if (row1 < 0 || row1 >= N) {
			return -1;
		}
		return row1;
	}

	@Override
	void reverse(PLAYER_TYPE player) {

		// un-occupy the destination location
		board.grid[row][col] = 0;

		int enemyPlayer = (player.type * -1);

		// for each neighbor of the raid destination
		for (int i = 1; i <= 4; i++) {
			int row = getRaidDestinationRow(i, this.row);
			int col = getRaidDestinationCol(i, this.col);

			if (row != -1 && col != -1) {
				// convert the location belonging to the opposing player
				if (opponentFlipped[i - 1] == 1) {
					board.grid[row][col] = enemyPlayer;
				}
			}

		}

		increasedPoints = 0;
		board.emptyBlocks += 1;

		board.nextPlayer = player;
	}

	@Override
	public String toString() {
		return String.format("%c%d Raid", col + 'A', row + 1);
	}
}
