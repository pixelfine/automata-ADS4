package Code;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class GridTile extends StackPane{
	enum state{NUMBER, UP, DOWN, LEFT, RIGHT}
	double posX;
	double posY;
	double width;
	double height;
	Rectangle rec;
	state state;
	Label label=new Label("");
	Object code;
	public GridTile(state state, double posX, double posY, double width, double height, Object object){
		setTranslateX(posX);
		setTranslateY(posY);
		this.width=width;
		this.height=height;
		this.state=state;
		this.code=object;
		rec = new Rectangle(posX,posY,width,height);
		rec.setStroke(Color.BLACK);
		label.setText(String.valueOf(object)); 
		createView();
		super.getChildren().addAll(rec, label);
	}
	public void createView() {
		switch(this.state) {
		case NUMBER : rec.setFill(Color.web("rgba(0%,0%,0%,0.4)")); break;
		case UP : rec.setFill(new ImagePattern(new Image(getClass().getResource("Ressources/mario/mario_up.png").toExternalForm()))); break;
		case DOWN : rec.setFill(new ImagePattern(new Image(getClass().getResource("Ressources/mario/mario_down.png").toExternalForm()))); break;
		case LEFT : rec.setFill(new ImagePattern(new Image(getClass().getResource("Ressources/mario/mario_left.png").toExternalForm()))); break;
		case RIGHT : rec.setFill(new ImagePattern(new Image(getClass().getResource("Ressources/mario/mario_right.png").toExternalForm()))); break;
		}
	}
	public void setState(int num) {
		switch(num) {
		case 0 : this.state=state.RIGHT; break;
		case 1 : this.state= state.UP; break; 
		case 2 : this.state=state.LEFT; break;
		case 3 : this.state=state.DOWN; break; 
		default : this.state=state.NUMBER; break;
		}
	}
}
