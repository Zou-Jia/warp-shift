package game;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameTest {

	@Test
	public void test3_4() {
		Game.isRedOpen = false;
		Game.isJumpOpen = false;
		Game.isGoalOpen = false;
		Game.lenX = 3;
		Game.lenY = 3;
		Game.maxNumOfStep = 7;
		Game.startPosX = 0;
		Game.startPosY = 2;
		Game.board = new Block[Game.lenX * Game.lenY];
		Game.board[0 * Game.lenX + 0] = new Block(DoorType.closed, DoorType.yellow, DoorType.closed, DoorType.yellow, false,
				BlockFunction.nothing, false);
		Game.board[0 * Game.lenX + 1] = new Block(DoorType.closed, DoorType.closed, DoorType.closed, DoorType.closed, false,
				BlockFunction.nothing, false);
		Game.board[0 * Game.lenX + 2] = new Block(DoorType.closed, DoorType.yellow, DoorType.closed, DoorType.lilac, false,
				BlockFunction.openRedDoor, false);

		Game.board[1 * Game.lenX + 0] = new Block(DoorType.red, DoorType.closed, DoorType.closed, DoorType.closed, false,
				BlockFunction.nothing, true);
		Game.board[1 * Game.lenX + 1] = new Block(DoorType.closed, DoorType.closed, DoorType.yellow, DoorType.yellow, false,
				BlockFunction.openGoal, false);
		Game.board[1 * Game.lenX + 2] = new Block(DoorType.yellow, DoorType.yellow, DoorType.closed, DoorType.closed, false,
				BlockFunction.nothing, false);
		
		Game.board[2 * Game.lenX + 0] = new Block(DoorType.closed, DoorType.closed, DoorType.closed, DoorType.yellow, false,
				BlockFunction.nothing, false);
		Game.board[2 * Game.lenX + 1] = new Block(DoorType.closed, DoorType.closed, DoorType.yellow, DoorType.lilac, false,
				BlockFunction.nothing, false);
		Game.board[2 * Game.lenX + 2] = new Block(DoorType.yellow, DoorType.closed, DoorType.yellow, DoorType.closed, false,
				BlockFunction.nothing, false);
		Game.setupRegularMove();
		Player p = new Player();
		long currentTime = System.currentTimeMillis();
		assertTrue(p.solveGame());
		long finishedTime = System.currentTimeMillis();
		System.out.println((finishedTime - currentTime)/1000.0 + "s");
	}

	
	@Test
	public void test1_2() {
		Game.isRedOpen = false;
		Game.isJumpOpen = false;
		Game.isGoalOpen = true;
		Game.lenX = 2;
		Game.lenY = 2;
		Game.maxNumOfStep = 3;
		Game.startPosX = 0;
		Game.startPosY = 1;
		Game.board = new Block[Game.lenX * Game.lenY];
		Game.board[0 * Game.lenX + 0] = new Block(DoorType.closed, DoorType.closed, DoorType.closed, DoorType.closed, false,
				BlockFunction.nothing, false);
		Game.board[0 * Game.lenX + 1] = new Block(DoorType.closed, DoorType.closed, DoorType.closed, DoorType.yellow, false,
				BlockFunction.nothing, true);

		Game.board[1 * Game.lenX + 0] = new Block(DoorType.closed, DoorType.closed, DoorType.yellow, DoorType.closed, false,
				BlockFunction.nothing, false);
		Game.board[1 * Game.lenX + 1] = new Block(DoorType.closed, DoorType.closed, DoorType.closed, DoorType.closed, false,
				BlockFunction.nothing, false);
	
		Game.setupRegularMove();
		Player p = new Player();
		long currentTime = System.currentTimeMillis();
		assertTrue(p.solveGame());
		long finishedTime = System.currentTimeMillis();
		System.out.println((finishedTime - currentTime)/1000.0 + "s");
	}
	@Test
	public void test4_2() {
		Game.isRedOpen = false;
		Game.isJumpOpen = true;
		Game.isGoalOpen = false;
		Game.lenX = 3;
		Game.lenY = 3;
		Game.maxNumOfStep = 9;
		Game.startPosX = 0;
		Game.startPosY = 0;
		Game.board = new Block[Game.lenX * Game.lenY];
		Game.board[0 * Game.lenX + 0] = new Block(DoorType.yellow, DoorType.closed, DoorType.closed, DoorType.yellow, false,
				BlockFunction.nothing, false);
		Game.board[0 * Game.lenX + 1] = new Block(DoorType.closed, DoorType.yellow, DoorType.yellow, DoorType.closed, false,
				BlockFunction.nothing, false);
		Game.board[0 * Game.lenX + 2] = new Block(DoorType.closed, DoorType.closed, DoorType.closed, DoorType.closed, false,
				BlockFunction.nothing, false);

		Game.board[1 * Game.lenX + 0] = new Block(DoorType.closed, DoorType.closed, DoorType.yellow, DoorType.closed, true,
				BlockFunction.nothing, false);
		Game.board[1 * Game.lenX + 1] = new Block(DoorType.yellow, DoorType.closed, DoorType.closed, DoorType.yellow, false,
				BlockFunction.nothing, false);
		Game.board[1 * Game.lenX + 2] = new Block(DoorType.closed, DoorType.lilac, DoorType.yellow, DoorType.closed, false,
				BlockFunction.openGoal, false);
		
		Game.board[2 * Game.lenX + 0] = new Block(DoorType.lilac, DoorType.yellow, DoorType.closed, DoorType.closed, false,
				BlockFunction.nothing, false);
		Game.board[2 * Game.lenX + 1] = new Block(DoorType.closed, DoorType.closed, DoorType.closed, DoorType.lilac, true,
				BlockFunction.nothing, false);
		Game.board[2 * Game.lenX + 2] = new Block(DoorType.closed, DoorType.closed, DoorType.lilac, DoorType.closed, false,
				BlockFunction.nothing, true);
		Game.setupRegularMove();
		Player p = new Player();
		long currentTime = System.currentTimeMillis();
		assertTrue(p.solveGame());
		long finishedTime = System.currentTimeMillis();
		System.out.println((finishedTime - currentTime)/1000.0 + "s");
	}
}
