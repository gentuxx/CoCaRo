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
