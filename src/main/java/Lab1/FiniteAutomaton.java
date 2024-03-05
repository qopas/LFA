package Lab1;

import java.util.*;

public class FiniteAutomaton {
    private Set<String> states;
    private Set<String> alphabet;
    private Map<String, Map<String, String>> transitions;
    private Set<String> acceptingStates;
    private String startState;

    public FiniteAutomaton(Set<String> states, Set<String> alphabet, Map<String, Map<String, String>> transitions, Set<String> acceptingStates, String startState) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.acceptingStates = acceptingStates;
        this.startState = startState;
    }

    public FiniteAutomaton(Grammar grammar) {
        // Initialize sets and maps
        states = new HashSet<>();
        alphabet = new HashSet<>();
        transitions = new HashMap<>();
        acceptingStates = new HashSet<>();

        // Initialize start state
        startState = "S";

        // Construct finite automaton
        constructFiniteAutomaton(grammar);
    }

    private void constructFiniteAutomaton(Grammar grammar) {
        // Add states
        states.add("S");
        states.add("B");
        states.add("D");

        // Add alphabet
        alphabet.add("a");
        alphabet.add("b");
        alphabet.add("c");
        alphabet.add("d");

        // Initialize transitions for each state
        transitions.put("S", new HashMap<>());
        transitions.put("B", new HashMap<>());
        transitions.put("D", new HashMap<>());

        // Define transitions based on grammar rules
        for (String production : grammar.getProductions()) {
            String[] parts = production.split(" → ");
            String fromState = parts[0];
            String toSymbols = parts[1];

            if (toSymbols.length() == 2) { // Example: "S → aS"
                String symbol = toSymbols.substring(0, 1);
                String toState = toSymbols.substring(1);

                transitions.get(fromState).put(symbol, toState);
                alphabet.add(symbol);
            } else if (toSymbols.length() == 1) { // Example: "B → d"
                String symbol = toSymbols;

                if (symbol.equals("d") || symbol.equals("b")) {
                    // If it's a terminal symbol leading to a terminal state
                    transitions.get(fromState).put(symbol, "D");
                } else {
                    // Handle other terminal symbols (if applicable)
                }
                alphabet.add(symbol);
            }
        }

        // Set 'D' as the only accepting state
        acceptingStates.add("D");

    }
    public boolean acceptsInput(String input) {
        String currentState = startState; // Start from the start state
        for (char symbol : input.toCharArray()) {
            String symbolStr = Character.toString(symbol);
            // If the current state does not have a transition for the symbol, return false
            if (!transitions.get(currentState).containsKey(symbolStr)) {
                return false;
            }
            // Move to the next state based on the transition
            currentState = transitions.get(currentState).get(symbolStr);
        }
        // Check if the final state is an accepting state
        return acceptingStates.contains(currentState);
    }
    // Getters
    public Set<String> getStates() {
        return states;
    }

    public Set<String> getAlphabet() {
        return alphabet;
    }

    public Map<String, Map<String, String>> getTransitions() {
        return transitions;
    }

    public Set<String> getAcceptingStates() {
        return acceptingStates;
    }

    public String getStartState() {
        return startState;
    }

    public static void main(String[] args) {
        Grammar grammar = new Grammar();
        FiniteAutomaton finiteAutomaton = new FiniteAutomaton(grammar);

        // Print Finite Automaton details
        System.out.println("States: " + finiteAutomaton.getStates());
        System.out.println("Alphabet: " + finiteAutomaton.getAlphabet());
        System.out.println("Transitions: " + finiteAutomaton.getTransitions());
        System.out.println("Start State: " + finiteAutomaton.getStartState());

        String input1 = "bcd";
        String input2 = "abc";
        String input3 = "baaccd";
        String input4 = "dab";

        // Test input strings against the automaton
        System.out.println("Input1 accepted? " + finiteAutomaton.acceptsInput(input1));
        System.out.println("Input2 accepted? " + finiteAutomaton.acceptsInput(input2));
        System.out.println("Input3 accepted? " + finiteAutomaton.acceptsInput(input3));
        System.out.println("Input4 accepted? " + finiteAutomaton.acceptsInput(input4));
    }
}