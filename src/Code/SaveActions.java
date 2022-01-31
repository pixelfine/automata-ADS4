package Code;

public class SaveActions {
	int dir;
	int posX;
	int posY;
	Object value;
	String info;
	public SaveActions(Grid grid) {
		this.dir=new Integer(grid.getDir());
		this.posX=new Integer(grid.getPosX());
		this.posY=new Integer(grid.getPosY());
		this.value=grid.getValue(posX, posY);
	}
	public SaveActions(Grid grid, String info) {
		this.dir=new Integer(grid.getDir());
		this.posX=new Integer(grid.getPosX());
		this.posY=new Integer(grid.getPosY());
		this.value=grid.getValue(posX, posY);
		this.info=info;
	}
}
