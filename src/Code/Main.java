package Code;
import Code.*;
import Code.AbstractSyntaxTree.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{
	public static Scanner scan;
	public static boolean onIG;
    public static void main(String[] args) throws Exception {
    	if(iGMode()) {onIG=true; Prelauncher.main(args);}
    	else {
    		onIG=false;
	        String exeName = "Main";
	        IOEnv ioEnv = IOEnv.parseArgs(exeName, scanFile());
	        Parser parser = new MyParser();
	        Program prog = parser.parseProgram(exeName, ioEnv.inProgram);
	        Grid grid = Grid.parseGrid(exeName, ioEnv.inGrid);
	        Interpreter interp = new StupidInterpreter();
	        interp.run(prog, grid);
	        ioEnv.outGrid.println(grid);
    	}
    }
    public static boolean iGMode() {
    	System.out.println("Voulez vous lancer l'interface graphique ? : 'yes' | 'no'");
    	String result;
    	while(true) {
    		result="";
    		scan = new Scanner(System.in);
    		result= scan.nextLine();
    		System.out.println(result);
    		if(result.equals("yes")){
    			return true;
    		}else if(result.equals("no")) {
    			return false;
    		}
    	}
    }
    
    public static String[] scanFile() {
    	/*
    	 * programme.txt
  		 * grille.txt
  		 * STOP
    	 */
    	System.out.println("Inserer le nom des fichiers et tapez STOP pour finir");
    	scan=new Scanner(System.in);
    	ArrayList<String> arrList = new ArrayList<String>();
    	String item="src/Code/fichiers/"; String tmp="";
    	while (true) {
    		tmp=scan.nextLine();
    		if(tmp.equals("STOP")) {
    			scan.close();
    			break;
    		}
    		arrList.add(item+tmp);
    	}
    	String[] stringArr = arrList.stream().toArray(String[]::new);
        return stringArr;
    }
}
