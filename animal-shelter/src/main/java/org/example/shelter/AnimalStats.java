package main.java.org.example.shelter;

import main.java.org.example.model.Animal;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class AnimalStats {
    public static <T extends Animal> double averageAge(List<T> animals) {
        double total = 0.00d;
        for (Animal animal: animals) {
            total += animal.getAge();
        }
        if (animals.stream().count() == 0) {
            return total;
        }
        return total / animals.stream().count();
    }

    public static <T extends Animal> T oldest(List<T> animals) {
        if (animals.isEmpty()) {
            return null;
        }
        T beast = animals.getFirst();
        for (T animal: animals) {
            if (animal.getAge() > beast.getAge()) {
                beast = animal;
            }
        }
        return beast;
    }

    public static <T extends Animal> Map<String, Integer> countBySpecies(List<T> animals) {
        Map<String, Integer> grouppedAnimals = new HashMap<>();
        for(T animal: animals) {
            String species = animal.getClass().getSimpleName();
            if(grouppedAnimals.containsKey(species)) {
                grouppedAnimals.put(species, grouppedAnimals.get(species) + 1);
            } else {
                grouppedAnimals.put(species, 1);
            }
        }

        return grouppedAnimals;
    }
}
