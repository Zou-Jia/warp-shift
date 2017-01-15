package game;

import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;

class Stage{
	boolean isRedOpen = Game.isRedOpen;
	boolean isJumpOpen = Game.isJumpOpen;
	boolean isGoalOpen = Game.isGoalOpen;
	Block[] board = Game.board.clone();
	int peopleX = Game.startPosX;
	int peopleY = Game.startPosY;
	Stack<Move> peopleMove;
	Stack<Move> blockMove;
	Move previousMove = null;
	public Stage() {
		initialzePeopleMove();
	}

	Stage cloneOne(){
		Stage stage = new Stage();
		stage.isRedOpen = this.isRedOpen;
		stage.isJumpOpen = this.isJumpOpen;
		stage.isGoalOpen = this.isGoalOpen;
		stage.board = this.board.clone();
		stage.peopleX = this.peopleX;
		stage.peopleY = this.peopleY;
		return stage;
	}

	public void initialzePeopleMove(){
		peopleMove = new Stack<Move>();
		if(board[peopleY*Game.lenX + peopleX].jump && isJumpOpen){
			peopleMove.push(new Move(null, false, -1, true));
		}
		checkMove(peopleX, peopleY-1, Direction.up);
		checkMove(peopleX, peopleY+1, Direction.down);
		checkMove(peopleX-1, peopleY, Direction.left);
		checkMove(peopleX+1, peopleY, Direction.right);
		blockMove = new Stack<Move>();
	}
	public boolean canGo(DoorType door1, DoorType door2){	
		switch (door1){
		case closed	: return false;
		case red	:
			if(isRedOpen && (door2 == DoorType.red || door2 == DoorType.yellow)){
				return true;
			}
		case yellow	:
			if(door2 == DoorType.yellow || (isRedOpen && door2 == DoorType.red)){
				return true;
			}
			break;
		case lilac		:
			if(door2 == DoorType.lilac){
				return true;
			}
			break;
		}
		return false;
	}
	public void checkMove(int nextX, int nextY,  Direction dir){
		int curY = peopleY;
		int curX = peopleX;
		if(nextX >= 0 && nextX < Game.lenX && nextY >= 0 && nextY < Game.lenY){
			switch(dir){
			case up: 
				if(canGo(board[curY*Game.lenX + curX].doorUp, board[nextY*Game.lenX + nextX].doorDown)){
					peopleMove.push(new Move(dir, false, -1, false));
				}
				break;
			case down: 
				if(canGo(board[curY*Game.lenX + curX].doorDown, board[nextY*Game.lenX + nextX].doorUp)){
					peopleMove.push(new Move(dir, false, -1, false));
				}
				break;
			case left: 
				if(canGo(board[curY*Game.lenX + curX].doorLeft, board[nextY*Game.lenX + nextX].doorRight)){
					peopleMove.push(new Move(dir, false, -1, false));
				}
				break;
			case right: 
				if(canGo(board[curY*Game.lenX + curX].doorRight, board[nextY*Game.lenX + nextX].doorLeft)){
					peopleMove.push(new Move(dir, false, -1, false));
				}
				break;
			}
		}
	}
	public String toString(){
		if(previousMove != null){
			return previousMove.toString();
		}else{
			return "null";
		}
	}
	//			return printBoard();
	public boolean equals(Object another) {
		if (this == another)
			return true;
		if (another instanceof Stage) {
			Stage stage = (Stage) another;
			if (isRedOpen != stage.isRedOpen)
				return false;
			if (isJumpOpen != stage.isJumpOpen)
				return false;
			if (isGoalOpen != stage.isGoalOpen)
				return false;
			if(peopleX != stage.peopleX)
				return false;
			if (peopleY != stage.peopleY)
				return false;
			return Arrays.equals(board, stage.board);
		}
		return false;
	}

	public String printBoard(){
		String result = "\n";
		for (int i = 0 ; i< Game.lenX; i++) {
			String top="";
			String middle="";
			String bottom="";
			for (int j =0;j<Game.lenY;j++){
				top += "O"+ board[i*Game.lenX +j].doorUp.getText()+"O" ;
				middle += board[i*Game.lenX +j].doorLeft.getText();
				if ( peopleX == j && peopleY == i ) {
					middle += "*";
				} else {
					middle+=" ";
				}
				middle += board[i*Game.lenX +j].doorRight.getText();
				bottom += "O"+ board[i*Game.lenX +j].doorDown.getText()+"O" ;
			}
			result += top +"\n" + middle+"\n"+bottom+"\n"; 
		}
		return result;
	}
//	@Override
//	public int hashCode() {
//		return Objects.hash(isRedOpen, isJumpOpen,isGoalOpen, board,peopleX,peopleY);
//	}

	public int minStepsRequired(){
		int steps = 0;
		if (isRedOpen!= Game.isRedOpen){
		Position red = getPositionFor(BlockFunction.openRedDoor);
			steps+= getSteps(red.x,red.y);
		}
		if (isGoalOpen!= Game.isGoalOpen){
			Position goal = getPositionFor(BlockFunction.openGoal);
			steps+= getSteps(goal.x,goal.y);
		}
		if (isJumpOpen!= Game.isJumpOpen){
			Position jump = getPositionFor(BlockFunction.openJump);
			steps+= getSteps(jump.x,jump.y);
		}
		return steps;
	}


	private int getSteps(int endx, int endy) {
		int steps = 0;
		if (peopleX != endx )
			steps++;
		if (peopleY != endy )
			steps++;
		return steps;
	}

	private Position getPositionFor(BlockFunction func) {
		if(func != BlockFunction.nothing)
			for  (int y = 0; y< Game.lenY;y++) 
				for (int x = 0; x< Game.lenX;x++ ){
					if (board[y* Game.lenX + x].function == func) {
						return new Position(x,y);
					}
				}
		return null;
	}
	class Position {
		int x;
		int y;
		public Position(int x, int y){
			this.x=x;
			this.y=y;
		}
	}
}

