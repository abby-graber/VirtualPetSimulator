import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

// Class to contain pet object
public class Pet {
   private String name;
   private String petType;
   private int health;
   private int happiness;
   private int energyLevel;
   private int hunger;
   private ArrayList<String> customTraits;
   
   public Pet() {
       this.name = "...";          
       this.petType = "...";       
       this.health = 100;              
       this.happiness = 100;           
       this.energyLevel = 100;         
       this.hunger = 0;                
       this.customTraits = new ArrayList<String>(); 
   }
   
   // Arg constructor
   public Pet(String name, String petType) {
      this.name = name;
      this.petType = petType;
      this.health = 100;
      this.happiness = 100;
      this.energyLevel = 100;
      this.hunger = 0;
      this.customTraits = new ArrayList<>();
   }
   
   /** Getters */
   
   public String getName() {
      return name;
   }
   
   public String getPetType() {
      return petType;
   }
   
   public int getHealth() {
      return health;
   }
   
   public int getHappiness() {
      return happiness;
   }
   
   public int getEnergyLevel() {
      return energyLevel;
   }
   
   public int getHunger() {
      return hunger;
   }
   
   public ArrayList<String> getCustomTraits() {
      return customTraits;
   }
   
   /** Setters */
   
   public void setName(String name) {
      this.name = name;
   }
   
   public void setPetType(String petType) {
      this.petType = petType;
   }
   
   public void setHealth(int health) {
      this.health = Math.max(0, Math.min(health, 100));
   }
   
   public void setHappiness(int happiness) {
       this.happiness = Math.max(0, Math.min(happiness, 100));
   }
   
   public void setEnergyLevel(int energyLevel) {
      this.energyLevel = Math.max(0, Math.min(energyLevel, 100));
   }
   
   public void setHunger(int hunger) {
      this.hunger = Math.max(0, Math.min(hunger, 100));
   }
   
   public void addCustomTrait(String trait) {
      this.customTraits.add(trait);
   }
   
   /** Methods */
   
   // Increases health, happiness, and energy
   public void feed() {
      setHealth(getHealth() + 15);
      setHappiness(getHappiness() + 5);
      setEnergyLevel(getEnergyLevel() + 10);
      setHunger(getHunger() - 20);
   }
   
   // Increases happiness and decreases energy
   public void play() {
      setHappiness(getHappiness() + 15);
      setEnergyLevel(getEnergyLevel() - 5);
      setHunger(getHunger() + 5);
   }
   
   // Increases health and energy
   public void sleep() {
      setHealth(getHealth() + 10);
      setEnergyLevel(getEnergyLevel() + 25);
      setHunger(getHunger() + 10);
   }
   
   public void hungry(Pet pet, Data data, GameControl gameControl) {
	  ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
      scheduler.scheduleAtFixedRate(new Runnable() {
         @Override
         public void run() {
        	 if (getHunger() == 100) {
        		 setHealth(getHealth() - 5);
                 
                 if (getHealth() == 20) {
                     String warning = "Warning! Your pet's health is at 20% health!";
                     JOptionPane.showMessageDialog(null, warning, getName() + " needs to be fed!", JOptionPane.INFORMATION_MESSAGE);
                 } else if (getHealth() <= 0) {
                     String wompWomp = "Your pet is dead :(";
                     JOptionPane.showMessageDialog(null, wompWomp, getName() + " has been removed from your saved pets", JOptionPane.INFORMATION_MESSAGE);

                     data.removePet(pet);
                     gameControl.startGame();

                     scheduler.shutdown();
                     return;
                 }
             }
          }
      }, 0, 30, TimeUnit.SECONDS);
   }
   
   // Constantly updates the pet stats, i.e. health, happiness, and energy
   public void statusUpdate() {
      ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
      scheduler.scheduleAtFixedRate(new Runnable() {
         @Override
         public void run() {
        	 setHappiness(getHappiness() - 2);
             setEnergyLevel(getEnergyLevel() - 2);
             setHunger(getHunger() + 1);
             
             if(getHealth() == 0) {
            	 scheduler.shutdown();
                 return; 
             }
         }
      }, 0, 30, TimeUnit.SECONDS);

   }
   
   // Displays pet stats as they update
   public void displayStatus() {
      String stats = "Health: " + getHealth() + "%\n" +
    		  		 "Happiness: " + getHappiness() + "%\n" +
    		  		 "Energy Level: " + getEnergyLevel() + "%\n" +
    		  		 "Hunger: " + getHunger() + "%";
      JOptionPane.showMessageDialog(null, stats, getName() + "'s Stats", JOptionPane.INFORMATION_MESSAGE);
   }
   
   @Override
   public boolean equals(Object obj) {
       if (this == obj) return true; 
       if (obj == null || getClass() != obj.getClass()) return false; 

       Pet pet = (Pet) obj; 
       return name.equals(pet.name); 
   }

   @Override
   public int hashCode() {
       return name.hashCode(); 
   }
}

