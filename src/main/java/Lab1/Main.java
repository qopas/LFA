package Lab1;
/*
    Lab1:
    TO DO:
        Variant 14:
        VN={S, B, D},
        VT={a, b, c, d},
        P={
            S → aS
            S → bB
            B → cB
            B → d
            B → aD
            D → aB
            D → b
        }

    Lab2:
    Variant 14
    Q = {q0,q1,q2},
    ∑ = {a,b,c},
    F = {q2},
    δ(q0,a) = q0,
    δ(q0,b) = q1,
    δ(q1,c) = q1,
    δ(q1,c) = q2,
    δ(q2,a) = q0,
    δ(q1,a) = q1.
 */
public class Main {
    public static void main(String[] args) {
        Grammar grammar = new Grammar();
        // Generate and print a few strings
        for (int i = 0; i < 5; i++) {
            String generatedString = grammar.generateString();
            System.out.println("Generated String " + (i+1) + ": " + generatedString);
        }
        FiniteAutomaton finiteAutomaton = new FiniteAutomaton(grammar);

        System.out.println("States: " + finiteAutomaton.getStates());
        System.out.println("Alphabet: " + finiteAutomaton.getAlphabet());
        System.out.println("Transitions: " + finiteAutomaton.getTransitions());
        System.out.println("Accepting States: " + finiteAutomaton.getAcceptingStates());
        System.out.println("Start State: " + finiteAutomaton.getStartState());

        String input1 = "aaabd";
        String input2 = "aabccaba";
        String input3 = "abcda";
        String input4 = "abd";
        System.out.println("Input1 accepted? " + finiteAutomaton.acceptsInput(input1));
        System.out.println("Input2 accepted? " + finiteAutomaton.acceptsInput(input2));
        System.out.println("Input3 accepted? " + finiteAutomaton.acceptsInput(input3));
        System.out.println("Input4 accepted? " + finiteAutomaton.acceptsInput(input4));
        System.out.println(grammar.classifyGrammar());
    }
}