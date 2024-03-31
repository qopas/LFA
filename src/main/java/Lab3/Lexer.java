package Lab3;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String input;
    private int position;
    private List<String> tokens;

    public Lexer(String input) {
        this.input = input;
        this.position = 0;
        this.tokens = new ArrayList<>();
    }

    public List<String> tokenize() {
        while (position < input.length()) {
            char currentChar = input.charAt(position);
            switch (currentChar) {
                case 'a':
                    tokens.add("T(a)");
                    position++;
                    break;
                case 'b':
                    tokens.add("T(b)");
                    position++;
                    break;
                case 'c':
                    tokens.add("T(c)");
                    position++;
                    break;
                case 'd':
                    tokens.add("T(d)");
                    position++;
                    break;
                default:
                    if (currentChar == 'S' || currentChar == 'B' || currentChar == 'D') {
                        tokens.add("NT(" + currentChar + ")");
                        position++;
                    } else {
                        // Handle unexpected characters or syntax errors
                        System.err.println("Unexpected character: " + currentChar);
                        position++;
                    }
                    break;
            }
        }
        return tokens;
    }

    public static void main(String[] args) {
        String input = "abcdaab";
        Lexer lexer = new Lexer(input);
        List<String> tokens = lexer.tokenize();
        System.out.println("Tokens: " + tokens);
    }
}