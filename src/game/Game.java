package game;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Game{
	int peopleX = 0;
	int peopleY = 1;
	Block[] metrix;
	int lenX = 2;
	int lenY = 2;
	List<Move> regularMove;
	Stack<Stage> stages;
	boolean isFinished = false;
	int numOfStep = 0;
	int maxNumOfStep;

	public static void main(String[] args){
		new Game();
	}
	
	public Game(){
		stages = new Stack<Stage>();
		init();
		process();
	}
	
	public void init(){
		maxNumOfStep = 14;
		peopleX = 1;
		peopleY = 0;
		lenX = 3;
		lenY = 4;
		metrix = new Block[lenX * lenY];
		initMetir(metrix);
		
		Stage stage = new Stage();
		stages.push(stage);
		
		stage.isGoalOpen = false;
		stage.hashOrder = new int[lenX * lenY];
		setStageHashCode(stage.hashOrder, metrix);
		setStageMovement(stage);
		setupRegularMove();
	}
	
	public void setupRegularMove(){
		regularMove = new ArrayList<Move>();
		for(Direction d: Direction.values()){
			for(int i = 0; i < d.nCode; i++){
				regularMove.add(new Move(d, true, i, false));
			}
		}
	}

	public void initMetir(Block[] metrix){
		metrix[0*lenX + 0] = new Block(DoorType.closed, DoorType.closed, DoorType.lilac, DoorType.closed, true, BlockFunction.nothing, false);
		metrix[0*lenX + 1] = new Block(DoorType.closed, DoorType.lilac, DoorType.yellow, DoorType.closed, false, BlockFunction.nothing, false);
		metrix[0*lenX + 2] = new Block(DoorType.closed, DoorType.closed, DoorType.closed, DoorType.lilac, false, BlockFunction.nothing, true);

		metrix[1*lenX + 0] = new Block(DoorType.yellow, DoorType.closed, DoorType.yellow, DoorType.closed, false, BlockFunction.nothing, false);
		metrix[1*lenX + 1] = new Block(DoorType.yellow, DoorType.closed, DoorType.closed, DoorType.yellow, false, BlockFunction.nothing, false);
		metrix[1*lenX + 2] = new Block(DoorType.closed, DoorType.yellow, DoorType.closed, DoorType.yellow, false, BlockFunction.openJump, false);

		metrix[2*lenX + 0] = new Block(DoorType.yellow, DoorType.yellow, DoorType.closed, DoorType.closed, false, BlockFunction.openRedDoor, false);
		metrix[2*lenX + 1] = new Block(DoorType.closed, DoorType.closed, DoorType.yellow, DoorType.yellow, false, BlockFunction.nothing, false);
		metrix[2*lenX + 2] = new Block(DoorType.closed, DoorType.yellow, DoorType.yellow, DoorType.closed, false, BlockFunction.nothing, false);

		metrix[3*lenX + 0] = new Block(DoorType.lilac, DoorType.closed, DoorType.closed, DoorType.closed, false, BlockFunction.openGoal, false);
		metrix[3*lenX + 1] = new Block(DoorType.closed, DoorType.closed, DoorType.closed, DoorType.closed, false, BlockFunction.nothing, false);
		metrix[3*lenX + 2] = new Block(DoorType.closed, DoorType.closed, DoorType.red, DoorType.closed, true, BlockFunction.nothing, false);
	}

	public void setStageMovement(Stage stage){
		stage.peopelMove = new Stack<Move>();

		if(metrix[peopleY*lenX + peopleX].jump && stages.peek().isJumpOpen){
			stage.peopelMove.push(new Move(null, false, -1, true));
		}
		checkMove(peopleX, peopleY, peopleX, peopleY-1, Direction.up, stage, stage.peopelMove);
		checkMove(peopleX, peopleY, peopleX, peopleY+1, Direction.down, stage, stage.peopelMove);
		checkMove(peopleX, peopleY, peopleX-1, peopleY, Direction.left, stage, stage.peopelMove);
		checkMove(peopleX, peopleY, peopleX+1, peopleY, Direction.right, stage, stage.peopelMove);
	}

	public void setStageHashCode(int[] hashOrder, Block[] m){		
		for(int i = 0; i < lenX * lenY; i++){
			hashOrder[i] = m[i].hashCode == 0? m[i].getHash() : m[i].hashCode;
		}
	}
	
	public boolean canGo(DoorType door1, DoorType door2, Stage stage){	
		switch (door1){
		case closed	: return false;
		case red	:
			if(stage.isRedOpen && (door2 == DoorType.red || door2 == DoorType.yellow)){
				return true;
			}
		case yellow	:
			if(door2 == DoorType.yellow || (stage.isRedOpen && door2 == DoorType.red)){
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
	
	public void checkMove(int curX, int curY, int nextX, int nextY,  Direction dir, Stage stage, Stack<Move> movements){
		if(nextX >= 0 && nextX < lenX && nextY >= 0 && nextY < lenY){
			switch(dir){
			case up: 
				if(canGo(metrix[curY*lenX + curX].doorUp, metrix[nextY*lenX + nextX].doorDown, stage)){
					movements.push(new Move(dir, false, -1, false));
				}
				break;
			case down: 
				if(canGo(metrix[curY*lenX + curX].doorDown, metrix[nextY*lenX + nextX].doorUp, stage)){
					movements.push(new Move(dir, false, -1, false));
				}
				break;
			case left: 
				if(canGo(metrix[curY*lenX + curX].doorLeft, metrix[nextY*lenX + nextX].doorRight, stage)){
					movements.push(new Move(dir, false, -1, false));
				}
				break;
			case right: 
				if(canGo(metrix[curY*lenX + curX].doorRight, metrix[nextY*lenX + nextX].doorLeft, stage)){
					movements.push(new Move(dir, false, -1, false));
				}
				break;
			}
		}
	}
	
	public void process(){
		
		if(isFinished){
			System.out.println("Finished: " + stages);
			return;
		}
		
		if(numOfStep > maxNumOfStep){
			rollback();
			return;
		}
		
		while(! stages.peek().peopelMove.isEmpty() && !isFinished){
			Move move = stages.peek().peopelMove.pop();
			move(move);
			//System.out.println("people "  + stages.size() + stages);
		}
		
		while(stages.peek().regularMoveIndex < regularMove.size()&& !isFinished){
			stages.peek().regularMoveIndex ++;
			move(regularMove.get(stages.peek().regularMoveIndex-1));
			//System.out.println("regular "  + stages.size() + stages);
		}
		
		if(!isFinished){
			rollback();
			return;
		}
	}
	public void move(Move move){
		Block[] temp = new Block[lenX*lenY];
		for(int i=0;i < temp.length; i++){
			temp[i] = metrix[i];
		}
		Stage newS = stages.peek().cloneOne();
		if(move.isBlock){
			int index = move.blockIndex;
			int pX = peopleX, pY = peopleY;
			switch(move.dir){
			case up:		
				for(int i = 0; i < lenY; i ++){
					int t = (i + lenY + 1) % lenY;
					temp[i*lenX + index] = metrix[t*lenX + index];
				}

				if(peopleX == index){
					pY = (pY + lenY - 1)%lenY;
				}
				break;
			case down:
				for(int i = 0; i < lenY; i ++){
					int t = (i + lenY - 1) % lenY;
					temp[i*lenX + index] = metrix[t*lenX + index];
				}
				
				if(peopleX == index)
					pY = (pY + lenY + 1) % lenY;
				break;
			case left:
				for(int i = 0; i < lenX; i++){
					int t = (i + lenX + 1) % lenX;
					temp[index*lenX + i] = metrix[index*lenX + t];
				}

				if(peopleY == index)
					pX = (pX+ lenX -1) % lenX;
				break;
			case right:
				for(int i = 0; i < lenX; i++){
					int t = (i + lenX - 1) % lenX;
					temp[index*lenX + i] = metrix[index*lenX + t];
				}

				if(peopleY == index)
					pX = (pX+ lenX + 1) % lenX;
				break;
			}
			newS.hashOrder = new int[lenX * lenY];
			setStageHashCode(newS.hashOrder, temp);
			if(!checkSameStage(newS)){
				numOfStep ++; 
				newS.previousStage = stages.peek();
				newS.previousHashOrder = stages.peek().hashOrder;
				newS.previousMove = move;
				stages.push(newS);
				metrix = temp;
				peopleX = pX;
				peopleY = pY;
				setStageMovement(newS);
				process();
			}
			return; 
		}else if(move.jump){
			if(! stages.peek().previousMove.jump){
				numOfStep ++; 
				newS.hashOrder = new int[lenX * lenY];
				setStageHashCode(newS.hashOrder, metrix);
				newS.previousStage = stages.peek();
				newS.previousHashOrder = stages.peek().hashOrder;
				newS.previousMove = move;
				stages.push(newS);
				findJumpBlock();
				setStageMovement(newS);
				process();
			}
			return; 
		}else{
			int pX = peopleX, pY = peopleY;
			switch(move.dir){
			case up:
				pY -=1;
				break;
			case down:
				pY +=1;
				break;
			case left:
				pX -=1;
				break;
			case right:
				pX +=1;
				break;
			}
			Block currentPepple = metrix[pY * lenX + pX];
			currentPepple.doFunction(newS);
			if(currentPepple.isGoal && newS.isGoalOpen){
				isFinished = true;
			}
			newS.hashOrder = new int[lenX * lenY];
			setStageHashCode(newS.hashOrder, metrix);
			if(!checkSameStage(newS)){
				numOfStep ++; 
				newS.previousStage = stages.peek();
				newS.previousHashOrder = stages.peek().hashOrder;
				newS.previousMove = move;
				stages.push(newS);
				peopleX = pX;
				peopleY = pY;
				setStageMovement(newS);
				process();
			}
			return; 
		}
	}

	public void rollback(){
		Stage stage = stages.pop();
		Move move = stage.previousMove;
		if(move.isBlock){
			int index = move.blockIndex;
			Block[] temp = new Block[lenX*lenY];
			for(int i=0;i < temp.length; i++){
				temp[i] = metrix[i];
			}
			switch(move.dir){
			case up:
				for(int i = 0; i < lenY; i ++){
					int t = (i + lenY - 1) % lenY;
					temp[i*lenX + index] = metrix[t*lenX + index];
				}

				if(peopleX == index)
					peopleY = (peopleY + lenY + 1)%lenY;
				break;
			case down:
				for(int i = 0; i < lenY; i ++){
					int t = (i + lenY + 1) % lenY;
					temp[i*lenX + index] = metrix[t*lenX + index];
				}

				if(peopleX == index)
					peopleY = (peopleY + lenY - 1)%lenY;
				break;
			case left:
				for(int i = 0; i < lenX; i++){
					int t = (i + lenX - 1) % lenX;
					temp[index*lenX + i] = metrix[index*lenX + t];
				}

				if(peopleY == index)
					peopleX = (peopleX + lenX + 1)%lenX;
				break;
			case right:	
				for(int i = 0; i < lenX; i++){
					int t = (i + lenX + 1) % lenX;
					temp[index*lenX + i] = metrix[index*lenX + t];
				}

				if(peopleY == index)
					peopleX = (peopleX + lenX- 1) % lenX;
				break;
			}
			metrix = temp;
		}else if(move.jump){
			findJumpBlock();
		}else{
			switch(move.dir){
			case up:
				peopleY +=1;
				break;
			case down:
				peopleY -=1;
				break;
			case left:
				peopleX +=1;
				break;
			case right:
				peopleX -=1;
				break;
			}
		}
		numOfStep --;
	}
	
	public boolean checkSameStage(Stage stage1){
		for(Stage stage2: stages){
			boolean isThisSame = true;
			if(stage2 == null){
				continue;
			}
			if(stage1.isGoalOpen != stage2.isGoalOpen){
				isThisSame = false;
			}
			if(stage1.isJumpOpen != stage2.isJumpOpen){
				isThisSame = false;
			}
			if(stage1.isRedOpen != stage2.isRedOpen){
				isThisSame = false;
			}
			for(int i = 0; i < stage1.hashOrder.length; i++){
				if(stage1.hashOrder[i] != stage2.hashOrder[i]){
					isThisSame = false;
				}
			}
			if(isThisSame){
				return isThisSame;
			}
		}
		return false;
	}
	
	public void findJumpBlock(){
		for (int i = 0; i < metrix.length; i++) {
			if(metrix[i].jump){
				peopleY = i / lenX;
				peopleX = i - peopleY * lenX;
			}
		}
	}
}