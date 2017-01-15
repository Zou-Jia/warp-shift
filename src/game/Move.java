package game;

enum Direction {
	up(Game.lenX), down(Game.lenX), left(Game.lenY), right(Game.lenY);	
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
			return "block "+blockIndex + " move " + dir + " ";
		}else if (jump){
			return "people jumped";
		}else{
			return "people " + dir;
		}
	}
}