package Code;

import java.io.File;
import java.util.ArrayList;

import Code.GridTile.state;
import Code.Tile.typeTile;
import Code.AbstractSyntaxTree.MyParser;
import Code.AbstractSyntaxTree.Program;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class InterfaceGraphique {
	public static final double size = 45;
	public static ArrayList<SaveActions> actionList = new ArrayList<SaveActions>();
	public int timerTime=200;
	public Stage stage;
	public Pane leftPane;
	public Pane rightPane;
	public Pane component;
	public File gridFile;
	public File progFile;
	public Label progLabel = new Label("null");
	public Label gridLabel = new Label("null");
	public Label runState = new Label("");
	public GridTile gridTile[][];
	public Grid grid;
	public boolean onTimer=false;
	public AnimationTimer timer;
	public boolean onRun=false;
	public TextField timertxt = new TextField(String.valueOf(timerTime));
	public Label lastAction= new Label("No Actions Recorded");
	public InterfaceGraphique(Stage stage){
		this.stage=stage;
	}
	public Scene getScene() {
		BorderPane root = new BorderPane();
		leftPane = new Pane();
		leftPane();
		rightPane = new Pane();
		rightPane();
		root.setLeft(leftPane);
		root.setRight(rightPane);
		Scene scene = new Scene(root);
		return scene;
	}
	public void leftPane() {
		createGrid();
	}
	public void rightPane() {
		rightPane.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		TileModele loadGrid = new TileModele(new Tile(typeTile.LOADGRID,100,45), this);
		TileModele loadProg = new TileModele(new Tile(typeTile.LOADPROG,100,45), this);
		TileModele run = new TileModele(new Tile(typeTile.RUN,100,100), this);
		Label label = new Label("Fichiers selectionnés :");
		VBox box = new VBox();
		box.setSpacing(10); box.setPadding(new Insets(2*size));
		box.getChildren().addAll(loadGrid.tile, loadProg.tile, label, gridLabel, progLabel, run.tile, runState, timertxt, lastAction);
		rightPane.getChildren().addAll(box);
		
	}
	public void onRun() {
		onRun=true;
		String exeName = "Main";
        IOEnv ioEnv = IOEnv.parseArgs(exeName, new String[] {progFile.getAbsolutePath(), gridFile.getAbsolutePath()});
        Parser parser = new MyParser();
        Program prog = parser.parseProgram(exeName, ioEnv.inProgram);
        Grid grid = Grid.parseGrid(exeName, ioEnv.inGrid);
        Interpreter interp = new StupidInterpreter();
        interp.run(prog, grid);
        ioEnv.outGrid.println(grid);
        onSecondRun();
	}
	public void onSecondRun() {
		String exeName = "Main";
        IOEnv ioEnv = IOEnv.parseArgs(exeName, new String[] {progFile.getAbsolutePath(), gridFile.getAbsolutePath()});
        Parser parser = new MyParser();
        Program prog = parser.parseProgram(exeName, ioEnv.inProgram);
        this.grid = Grid.parseGrid(exeName, ioEnv.inGrid);
        createGrid();
        runState.setText("On Run");
        onTimer=true;
		setTimer(1000,1);
        execAction();
	}
	public void createGrid() {
		if(grid!=null) {
			if(leftPane.getChildren().size()>0) {
				leftPane.getChildren().remove(0);
			}
			component = new Pane();
			gridTile = new GridTile[grid.getSizeX()][grid.getSizeY()];
			for (int y = 0; y < grid.getSizeY() ; y ++) {
	            for (int x = 0; x < grid.getSizeX(); x ++) {
	            	gridTile[x][y]=new GridTile(state.NUMBER, x*size, (grid.getSizeY()-y)*size, size, size, grid.getValue(x, y));
	            	component.getChildren().add(gridTile[x][y]);
	            }
	        }
			setPlayer(gridTile, component);
			leftPane.getChildren().add(component);
		}
	}
	public void setPlayer(GridTile[][] gridTile, Pane component) {
		component.getChildren().remove(gridTile[grid.getPosX()][grid.getPosY()]);
		gridTile[grid.getPosX()][grid.getPosY()].setState(grid.getDir());
		gridTile[grid.getPosX()][grid.getPosY()].createView();
		component.getChildren().add(gridTile[grid.getPosX()][grid.getPosY()]);
	}
	public void execAction(){
		timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	updateTimer();
            	eatAction();
            	if(actionList.size()<1) {onRun=false; timer.stop(); runState.setText("End of Run");}
            }
        };                 
        timer.start();
	}
	public void eatAction() {
		if(!onTimer) {
			this.grid.setDir(actionList.get(0).dir);
			this.grid.setPosX(actionList.get(0).posX);
			this.grid.setPosY(actionList.get(0).posY);
			this.grid.setValue(actionList.get(0).posX, actionList.get(0).posY, actionList.get(0).value);
			this.lastAction.setText("Action : "+actionList.get(0).info);
			actionList.remove(0);
			createGrid();
			onTimer=true;
			setTimer(timerTime,1);
		}
	}
	public void setTimer(int time, int n){
    	Timeline timeline = new Timeline(new KeyFrame(Duration.millis(time),s -> endOfTimer(n))); timeline.play();
    }
	public void endOfTimer(int n) {
		switch(n) {
		case 1 :   onTimer=false; break;
		}
	}
	private void updateTimer() {
		if(isInteger(timertxt.getText())) {
			timerTime=Integer.valueOf(timertxt.getText());
		}
	}
	private boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
}
