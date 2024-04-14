package Lab6.AST;

import java.util.List;

public class DestinationNode implements ASTNode{
    private String destinationAgent;
    private List<ASTNode> children;
    public DestinationNode(String destinationAgent) {
        this.destinationAgent = destinationAgent;
    }

    public String getDestinationAgent() {
        return destinationAgent;
    }
    public void addChild(ASTNode child) {
        children.add(child);
    }

    public List<ASTNode> getChildren() {
        return children;
    }
    @Override
    public String getText() {
        return "Destination: " + destinationAgent;
    }
}
