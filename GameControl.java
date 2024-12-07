import javax.swing.*;

public class GameControl {
	private UserInterface ui;
	private Data data;
	private PetControls petControls;
	
   public static void main(String[] args) { 
      GameControl gameControl = new GameControl();
      gameControl.startGame();
   }
   
   public GameControl() {
	   this.ui = new UserInterface(this);
	   this.data = new Data(this.petControls);
	   this.petControls = new PetControls();
   }
   
   // Starts game
   public void startGame() {
	   ui.createGUI();
   }
   
   public String getUserInput(String prompt) {
	   String userInput = JOptionPane.showInputDialog(ui.getFrame(), prompt);
	    
	   if (userInput != null) {
	       return userInput;
	   } else {
		   return "No input provided";
	   }
   }
   
   // Saves game 
   public void saveGame() {
	   data.savePets();
	   JOptionPane.showMessageDialog(ui.getFrame(), "Game saved!", "Save Game", JOptionPane.INFORMATION_MESSAGE);
   }
   
   // Exits game
   public void exitGame() {
	   System.exit(0);
   }
}