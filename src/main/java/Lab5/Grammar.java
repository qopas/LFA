package Lab5;

import java.util.*;

public class Grammar {
    private Set<String> Vn; // Non-terminals
    private Set<String> Vt; // Terminals
    private Map<String, List<String>> productions; // Production rules
    private String startSymbol; // Start symbol

    public Grammar(Set<String> Vn, Set<String> Vt, Map<String, List<String>> productions, String startSymbol) {
        this.Vn = Vn;
        this.Vt = Vt;
        this.productions = productions;
        this.startSymbol = startSymbol;
    }

    public Set<String> getVn() {
        return Vn;
    }

    public Set<String> getVt() {
        return Vt;
    }

    public Map<String, List<String>> getProductions() {
        return productions;
    }

    public String getStartSymbol() {
        return startSymbol;
    }

    public void setVn(Set<String> vn) {
        Vn = vn;
    }

    public void setVt(Set<String> vt) {
        Vt = vt;
    }

    public void setProductions(Map<String, List<String>> productions) {
        this.productions = productions;
    }

    public void setStartSymbol(String startSymbol) {
        this.startSymbol = startSymbol;
    }
}
