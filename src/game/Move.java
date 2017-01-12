package game;

enum Direction {
	up(3), down(3), left(4), right(4);	
	int nCode;
	Direction(int _nCode) {
		this.nCode = _nCode;
	}
	int getInt(){
		return nCode;
	}
}

class Move{
	Direction dir;
	boolean isBlock;
	int blockIndex;
	boolean jump;
	Move(Direction dir,boolean isBlock,int blockIndex, boolean jump){
		this.dir = dir;
		this.isBlock = isBlock;
		this.blockIndex = blockIndex;
		this.jump = jump;
	}
	
	public String toString(){
		if(isBlock){
			return blockIndex + " " + dir + " ";
		}else{
			return "people " + dir;
		}
	}
}