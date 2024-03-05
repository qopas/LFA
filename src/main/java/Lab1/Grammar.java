package Lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public void setProductions(List<String> productions) {
        this.productions = productions;
    }

    // Generate a string using the grammar rules
    public String generateString() {
        StringBuilder sb = new StringBuilder();
        generateStringHelper("S", sb); // Start with the start symbol 'S'
        return sb.toString();
    }
    public String classifyGrammar() {
        boolean isType3 = true;  // Initially assuming it might be Type 3 (Regular Grammar)
        boolean isType2 = true;  // Initially assuming it might be Type 2 (Context-Free Grammar)

        for (String production : productions) {
            String[] parts = production.split(" → ");
            String lhs = parts[0].trim();
            String rhs = parts[1].trim();

            // Check for Type 2 (Context-Free Grammar): LHS must be a single non-terminal
            if (!lhs.matches("[A-Z]")) {
                isType2 = false;  // Not Type 2 if LHS is not a single non-terminal
            }

            // Check for Type 3 (Regular Grammar):
            // RHS must be a single terminal, or a terminal followed by a non-terminal, or a non-terminal followed by a terminal
            if (!(rhs.matches("[a-z]") || rhs.matches("[a-z][A-Z]") || rhs.matches("[A-Z][a-z]"))) {
                isType3 = false;  // Not Type 3 if RHS doesn't follow the specific patterns
            }
        }

        // Determine the grammar type based on checks
        if (isType3) {
            return "Type 3 (Regular Grammar)";
        } else if (isType2) {
            return "Type 2 (Context-Free Grammar)";
        } else {
            return "Type 1 (Context-Sensitive Grammar) or Type 0 (Recursively Enumerable)";
        }
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