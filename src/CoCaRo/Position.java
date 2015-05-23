package CoCaRo;

public class Position {
	private int xPos;
	private int yPos;
	
	public Position(int xPos, int yPos) {
		this.xPos = xPos ;
		this.yPos = yPos ;
	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}

	public void setX(int xPos) {
		this.xPos = xPos;
	}

	public void setY(int yPos) {
		this.yPos = yPos;
	}

	public void updatePosition(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
}
