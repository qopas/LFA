package Lab2;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AutomatonTypeChecker {
    public static boolean isDeterministic(Map<String, Map<String, String>> transitions) {
        // Iterate through each state's transitions
        for (Map<String, String> stateTransitions : transitions.values()) {
            Set<String> seenSymbols = new HashSet<>();
            // Check if there are duplicate symbols in the transitions
            for (String symbol : stateTransitions.keySet()) {
                if (seenSymbols.contains(symbol)) {
                    return false; // If a symbol is seen more than once, it's non-deterministic
                }
                if(!symbol.equals("d")) {
                    seenSymbols.add(symbol);
                }
                else{
                    seenSymbols.add("c");
                }
            }
        }
        return true; // If no duplicate symbols found, it's deterministic
    }
}
