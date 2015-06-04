package CoCaRo;

public class Position {
	
	public final static Position NORTH = new Position(0,-1);
	public final static Position SOUTH = new Position(0,1);
	public final static Position WEST = new Position(-1,0);
	public final static Position EAST = new Position(1,0);
	
	private int xPos;
	private int yPos;
	
	public Position(int xPos, int yPos) {
		this.xPos = xPos ;
		this.yPos = yPos ;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Position) {
			Position position = (Position)obj;
			return position.getX()==xPos && position.getY()==yPos;
		}
		return false;
	}
	
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}

	public void setX(int xPos) {
		if(xPos>=0) {
			this.xPos = xPos;
		}
		else {
			System.out.println("X vaut "+xPos);
		}
	}

	public void setY(int yPos) {
		if(yPos>=0) {
			this.yPos = yPos;
		}
		else {
			System.out.println("Y vaut "+yPos);
		}
	}

	public void updatePosition(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public String toString() {
		return "("+xPos+";"+yPos+")";
	}
}
