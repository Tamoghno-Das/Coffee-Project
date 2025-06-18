package com.example.controller;

import com.example.Coffe;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/coffees")
public class CoffeController {

    private final List<Coffe> coffees = new ArrayList<>();

    public CoffeController() {
        loadInitialCoffees();
    }

    private void loadInitialCoffees() {
        coffees.addAll(List.of(
                new Coffe("Espresso", 300, "Strong", "Italy"),
                new Coffe("Latte", 200, "Mild", "France"),
                new Coffe("Cappuccino", 250, "Balanced", "Italy"),
                new Coffe("Mocha", 280, "Sweet", "USA")
        ));
    }

    @GetMapping
    public List<Coffe> getCoffees(@RequestParam(required = false) String type) {
        if (type == null) return coffees;

        return coffees.stream()
                .filter(c -> c.getCoffeeType().equalsIgnoreCase(type))
                .toList();
    }

    @GetMapping("/{name}")
    public Coffe getCoffeeByName(@PathVariable String name) {
        return coffees.stream()
                .filter(coffee -> coffee.getCoffeeName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public String createCoffee(@RequestBody Coffe newCoffee) {
        boolean exists = coffees.stream()
                .anyMatch(c -> c.getCoffeeName().equalsIgnoreCase(newCoffee.getCoffeeName()));
        if (exists) {
            return "Coffee with this name already exists!";
        }
        coffees.add(newCoffee);
        return "Coffee added successfully!";
    }

    @PutMapping("/{name}")
    public String updateCoffee(@PathVariable String name, @RequestBody Coffe updatedCoffee) {
        for (int i = 0; i < coffees.size(); i++) {
            if (coffees.get(i).getCoffeeName().equalsIgnoreCase(name)) {
                coffees.set(i, updatedCoffee);
                return "Coffee updated successfully!";
            }
        }
        return "Coffee not found!";
    }

    @DeleteMapping("/{name}")
    public String deleteCoffee(@PathVariable String name) {
        boolean removed = coffees.removeIf(c -> c.getCoffeeName().equalsIgnoreCase(name));
        return removed ? "Coffee deleted successfully!" : "Coffee not found!";
    }
}