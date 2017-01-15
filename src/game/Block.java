package game;

enum DoorType {
	closed(0), red(11), yellow(13), lilac(17);
	int nCode;
	DoorType(int _nCode) {
		this.nCode = _nCode;
	}
	int getInt(){
		return nCode;
	}
	public String getText() {
		switch (this) {
		case closed:
			return "O";
		case lilac:
			return "L";
		case red:
			return "R";
		case yellow:
			return "Y";
		default:
			return " ";			
		}
   }
}

enum BlockFunction {
	nothing, openRedDoor, openJump, openGoal
}
class Block{
	public DoorType doorUp;
	public DoorType doorDown;
	public DoorType doorLeft;
	public DoorType doorRight;
	public boolean jump;
	public BlockFunction function;
	public boolean isGoal;
	public int hashCode;
	public Block(DoorType doorUp, DoorType doorDown, DoorType doorLeft, DoorType doorRight, boolean jump, BlockFunction function, boolean isGoal){
		this.doorUp = doorUp;
		this.doorDown = doorDown;
		this.doorLeft = doorLeft;
		this.doorRight = doorRight;
		this.jump = jump;
		this.function = function;
		this.isGoal = isGoal;
		this.hashCode = getHash();
	}
	public int getHash(){
		int a = 1*doorUp.nCode + 5*doorDown.nCode + 3*doorLeft.nCode + 7*doorRight.nCode;
		a += jump ? 2 : 0;
		a += function == BlockFunction.openGoal? 4 : 0;
		a += function == BlockFunction.openJump? 8 : 0;
		a += function == BlockFunction.openRedDoor? 16 : 0;
		a += isGoal? 32 : 0;
		return a;
	}
	
	public void doFunction(Stage s){
		switch(function){
		case nothing:break;
		case openRedDoor: s.isRedOpen = true; break;
		case openJump: s.isJumpOpen = true; break;
		case openGoal: s.isGoalOpen = true; break;
		}
	}
	
	public String toString(){
		return doorUp +" " + doorDown+" " +doorLeft+" " +doorRight;
	}
}
