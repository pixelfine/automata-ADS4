package Code;
import java.io.File;

import Code.AbstractSyntaxTree.MyParser;
import Code.AbstractSyntaxTree.Program;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class TileModele {
	Tile tile;
	InterfaceGraphique ig;
	Alert alert;
	public TileModele(Tile tile, InterfaceGraphique ig) {
		this.tile=tile;
		this.ig=ig;
		tile.setOnMouseClicked(this::handleMouseClick);
	}
	public void handleMouseClick(MouseEvent event){
		switch(tile.type) {
		case LOADGRID : handleLoad(1); break;
		case LOADPROG : handleLoad(2); break;
		case RUN : handleRun(); break;
		}
	}
	public void handleRun() {
		if(!ig.onRun) {
			if(ig.progFile!=null && ig.gridFile!=null) {
				ig.onRun();
		        
			}else {
				message("Exception","Vous devez choisir une grille et un programme.");
				return;
			}
		}else {message("Exception","Vous ne pouvez pas Run plusieurs programme en meme temps !");
			return;
		}
	}
	public void handleLoad(int loadNum) {
		File selectedFile = null;
		FileChooser f = new FileChooser();
		f.setTitle("Open Resource File");
		f.setInitialDirectory(new File("src/Code/fichiers/"));
		selectedFile = f.showOpenDialog(null);
		try {
			if(selectedFile.exists() && selectedFile!=null) {
				switch(loadNum) {
				case 1 : ig.gridFile=selectedFile; ig.gridLabel.setText(selectedFile.getName());break;
				case 2 : ig.progFile=selectedFile; ig.progLabel.setText(selectedFile.getName());break;
				}
			}
		}catch(NullPointerException e) {
			message("Exception","Vous devez choisir un fichier"); 
			System.err.println("Vous devez choisir un fichier");
			return; 
		}
		catch(Exception e) { e.printStackTrace(); return; }
	}
	public void message(String message, String message2) {
		this.alert=new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(message);
		alert.setContentText(message2);
		alert.showAndWait();
	}
	
}
