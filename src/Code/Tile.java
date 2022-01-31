package Code;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle{
	enum typeTile{LOADGRID, LOADPROG, RUN}
	typeTile type;
	public Tile(typeTile type, double x, double y, double width, double height) {
		super(x, y, width, height);
		this.type=type;
		createView();
	}
	public Tile(typeTile type, double width, double height) {
		super(width, height);
		this.type=type;
		createView();
	}
	public void createView() {
		switch(type) {
		case LOADGRID : createLoadView(1); break;
		case LOADPROG : createLoadView(2); break;
		case RUN : createLoadView(3); break;
		}
	}
	public void createLoadView(int num) {
		switch(num) {
		case 1 : super.setFill(new ImagePattern(new Image(getClass().getResource("Ressources/LoadGrid.jpg").toExternalForm()))); break;
		case 2 : super.setFill(new ImagePattern(new Image(getClass().getResource("Ressources/LoadProg.jpg").toExternalForm()))); break;
		case 3 : super.setFill(new ImagePattern(new Image(getClass().getResource("Ressources/Run.png").toExternalForm()))); break;
		}
	}
}