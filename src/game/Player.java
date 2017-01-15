package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Player {
	public static void main(String[] args) {
		Game.initialize();
		Player p = new Player();
		p.solveGame();
	}

	int numOfStep = 0;
	boolean isFinished = false;
	Stack<Stage> stages;
	LinkedList<Stage> queue = new LinkedList<Stage>();

	private List<Stage> visitedStage = new ArrayList<>();

	public boolean solveGame() {
		stages = new Stack<Stage>();
		Stage firstStage = new Stage();
		stages.push(firstStage);
		queue.offer(firstStage);
		process();
		System.out.println(stages);
		return isFinished;
	}

//	public void process() {
//		while (!queue.isEmpty()) {
//			if (isFinished) {
//				return;
//			}
//			handleNextStage();
//		}
//	}

//	public void handleNextStage() {
//		Stage stage = queue.poll();
//		visitedStage.add(stage);
//		while (stage.peopleMove.isEmpty() && !isFinished) {
//			Move move = stage.peopleMove.pop();
//			move(move);
//		}
//		int regularMoveIndex = 0;
//		while (regularMoveIndex < Game.regularMove.size() && !isFinished) {
//			move(Game.regularMove.get(regularMoveIndex));
//			regularMoveIndex++;
//		}
//	}

	 public void process() {
	 if (isFinished) {
	 System.out.println("Finished: " + stages);
	 return;
	 }
	
	 if (numOfStep >= Game.maxNumOfStep) {
	 stages.pop();
	 numOfStep--;
	 return;
	 }
	
	 while (!stages.peek().peopleMove.isEmpty() && !isFinished) {
	 Move move = stages.peek().peopleMove.pop();
	 move(move);
	 }
	 int regularMoveIndex = 0;
	 while (regularMoveIndex < Game.regularMove.size() && !isFinished) {
	 move(Game.regularMove.get(regularMoveIndex));
	 regularMoveIndex++;
	 }
	
	 if (!isFinished) {
	 stages.pop();
	 numOfStep--;
	 return;
	 }
	 }

	public void blockMove(Move move) {
		Stage newS = stages.peek().cloneOne();
		Block[] temp = newS.board.clone();

		int index = move.blockIndex;
		int pX = newS.peopleX, pY = newS.peopleY;
		int lenY = Game.lenY;
		int lenX = Game.lenX;
		switch (move.dir) {
		case up:
			for (int i = 0; i < lenY; i++) {
				int t = (i + lenY + 1) % lenY;
				temp[i * lenX + index] = newS.board[t * lenX + index];
			}

			if (pX == index) {
				pY = (pY + lenY - 1) % lenY;
			}
			break;
		case down:
			for (int i = 0; i < lenY; i++) {
				int t = (i + lenY - 1) % lenY;
				temp[i * lenX + index] = newS.board[t * lenX + index];
			}

			if (pX == index)
				pY = (pY + lenY + 1) % lenY;
			break;
		case left:
			for (int i = 0; i < lenX; i++) {
				int t = (i + lenX + 1) % lenX;
				temp[index * lenX + i] = newS.board[index * lenX + t];
			}

			if (pY == index)
				pX = (pX + lenX - 1) % lenX;
			break;
		case right:
			for (int i = 0; i < lenX; i++) {
				int t = (i + lenX - 1) % lenX;
				temp[index * lenX + i] = newS.board[index * lenX + t];
			}

			if (pY == index)
				pX = (pX + lenX + 1) % lenX;
			break;
		}
		newS.board = temp;
		if (!checkSameStage(newS)) {
			numOfStep++;
			newS.peopleX = pX;
			newS.peopleY = pY;
			newS.initialzePeopleMove();
			stages.push(newS);
			process();
		}

		return;
	}

	public void move(Move move) {
		stages.peek().previousMove = move;
		if (move.isBlock) {
			blockMove(move);
		} else if (move.jump) {
			jumpMove();
		} else {
			regularMove(move.dir);
		}
	}

	public void regularMove(Direction dir) {
		Stage newS = stages.peek().cloneOne();

		int pX = newS.peopleX, pY = newS.peopleY;
		switch (dir) {
		case up:
			pY -= 1;
			break;
		case down:
			pY += 1;
			break;
		case left:
			pX -= 1;
			break;
		case right:
			pX += 1;
			break;
		}
		Block currentPepple = newS.board[pY * Game.lenX + pX];
		currentPepple.doFunction(newS);
		if (currentPepple.isGoal && newS.isGoalOpen) {
			isFinished = true;
			return;
		}
		newS.peopleX = pX;
		newS.peopleY = pY;
		if (!checkSameStage(newS)) {
			 numOfStep++;
			newS.initialzePeopleMove();
			 stages.push(newS);
			process();
		}
	}

	public void jumpMove() {
		Stage newS = stages.peek().cloneOne();
		numOfStep++;
		for (int i = 0; i < newS.board.length; i++) {
			if (newS.board[i].jump) {
				if (newS.peopleY != i / Game.lenX || newS.peopleX != i - newS.peopleY * Game.lenX) {
					newS.peopleY = i / Game.lenX;
					newS.peopleX = i - newS.peopleY * Game.lenX;
					break;
				}
			}
		}
		newS.initialzePeopleMove();
		stages.push(newS);
		process();
	}

	public boolean checkSameStage(Stage stage) {
		for (Stage stage2 : stages) {
			if (stage2.equals(stage)) {
				return true;
			}
		}
		return false;
	}
}