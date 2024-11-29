import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// Class to contain pet object
public class Pet {
   private String name;
   private String petType;
   private int health;
   private int happiness;
   private int energyLevel;
   private int hunger;
   private ArrayList<String> customTraits;
   
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
   
   public void hungry() {
      while(getHunger() == 100) {
      
         setHealth(getHealth() - 5);
         
         try {
            Thread.sleep(5000); // Update stat every 5 second (5000 milliseconds)
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
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
            
            displayStatus();
         }
      }, 0, 30, TimeUnit.SECONDS);

   }
   
   // Displays pet stats as they update
   public void displayStatus() {
      System.out.println("------------------------------------------------------------------");
      System.out.println("Health: " + getHealth() + 
                         "%   Happiness: " + getHappiness() + 
                         "%   Energy Level: " + getEnergyLevel() + 
                         "%   Hunger: " + getHunger());
      System.out.println("------------------------------------------------------------------");
   }
 
}