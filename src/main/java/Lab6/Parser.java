package Lab6;

import Lab3.Lexer;
import Lab3.Token;
import Lab3.TokenType;
import Lab6.AST.*;

import java.util.List;

class Parser {
    private final List<Token> tokens;
    private int currentTokenIndex;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
    }

    public ASTNode parse() {
        ASTNode rootNode = null; // Initialize rootNode as null

        // Check if the input starts with an AGENT token
        if (peek().getType() == TokenType.AGENT) {
            AgentNode agent = agent();
            TextMessageNode textMessage = textMessage();

            // Check if the next token is DESTINATION
            if (peek().getType() == TokenType.DESTINATION) {
                DestinationNode destination = destination();  // Parse the DESTINATION token

                // Construct the AST
                rootNode = new AgentNode("Root Node");
                ((AgentNode) rootNode).addChild(agent);
                ((AgentNode) rootNode).addChild(textMessage);
                ((AgentNode) rootNode).addChild(destination);
            } else {
                throw new RuntimeException("Syntax error: Expected DESTINATION but found " + peek().getType());
            }
        } else {
            throw new RuntimeException("Syntax error: Expected AGENT but found " + peek().getType());
        }

        // Check for end of file
        match(TokenType.END_OF_FILE);

        return rootNode;
    }
    private AgentNode agent() {
        match(TokenType.AGENT);
        return new AgentNode("Agent"); // Create AgentNode with a placeholder name
    }

    private TextMessageNode textMessage() {
        match(TokenType.TEXT);
        return new TextMessageNode("Hello, world!"); // Create TextMessageNode with a placeholder text
    }

    private DestinationNode destination() {
        match(TokenType.DESTINATION);  // Consume the DESTINATION token
        return new DestinationNode("Destination"); // Create DestinationNode with a placeholder destination
    }

    private Token match(TokenType expectedType) {
        Token currentToken = peek();
        if (currentToken.getType() == expectedType) {
            advance();
            return currentToken;
        } else {
            throw new RuntimeException("Syntax error: Expected " + expectedType + " but found " + currentToken.getType());
        }
    }

    private Token peek() {
        if (currentTokenIndex < tokens.size()) {
            return tokens.get(currentTokenIndex);
        } else {
            return new Token(TokenType.END_OF_FILE, "");
        }
    }

    private void advance() {
        currentTokenIndex++;
    }

    public static void main(String[] args) {
        // Input sentence
        String input = "Agent1 says \"Hello, world!\"  to Agent2 ";

        // Create lexer
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.tokenize(input);

        // Print tokens
        System.out.println("Tokens:");
        for (Token token : tokens) {
            System.out.println(token);
        }

        // Create parser
        Parser parser = new Parser(tokens);

        try {
            // Parse input and get the AST
            ASTNode rootNode = parser.parse();

            // Print AST nodes
            System.out.println("AST:");
            printAST(rootNode);

            System.out.println("Parsing successful.");
        } catch (RuntimeException e) {
            System.out.println("Error parsing input sentence: " + e.getMessage());
        }
    }

    private static void printAST(ASTNode node) {
        System.out.println(node.getText());
        if (node instanceof AgentNode) {
            for (ASTNode child : ((AgentNode) node).getChildren()) {
                printAST(child);
            }
        } else if (node instanceof TextMessageNode) {
            // No further nodes in TextMessageNode
        } else if (node instanceof DestinationNode) {
            // No further nodes in DestinationNode
        }
    }
}
