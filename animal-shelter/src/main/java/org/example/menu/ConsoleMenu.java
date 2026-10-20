package main.java.org.example.menu;

import main.java.org.example.model.*;
import main.java.org.example.shelter.AnimalStats;
import main.java.org.example.shelter.Shelter;

import java.util.List;
import java.util.Map;
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
            int userInput = readInt("Your choice:");
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
                    System.out.println("What species you would like: Dog, Cat, Bird, Rabbit");
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
                case 6:
                    System.out.println("Show animals sorted by: 1 Name; 2 Age");
                    int sortBy = scanner.nextInt();
                    List<Animal> sortedAnimals = switch (sortBy) {
                        case 1 -> shelter.sortByName();
                        case 2 -> shelter.sortByAge();
                        default -> shelter.getAllAnimals();
                    };
                    printAnimals(sortedAnimals, true);
                    break;
                case 7:
                    System.out.println("Shelter statistics");
                    List<Animal> allAnimals = shelter.getAllAnimals();
                    System.out.println("Avarage animal age:" + AnimalStats.averageAge(allAnimals));
                    Animal oldest = AnimalStats.oldest(allAnimals);
                    System.out.println("Oldest animal is "
                            + oldest.getClass().getSimpleName() + " called "
                            + oldest.getName() + " which is " + oldest.getAge() + " years young");
                    Map<String, Integer> countStats = AnimalStats.countBySpecies(allAnimals);
                    for (Map.Entry<String, Integer> entry : countStats.entrySet()) {
                        System.out.println(entry.getKey() + ":" + entry.getValue());
                    }
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
                6. Show shelter animals sorted
                7. Shelter statistics
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
        int animalType;
        do {
            animalType = readInt("What is animal type? 1. Dog; 2. Cat; 3. Bird:");
            if (animalType < 1 || animalType > 3) {
                System.out.println("Please choose 1, 2 or 3");
            }
        } while (animalType < 1 || animalType > 3);

        String name;
        do {
            System.out.print("Animal name:");
            name = s.nextLine();
            if (name.isBlank()) {
                System.out.println("Name cannot be empty");
            }
        } while (name.isBlank());

        int age;
        do {
            age = readInt("Animal age:");
            if (age < 0) {
                System.out.println("Age cannot be negative");
            }
        } while (age < 0);

        return switch (animalType) {
            case 1 -> new Dog(new AnimalId(), name, age);
            case 2 -> new Cat(new AnimalId(), name, age);
            case 3 -> new Bird(new AnimalId(), name, age);
            default -> null;
        };
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {        // is the next thing typed a number?
                int number = scanner.nextInt();
                scanner.nextLine();            // eat the Enter left behind
                return number;
            }
            scanner.nextLine();                // throw away the bad text, like "abc"
            System.out.println("Please enter a number");
        }
    }
}
