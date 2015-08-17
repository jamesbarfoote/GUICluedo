package guiCluedo.game;

public class Location {
	private int x = 0;
	private int y = 0;

	public Location( int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Checks that this location is equal to another
	 * @param l - The location you are checking against
	 * @return boolean
	 */
	public boolean equals(Location l){
		if(l.getX() == this.getX() && l.getY() == this.getY()){
			return true;
		}
		return false;
	}
}