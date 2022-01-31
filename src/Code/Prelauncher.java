package Code;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Prelauncher extends Application{
	public static Stage stage;
	public static boolean changed=false;
	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage stage) {
		Prelauncher.stage=stage;
		sizeHandling(stage);
		stage.setTitle("beep_beep_robot");
		stage.setScene(new InterfaceGraphique(stage).getScene());
		stage.show();
	}
	public void sizeHandling(Stage stage) {
		stage.setHeight(620);
		stage.setWidth(1200);
		stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                stage.setWidth((double)number2);
                changed=true;
            }
        });
        stage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
            	stage.setHeight((double)number2);
            	changed=true;
            }
        });
	}
}