package Lab3;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    // Regular expressions for token patterns
    private static final Pattern AGENT_PATTERN = Pattern.compile("Agent[1-3]");
    private static final Pattern TEXT_PATTERN = Pattern.compile("\"[^\"]*\"");
    private static final Pattern DESTINATION_PATTERN = Pattern.compile("Agent[1-3]");

    public List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher;

        String[] words = input.split("\\s+");

        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            matcher = AGENT_PATTERN.matcher(word);
            if (matcher.matches() && i != 0 && words[i-1 ].equals("to")) {
                tokens.add(new Token(TokenType.DESTINATION, word));
                continue;
            }
            if(matcher.matches()) {
                tokens.add(new Token(TokenType.AGENT, word));
                continue;
            }


            if (word.equals("says")) {
                StringBuilder textBuilder = new StringBuilder();
                while (i + 1 < words.length && !words[i + 1].equals("to")) {
                    textBuilder.append(words[i + 1]).append(" ");
                    i++;
                }
                if (textBuilder.length() > 0) {
                    String text = textBuilder.toString().trim();
                    tokens.add(new Token(TokenType.TEXT, text));
                }
                continue;
            }


            if (!word.equals("to")) {
                tokens.add(new Token(TokenType.ERROR, word)); // Tokenize unrecognized tokens as errors
            }
        }

        return tokens;
    }
    public static void main(String[] args) {
        String input = "The quick brown fox jumps over the lazy dog.";

        // Create lexer
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.tokenize(input);

        // Print tokens
        System.out.println("Tokens:");
        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}