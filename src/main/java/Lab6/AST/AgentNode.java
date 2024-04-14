package Lab6.AST;

import java.util.ArrayList;
import java.util.List;

public class AgentNode implements ASTNode {
    private String agentName;
    private List<ASTNode> children;

    public AgentNode(String agentName) {
        this.agentName = agentName;
        this.children = new ArrayList<>();
    }

    public String getAgentName() {
        return agentName;
    }

    public void addChild(ASTNode child) {
        children.add(child);
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    @Override
    public String getText() {
        return "AGENT - " + agentName;
    }
}
