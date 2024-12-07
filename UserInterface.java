import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class UserInterface {
	private GameControl gameControl;
	private JFrame frame;
	private Data data;
	private PetControls petControls;
	
	public UserInterface(GameControl gameControl) {
		this.gameControl = gameControl;
		this.petControls = new PetControls();
		this.data = new Data(this.petControls);
	}
	
	public void createGUI() {
		frame = new JFrame("Virtual Pet Simulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
		
		// Creates main panel
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		// Creates buttons
		JButton startButton = new JButton("Start Game");
		JButton exitButton = new JButton("Exit Game");
		
		// Start button
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String userInput = "";
				
				userInput = gameControl.getUserInput("Game Started!\nWhat would you like to do? (create/load)");

			    if(userInput.equalsIgnoreCase("create")) {
			    	createPetAction();
			    } else if(userInput.equalsIgnoreCase("load")) {
			    	loadPetsAction();
			    } else {
			        System.out.println("Error! Invalid input, please try again");
				    }
			}
			
		});
		
		// Exit button
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				gameControl.exitGame();
			}
		});
		
		// Add buttons to the panel
		panel.add(startButton);
		panel.add(exitButton);
		
		// Add panel to the frame
		frame.getContentPane().add(panel);
		
		// Make the frame visible
		frame.setVisible(true);
	}
	
	private void createPetAction() {
		String name = gameControl.getUserInput("Enter your pet's name: ");
		if(name.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this.getFrame(), "Pet name cannot be empty");
	        return;
		}
		
		String petType = gameControl.getUserInput("Enter the type of pet: ");
		if(petType.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this.getFrame(), "Pet type cannot be empty");
	        return;
		}
		
		String traits = gameControl.getUserInput("Enter your pet's custom traits separated by commas (e.g., playful, friendly, etc.): ");
		ArrayList<String> customTraits = new ArrayList<>();
		
		if(!traits.trim().isEmpty()) {
			String[] arr = traits.split(",");
			for(String trait : arr) {
				customTraits.add(trait.trim());
			}
		}
		
		Pet newPet = petControls.createPet(name, petType, customTraits);
		System.out.println("Pet created: " + newPet);
		data.addPet(newPet);
		data.savePets();
		
		JOptionPane.showMessageDialog(this.getFrame(), "You are playing with " + newPet.getName() + " ^-^");
		interactButtons(newPet);
		
		newPet.statusUpdate();
		newPet.hungry(newPet, data, gameControl);
	}
	
	private void loadPetsAction() {
		data.loadPets();
		List<Pet> pets = petControls.getPets();
		
		if(pets.isEmpty()) {
			JOptionPane.showMessageDialog(this.getFrame(), "No pets to load");
			return;
		}
		
		StringBuilder petListBuilder = new StringBuilder("Your pets:\n");
		for(int i = 0; i < pets.size(); i++) {
			Pet pet = pets.get(i);
			petListBuilder.append(i+1).append(". ").append(pet.getName()).append(" (").append(pet.getPetType()).append(")\n");
		}
		
		String petListString = petListBuilder.toString();
		String input = gameControl.getUserInput(petListString + "\nEnter the pet number you want: ");
		
		int index = -1;
		while(true) {
			try {
				index = Integer.parseInt(input) - 1;
				if(index < 0 || index >= pets.size()) {
					throw new IndexOutOfBoundsException();
				}
				break;
			} catch(Exception e) {
				JOptionPane.showMessageDialog(this.getFrame(), "Invalid selection. Please try again");
				input = gameControl.getUserInput(petListString + "\nEnter the pet number you want: ");
			}
		}
		
		Pet listPet = pets.get(index);
		data.addPet(listPet);
		data.savePets();
		
		JOptionPane.showMessageDialog(this.getFrame(), "You are playing with " + listPet.getName() + " ^-^");
		interactButtons(listPet);
		
		listPet.statusUpdate();
		listPet.hungry(listPet, data, gameControl);
	}
	
	private void interactButtons(Pet pet) {
		frame.getContentPane().removeAll();
		
		JButton feedButton = new JButton("Feed");
		JButton playButton = new JButton("Play");
		JButton sleepButton = new JButton("Sleep");
		JButton statusButton = new JButton("Check Status");
		JButton exitButton = new JButton("Exit");
		
		feedButton.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				petInteractions(pet, "feed");
			}
		});
		
		playButton.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				petInteractions(pet, "play");
			}
		});
		
		sleepButton.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				petInteractions(pet, "sleep");
			}
		});
		
		statusButton.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				petInteractions(pet, "check status");
			}
		});
		
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				gameControl.startGame();
			}
		});
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		panel.add(feedButton);
		panel.add(playButton);
		panel.add(sleepButton);
		panel.add(statusButton);
		panel.add(exitButton);
		
		frame.getContentPane().add(panel);
		
	    frame.revalidate();
	    frame.repaint();
		
		frame.setVisible(true);
	}
	
	private void petInteractions(Pet pet, String action) {
		switch (action) {
		case "feed":
			pet.feed();
			JOptionPane.showMessageDialog(this.getFrame(), "You fed " + pet.getName() + "!");
			break;
		case "play":
			pet.play();
			JOptionPane.showMessageDialog(this.getFrame(), "You played with " + pet.getName() + "!");
			break;
		case "sleep":
			pet.sleep();
			JOptionPane.showMessageDialog(this.getFrame(), pet.getName() + " is sleeping...");
			gameControl.saveGame();
			break;
		case "check status":
			pet.displayStatus();
			break;
		default:
			JOptionPane.showMessageDialog(this.getFrame(), "Unknown action, please try again");
			break;
		}
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
}