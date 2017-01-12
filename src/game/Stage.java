package game;

import java.util.Stack;

class Stage{
	boolean isRedOpen = false;
	boolean isJumpOpen = false;
	boolean isGoalOpen = false;
	
	Stage previousStage = null;
	Move previousMove = null;
	int[] previousHashOrder = null;
	
	int[] hashOrder;
	int regularMoveIndex = 0;
	Stack<Move> peopelMove;
	
	Stage cloneOne(){
		Stage stage = new Stage();
		stage.isRedOpen = this.isRedOpen;
		stage.isJumpOpen = this.isJumpOpen;
		stage.isGoalOpen = this.isGoalOpen;
		return stage;
	}
	
	public String toString(){
		if(previousMove != null){
			return previousMove.toString();
		}else{
			return "null";
		}
	}
}
