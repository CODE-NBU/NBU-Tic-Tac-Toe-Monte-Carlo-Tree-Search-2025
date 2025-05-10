// clear && astyle --style=java --indent=tab *.java && javac Main.java && java Main

import java.util.Arrays;
import java.util.Optional;

public class Main {
	private static enum Tile {
		E( 0),
		O(-1),
		X(+1);

		private int value;

		private Tile(int value) {
			this.value = value;
		}

		public int value() {
			return value;
		}
	}

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

	private static void print(int board[][]) {
		String text = Arrays.deepToString( board );
		text = text.replace("],", "],\n");
		text = text.replace(""+MIN, "O");
		text = text.replace(""+MAX, "X");
		text = text.replace(""+NONE, "-");
		text = text.replace(",", "");
		text = text.replace("[", " ");
		text = text.replace("]", " ");
		text = text.replace(" ", "");
		//text = text.replace("-", " ");
		System.out.println( text );
	}

	private static int trace(int board[][], int player, int depth) {
		Optional<Integer> score = hasWinner( board );
		if(score.isEmpty() == false) {
			return score.get();
		}

		int sum = 0;
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[i].length; j++) {
				if(board[i][j] != NONE) {
					continue;
				}

				board[i][j] = player;
				int next = NONE;
				if(player == MAX) {
					next = MIN;
				}
				if(player == MIN) {
					next = MAX;
				}
				sum += trace(board, next, depth+1);
				board[i][j] = NONE;
			}
		}

		/**/
		if(depth == 1) {
			print( board );
			System.out.println(sum);
			System.out.println();
		}
		/**/

		return sum;
	}

	public static void main(String args[]) {
		reset( board );

		trace(board, MAX, 0);
	}
}
