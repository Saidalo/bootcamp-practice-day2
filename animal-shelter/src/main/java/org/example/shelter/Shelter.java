package main.java.org.example.shelter;

import main.java.org.example.model.AdoptionStatus;
import main.java.org.example.model.Animal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Shelter <T extends Animal>{
    private final List<T> animals = new ArrayList<>();

    public void addAnimal(T animal){
        animals.add(animal);
    }

    public List<T> getAllAnimals(){
        return this.animals;
    }

    public List<T> findBySpecies(String species){
        return this.animals.stream().filter(animal -> animal.getSpecies().equalsIgnoreCase(species)).toList();
    }

    public List<T> findAvailableAnimals(){
        return this.animals.stream().filter(animal -> animal.getAdoptionStatus().equals(AdoptionStatus.AVAILABLE)).toList();
    }

    public List<T> sortByName() {
        return animals.stream().sorted(Comparator.comparing(Animal::getName)).toList();
    }

    public List<T> sortByAge() {
        return animals.stream().sorted(Comparator.comparing(Animal::getAge)).toList();
    }

    public void markAsAdopted(String id){
        List<T> found = animals.stream().filter(animal -> animal.getId().toString().equals(id)).toList();
        if (found.isEmpty()) {
            System.out.println("No animal found with id: " + id);
            return;
        }
        Animal pet = found.get(0);
        pet.markAsAdopted();
        System.out.println("Congratulations " + pet.getName() + " has family now");
    }
}
