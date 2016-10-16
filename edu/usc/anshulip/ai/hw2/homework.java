package edu.usc.anshulip.ai.hw2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * creates a problem instance by reading input.txt, invokes solution and prints to output.txt
 * breaking class naming convention for auto-grading script
 * @author anshulip
 *
 */
public class homework {

	private int N;
	private int[][] gridValues;
	private String problemMode;
	private String playerSymbol;
	private int depth;
	private int[][] grid;

	public static void main(String[] args) {
		homework problem = new homework();
		problem.readProblemInstance();

		GameBoard board = new GameBoard(problem.N, problem.gridValues, problem.grid);

		GamePlayer player = new GamePlayer();

		boolean mode = false;
		if (problem.problemMode.equals("COMPETITION") || problem.problemMode.equals("ALPHABETA")) {
			mode = true;
		}

		PlayerMove finalMove = player.maxValue(board, Integer.MIN_VALUE, Integer.MAX_VALUE, problem.depth, mode);
		//player.finalAction.execute(PLAYER_TYPE.MAX);

		
		problem.printSolutionToFile(finalMove.action, board);
	}

	public void readProblemInstance() {
		BufferedReader bufferedFileReader = null;
		try {

			bufferedFileReader = new BufferedReader(new FileReader("input.txt"));

			// initialize the problem from input file
			this.N = Integer.parseInt(read(bufferedFileReader));
			this.problemMode = read(bufferedFileReader);
			this.playerSymbol = read(bufferedFileReader);
			this.depth = Integer.parseInt(read(bufferedFileReader));
			this.gridValues = new int[N][N];
			this.grid = new int[N][N];

			for (int i = 0; i < N; i++) {
				String gridLine = read(bufferedFileReader);
				String[] gridLineValues = gridLine.split(" ");
				for (int j = 0; j < N; j++) {
					gridValues[i][j] = Integer.parseInt(gridLineValues[j]);
				}
			}

			for (int i = 0; i < N; i++) {
				String gridLine = read(bufferedFileReader);
				for (int j = 0; j < N; j++) {
					String oneGridChar = gridLine.substring(j, j + 1);
					if (oneGridChar.equals(".")) {
						grid[i][j] = 0;
					} else if (oneGridChar.equals(playerSymbol)) {
						grid[i][j] = 1;
					} else {
						grid[i][j] = -1;
					}
				}
			}

		} catch (IOException e) {
			// do nothing
		} finally {
			close(bufferedFileReader);
		}

	}

	private void close(Closeable ioClass) {
		if (ioClass != null) {
			try {
				ioClass.close();
			} catch (IOException e) {
				// do nothing
			}
		}

	}

	public String read(BufferedReader bufferedFileReader) {
		try {
			return bufferedFileReader.readLine().trim();
		} catch (IOException e) {
			// do nothing
		}
		return null;
	}

	public void printSolutionToFile(GameAction action, GameBoard board) {
		BufferedWriter bufferedFileWriter = null;
		try {
			bufferedFileWriter = new BufferedWriter(new FileWriter("output.txt"));
			// write solution to file

			String actionString = action.toString();
			bufferedFileWriter.write(actionString);

			bufferedFileWriter.write("\n");
			
			String boardString = board.toString(this.playerSymbol);
			bufferedFileWriter.write(boardString);

		} catch (IOException e) {
			// do nothing
		} finally {
			close(bufferedFileWriter);
		}

	}
}
