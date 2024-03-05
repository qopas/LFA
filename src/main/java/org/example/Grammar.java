package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grammar {
    private List<String> productions;

    public Grammar() {
        // Initialize the production rules
        productions = new ArrayList<>();
        productions.add("S → aS");
        productions.add("S → bB");
        productions.add("B → cB");
        productions.add("B → d");
        productions.add("B → aD");
        productions.add("D → aB");
        productions.add("D → b");
    }

    public List<String> getProductions() {
        return productions;
    }

    // Generate a string using the grammar rules
    public String generateString() {
        StringBuilder sb = new StringBuilder();
        generateStringHelper("S", sb); // Start with the start symbol 'S'
        return sb.toString();
    }

    // Helper method to generate string recursively
    private void generateStringHelper(String symbol, StringBuilder sb) {
        Random rand = new Random();
        // Find all productions with the given non-terminal symbol
        List<String> possibleProductions = new ArrayList<>();
        for (String production : productions) {
            if (production.startsWith(symbol + " →")) {
                possibleProductions.add(production);
            }
        }
        // Choose a random production
        String chosenProduction = possibleProductions.get(rand.nextInt(possibleProductions.size()));
        // Apply the chosen production
        String[] parts = chosenProduction.split(" → ")[1].split("");
        for (String part : parts) {
            if (part.equals("S") || part.equals("B") || part.equals("D")) {
                // Recursively generate string for non-terminal symbol
                generateStringHelper(part, sb);
            } else {
                // Append terminal symbol
                sb.append(part);
            }
        }
    }
}