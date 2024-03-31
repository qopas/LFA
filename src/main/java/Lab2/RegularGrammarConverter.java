package Lab2;

import Lab1.FiniteAutomaton;
import Lab1.Grammar;

import java.util.*;

public class RegularGrammarConverter {

    public static Grammar convertToRegularGrammar(FiniteAutomaton fa) {
        List<String> productions = new ArrayList<>();

        // Add productions for each transition
        for (String state : fa.getStates()) {
            Map<String, String> stateTransitions = fa.getTransitions().get(state);
            if (stateTransitions != null) {
                for (Map.Entry<String, String> transition : stateTransitions.entrySet()) {
                    String symbol = transition.getKey();
                    String nextState = transition.getValue();
                    String production;
                    if(symbol.equals("d")){
                        production = state + " → " + "c" + nextState;
                    }
                    else {
                        production = state + " → " + symbol + nextState;
                    }
                    productions.add(production);
                }
            }
        }

        // Add productions for accepting states
        for (String state : fa.getAcceptingStates()) {
            // We add an epsilon production for each accepting state
            String production = state + " → ε";
            productions.add(production);
        }

        // Construct Grammar object
        Grammar grammar = new Grammar();
        grammar.setProductions(productions);

        return grammar;
    }

    public static void main(String[] args) {
        // Define the finite automaton
        Set<String> states = new HashSet<>(Arrays.asList("q0", "q1", "q2"));
        Set<String> alphabet = new HashSet<>(Arrays.asList("a", "b", "c"));
        Map<String, Map<String, String>> transitions = new HashMap<>();
        transitions.put("q0", Map.of("a", "q0", "b", "q1"));
        transitions.put("q1", Map.of("c", "q1", "a", "q1", "d", "q2"));
        transitions.put("q2", Map.of("a", "q0"));
        Set<String> acceptingStates = new HashSet<>(Collections.singletonList("q2"));
        String startState = "q0";

        // Convert the finite automaton to a regular grammar
        FiniteAutomaton fa = new FiniteAutomaton(states,alphabet,transitions,acceptingStates,startState);
        Grammar grammar = RegularGrammarConverter.convertToRegularGrammar(fa);

        // Access the grammar productions
        List<String> grammarProductions = grammar.getProductions();
        System.out.println("Regular Grammar Productions:");
        for (String production : grammarProductions) {
            System.out.println(production);
        }
        System.out.println("Is determenistic: " + AutomatonTypeChecker.isDeterministic(fa.getTransitions())) ;
    }

}