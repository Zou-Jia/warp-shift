package game;

import java.util.ArrayList;
import java.util.List;

public class Game {
	static Block[] board;
	static List<Move> regularMove;
	static boolean isRedOpen = false;
	static boolean isJumpOpen = false;
	static boolean isGoalOpen = false;
	static int lenX = 3;
	static int lenY = 3;
	static int maxNumOfStep = 7;
	static int startPosX = 0;
	static int startPosY = 2;

	public static void initialize() {
		board = new Block[lenX * lenY];
		// board[0*lenX + 0] = new Block(DoorType.closed, DoorType.closed,
		// DoorType.lilac, DoorType.closed, true, BlockFunction.nothing, false);
		// board[0*lenX + 1] = new Block(DoorType.closed, DoorType.lilac,
		// DoorType.yellow, DoorType.closed, false, BlockFunction.nothing,
		// false);
		// board[0*lenX + 2] = new Block(DoorType.closed, DoorType.closed,
		// DoorType.closed, DoorType.lilac, false, BlockFunction.nothing, true);
		//
		// board[1*lenX + 0] = new Block(DoorType.yellow, DoorType.closed,
		// DoorType.yellow, DoorType.closed, false, BlockFunction.nothing,
		// false);
		// board[1*lenX + 1] = new Block(DoorType.yellow, DoorType.closed,
		// DoorType.closed, DoorType.yellow, false, BlockFunction.nothing,
		// false);
		// board[1*lenX + 2] = new Block(DoorType.closed, DoorType.yellow,
		// DoorType.closed, DoorType.yellow, false, BlockFunction.openJump,
		// false);
		//
		// board[2*lenX + 0] = new Block(DoorType.yellow, DoorType.yellow,
		// DoorType.closed, DoorType.closed, false, BlockFunction.openRedDoor,
		// false);
		// board[2*lenX + 1] = new Block(DoorType.closed, DoorType.closed,
		// DoorType.yellow, DoorType.yellow, false, BlockFunction.nothing,
		// false);
		// board[2*lenX + 2] = new Block(DoorType.closed, DoorType.yellow,
		// DoorType.yellow, DoorType.closed, false, BlockFunction.nothing,
		// false);
		//
		// board[3*lenX + 0] = new Block(DoorType.lilac, DoorType.closed,
		// DoorType.closed, DoorType.closed, false, BlockFunction.openGoal,
		// false);
		// board[3*lenX + 1] = new Block(DoorType.closed, DoorType.closed,
		// DoorType.closed, DoorType.closed, false, BlockFunction.nothing,
		// false);
		// board[3*lenX + 2] = new Block(DoorType.closed, DoorType.closed,
		// DoorType.red, DoorType.closed, true, BlockFunction.nothing, false);

		board[0 * lenX + 0] = new Block(DoorType.closed, DoorType.yellow, DoorType.closed, DoorType.yellow, false,
				BlockFunction.nothing, false);
		board[0 * lenX + 1] = new Block(DoorType.closed, DoorType.closed, DoorType.closed, DoorType.closed, false,
				BlockFunction.nothing, false);
		board[0 * lenX + 2] = new Block(DoorType.closed, DoorType.yellow, DoorType.closed, DoorType.lilac, false,
				BlockFunction.openRedDoor, false);

		board[1 * lenX + 0] = new Block(DoorType.red, DoorType.closed, DoorType.closed, DoorType.closed, false,
				BlockFunction.nothing, true);
		board[1 * lenX + 1] = new Block(DoorType.closed, DoorType.closed, DoorType.yellow, DoorType.yellow, false,
				BlockFunction.openGoal, false);
		board[1 * lenX + 2] = new Block(DoorType.yellow, DoorType.yellow, DoorType.closed, DoorType.closed, false,
				BlockFunction.nothing, false);
		
		board[2 * lenX + 0] = new Block(DoorType.closed, DoorType.closed, DoorType.closed, DoorType.yellow, false,
				BlockFunction.nothing, false);
		board[2 * lenX + 1] = new Block(DoorType.closed, DoorType.closed, DoorType.yellow, DoorType.lilac, false,
				BlockFunction.nothing, false);
		board[2 * lenX + 2] = new Block(DoorType.yellow, DoorType.closed, DoorType.yellow, DoorType.closed, false,
				BlockFunction.nothing, false);

		setupRegularMove();
	}

	public static void setupRegularMove() {
		regularMove = new ArrayList<Move>();
		for (Direction d : Direction.values()) {
			for (int i = 0; i < d.nCode; i++) {
				regularMove.add(new Move(d, true, i, false));
			}
		}
	}
}