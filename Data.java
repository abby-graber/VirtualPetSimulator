import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;


public class Data {
	private List<Pet> pets;
    private PetControls petControls;
    
    public Data() {
    	this.pets = new ArrayList<>();
        this.petControls = null; 
    }
   
    public Data(PetControls petControls) {
	    this.petControls = petControls;
	    this.pets = new ArrayList<>();
    }
   
    // Open a file and write each pet's data to the file; close the file
    public void savePets() {
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	   
	    pets = new ArrayList<>(new HashSet<>(pets));
	    
	    // Write the JSON data to the file
	    try(FileWriter writer = new FileWriter("pet_data.json")) {
		    gson.toJson(pets, writer); // Serialize the list of pets to JSON
	    } catch (IOException e) {
		    e.printStackTrace();
	    }
    }
   
    // Open the file read the data from the file and extracts data
    public void loadPets() {
    	Gson gson = new Gson();
    	
    	try(FileReader reader = new FileReader("pet_data.json")) {
    		Pet[] petArray = gson.fromJson(reader, Pet[].class);
    		for(Pet pet : petArray) {
    			petControls.addPet(pet);
    			pets.add(pet);
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public void addPet(Pet pet) {
    	if (!pets.contains(pet)) {
	        pets.add(pet);
	        petControls.addPet(pet); 
	        savePets();
    	}
    }
    
    public void removePet(Pet pet) {
    	if (pets.contains(pet)) {
            pets.remove(pet);
            petControls.removePet(pet);
            savePets();
        }
    }
    
    public List<Pet> getPets() {
    	return pets;
    }
}