// clear && astyle --style=java --indent=tab *.java && javac Main.java && java Main

import java.util.Random;
import java.util.Arrays;
import java.util.Optional;

public class Main {
	private static final Random PRNG = new Random();

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
		text = text.replace(""+NONE, ".");
		text = text.replace(",", "");
		text = text.replace("[", " ");
		text = text.replace("]", " ");
		text = text.replace(" ", "");
		//text = text.replace("·", " ");
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

		/*
		print( board );
		System.out.println(sum);
		System.out.println();
		/**/

		return sum;
	}

	private static int mts(int board[][], int player, int depth) {
		Optional<Integer> score = hasWinner( board );
		if(score.isEmpty() == false) {
			return score.get();
		}

		int i = -1;
		int j = -1;
		do {
			i = PRNG.nextInt(3);
			j = PRNG.nextInt(3);
		} while(board[i][j] != NONE);

		board[i][j] = player;
		int result = mts(board, (player==MAX)?MIN:MAX, depth+1);
		board[i][j] = NONE;

		return result;
	}

	public static void main(String args[]) {
		reset( board );

		//trace(board, MAX, 0);

		board[0][1] = MAX;
		board[1][0] = MIN;
		board[2][1] = MAX;
		board[1][2] = MIN;
		print(board);

		int ITERATIONS = 9_000_000;

		double probabilities[][] = {
			{0, 0, 0,},
			{0, 0, 0,},
			{0, 0, 0,},
		};

		int g;
		for(g=0; g<ITERATIONS; g++)	{
//	    long stop = System.currentTimeMillis() + 5000;
//	    for(g=0; System.currentTimeMillis()<stop; g++)	{
			int i = -1;
			int j = -1;
			do {
				i = PRNG.nextInt(3);
				j = PRNG.nextInt(3);
			} while(board[i][j] != NONE);

			board[i][j] = MAX;
//print(board);
//System.out.println();
			int result = mts(board, MIN, 1);
			if(result == MAX) {
				probabilities[i][j]++;
			}
			board[i][j] = NONE;
		}

//	    for(int i=0; i<3; i++) {
//            for(int j=0; j<3; j++) {
//                probabilities[i][j] /= g;
//            }
//	    }

		System.out.println( Arrays.deepToString(probabilities).replace("],", "],\n") );
	}
}
