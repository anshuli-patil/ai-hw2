package edu.usc.anshulip.ai.hw2;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

	// size of NxN Grid 
	int N;

	int[][] grid;
	int[][] gridValues;

	int emptyBlocks = 0;

	PLAYER_TYPE nextPlayer = PLAYER_TYPE.MAX;

	GameBoard(int N, int[][] gridValues, int[][] grid) {
		this.N = N;
		this.gridValues = gridValues;
		this.grid = grid;
		
		for (int i = 0; i < N; i++) {
			for(int j = 0; j< N; j++) {
				if(grid[i][j] == 0) {
					emptyBlocks += 1;
				}
			}
		}
	}

	public void performAction(PLAYER_TYPE player, GameAction action) {
		action.execute(player);
	}

	public int utilityValue() {
		int playerVal = 0;
		int oppVal = 0;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (grid[i][j] == 1) {
					playerVal += gridValues[i][j];
				} else if (grid[i][j] == -1) {
					oppVal += gridValues[i][j];
				}
			}
		}
		return playerVal - oppVal;
	}

	public boolean terminalTest() {
		return emptyBlocks == 0;
	}

	List<GameAction> getNextActions(PLAYER_TYPE player) {

		// adding all actions from all positions
		List<GameAction> nextMoves = new ArrayList<GameAction>();

		for (ACTION_TYPE action : ACTION_TYPE.values()) {
			nextMoves.addAll(nextActionByType(player, action));
		}
		return nextMoves;
	}

	private List<GameAction> nextActionByType(PLAYER_TYPE player, ACTION_TYPE action) {
		List<GameAction> nextMoves = new ArrayList<GameAction>();

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {

				if (grid[i][j] == 0) {
					GameAction newAction = GameAction.getGameAction(i, j, this, action);

					if (newAction.checkValidity(player)) {
						nextMoves.add(newAction);
					}
				}
			}
		}

		return nextMoves;
	}

	public String toString(String playerSymbol) {
		StringBuilder board = new StringBuilder();
		String enemySymbol;

		if (playerSymbol.equals("X")) {
			enemySymbol = "O";
		} else {
			enemySymbol = "X";
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				int gridVal = grid[i][j];
				if (gridVal == 0) {
					board.append(".");
				} else if (gridVal == 1) {
					board.append(playerSymbol);
				} else if (gridVal == -1) {
					board.append(enemySymbol);
				}
			}

			
			board.append("\n");
		}
		return board.toString();
	}

}
