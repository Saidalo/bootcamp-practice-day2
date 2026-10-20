package main.java.org.example.menu;

import main.java.org.example.model.*;
import main.java.org.example.shelter.Shelter;

import java.util.List;
import java.util.Scanner;


public class ConsoleMenu {
    private final Shelter<Animal> shelter;
    private final Scanner scanner =  new Scanner(System.in);
    public ConsoleMenu(Shelter<Animal> shelter) {
        this.shelter = shelter;
    }

    public void start(){
        // Show menu in a loop
        while (true) {
            printMenu();
            int userInput = scanner.nextInt();
            // Call correct 'Shelter' methods based on selected option

            switch (userInput) {
                case 1:
                    Animal myPet = getAnimalCredentials(scanner);
                    if(myPet == null) {
                        System.out.println("Something went wrong, please try again");
                    } else {
                        this.shelter.addAnimal(myPet);
                        System.out.println("Congratulations: " + myPet.getName() + " is in our shelter now");
                    }
                    break;
                case 2:
                    listAllAnimalsInShelter();
                    break;
                case 3:
                    System.out.println("What species you would like: Dog, Cat, Bird");
                    scanner.nextLine();
                    String species = scanner.nextLine();
                    printAnimalsBySpecies(species);
                    break;
                case 4:
                    List<Animal> animals = shelter.findAvailableAnimals();
                    if (animals.isEmpty()) {
                        System.out.println("Sorry, all our animals are Adopted");
                    } else {
                        printAnimals(animals, true);
                    }
                    break;
                case 5:
                    System.out.print("Your animal id:");
                    scanner.nextLine();
                    String animalID = scanner.nextLine();
                    shelter.markAsAdopted(animalID);
                    break;
                case 0:
                default:
                    return;
            }
        }
    }

    private void printMenu(){
        System.out.println("""
                1. Add animal
                2. List all animals
                3. Find animals by species
                4. List available animals
                5. Mark animal as adopted
                0. Exit
                """);
    }

    private void printAnimalsBySpecies(String species) {
        List<Animal> animals = shelter.findBySpecies(species);
        if (animals.isEmpty()) {
            System.out.println("Sorry there is no " + species + "s for now");
            return;
        }
        System.out.println("All of our " + species +"s:");
        printAnimals(animals, false);

    }

    private void listAllAnimalsInShelter() {
        printAnimals(shelter.getAllAnimals(), true);
    }

    private void printAnimals(List<Animal> animals, boolean includeSpecies) {
        for(Animal animal: animals) {
            System.out.println("id:" + animal.getId().toString() + " " +
                    (includeSpecies ? "Species: " + animal.getClass().getSimpleName() + " " : "") +
                    "name:" + animal.getName() +
                    " age: " + animal.getAge() +
                    " status: " + (animal.getAdoptionStatus().equals(AdoptionStatus.AVAILABLE) ? "Available" : "Adopted"));
        }
    }

    private Animal getAnimalCredentials(Scanner s) {
        System.out.println("What is animal type:");
        System.out.println("1. Dog; 2. Cat; 3. Bird");
        int animalType = s.nextInt();
        s.nextLine();
        System.out.print("Animal name:");
        String name = s.nextLine();
        System.out.print("Animal age:");
        int age = s.nextInt();
        return switch (animalType) {
            case 1 -> new Dog(new AnimalId(), name, age);
            case 2 -> new Cat(new AnimalId(), name, age);
            case 3 -> new Bird(new AnimalId(), name, age);
            default -> null;
        };
    }
}
