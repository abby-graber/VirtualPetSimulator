import java.util.ArrayList;
import java.util.List;

public class PetControls {
   private List<Pet> pets;
   
   // No-arg constructor
   public PetControls() {
	   this.pets = new ArrayList<>();
   }
   
   // Accepts pet from data storage
   public PetControls(List<Pet> pets) {
      this.pets = pets;
   }
   
   // Creates a new pet object 
   public Pet createPet(String name, String petType, ArrayList<String> customTraits) {
	   Pet newPet = new Pet(name, petType);
	   if(customTraits != null) {
		   for(String trait : customTraits) {
			   newPet.addCustomTrait(trait);
		   }
	   }
	   return newPet;
   }
   
   // Gets list of all saved pets
   public List<Pet> getPets() {
	   return pets;
   }
   
   // Loops through the list of pets to return a specific pet by name
   public Pet getPetByName(String name) {
      for(Pet pet : pets) {
    	  if(pet.getName().equalsIgnoreCase(name) ) {
    		  return pet;
    	  }
      }
      return null;
   }
   
   // Updates the stats of all pets while program is active
   public void updatePetStatus() {
      for (Pet p : pets) {
         p.statusUpdate(); 
      }
   }
   
   // Remove a pet from the list
   public void removePet(Pet pet) {
	   if (pets.contains(pet)) {
		   pets.remove(pet);
	   }
   }
   
   // Add a new instance of a Pet to a list
   public void addPet(Pet pet) {
	   if (!pets.contains(pet)) {
		   pets.add(pet);
	   }
   }
   
}