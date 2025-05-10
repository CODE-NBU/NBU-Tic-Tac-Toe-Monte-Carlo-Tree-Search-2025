// clear && astyle --style=java --indent=tab *.java && javac Main.java && java Main

import java.util.Arrays;
import java.util.Optional;

public class Main {
	private static enum Tile {
		E,
		O,
		X,
	};

	private static final int NONE = 0;
	private static final int MIN = -1;
	private static final int MAX = +1;

	private static int board[][] = {
		{0, 0, 0,},
		{0, 0, 0,},
		{0, 0, 0,},
	};

	private static void reset(int board[][]) {
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[i].length; j++) {
				board[i][j] = 0;
			}
		}
	}

	private static Optional<Integer> hasWinner(int board[][]) {
		for(int l=0; l<3; l++) {
			for(int k=0, count=0; k<3; k++) {
				if(board[l][k] == MIN) {
					count++;
				}

				if(count == 3) {
					return Optional.of( MIN );
				}
			}

			for(int k=0, count=0; k<3; k++) {
				if(board[k][l] == MIN) {
					count++;
				}

				if(count == 3) {
					return Optional.of( MIN );
				}
			}
		}

		for(int k=0, count=0; k<3; k++) {
			if(board[k][k] == MIN) {
				count++;
			}

			if(count == 3) {
				return Optional.of( MIN );
			}
		}

		for(int k=0, count=0; k<3; k++) {
			if(board[k][2-k] == MIN) {
				count++;
			}

			if(count == 3) {
				return Optional.of( MIN );
			}
		}

		for(int l=0; l<3; l++) {
			for(int k=0, count=0; k<3; k++) {
				if(board[l][k] == MAX) {
					count++;
				}

				if(count == 3) {
					return Optional.of( MAX );
				}
			}

			for(int k=0, count=0; k<3; k++) {
				if(board[k][l] == MAX) {
					count++;
				}

				if(count == 3) {
					return Optional.of( MAX );
				}
			}
		}

		for(int k=0, count=0; k<3; k++) {
			if(board[k][k] == MAX) {
				count++;
			}

			if(count == 3) {
				return Optional.of( MAX );
			}
		}

		for(int k=0, count=0; k<3; k++) {
			if(board[k][2-k] == MAX) {
				count++;
			}

			if(count == 3) {
				return Optional.of( MAX );
			}
		}

		for(int i=0, count=0; i<board.length; i++) {
			for(int j=0; j<board[i].length; j++) {
				if(board[i][j] == MIN) {
					count++;
				}
				if(board[i][j] == MAX) {
					count++;
				}

				if(count == 9) {
					return Optional.of( NONE );
				}
			}
		}

		return Optional.empty();
	}

	public static void main(String args[]) {
		reset( board );

		board = new int[][] {
			{-1, +1, -1,},
			{+1, +1, -1,},
			{-1, -1, +1,},
		};

		System.out.println(
		    Arrays.deepToString(board).replace("],","],\n") );
		System.out.println( hasWinner(board) );
	}
}
