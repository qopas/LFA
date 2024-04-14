package Lab6.AST;

import java.util.List;

public class TextMessageNode implements  ASTNode{
    private String messageText;
    private List<ASTNode> children;
    public TextMessageNode(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageText() {
        return messageText;
    }
    public void addChild(ASTNode child) {
        children.add(child);
    }

    public List<ASTNode> getChildren() {
        return children;
    }
    @Override
    public String getText() {
        return "TEXT - " + messageText;
    }
}
