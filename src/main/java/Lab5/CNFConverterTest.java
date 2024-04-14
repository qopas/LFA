package Lab5;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

public class CNFConverterTest {

    private CNFConverter cnf;

    @Before
    public void setUp() {
        cnf = new CNFConverter();
    }

    @Test
    public void testRemoveEpselon() {
        Map<String, List<String>> testMap = new LinkedHashMap<>();
        List<String> production1 = new ArrayList<>(Arrays.asList("a", "ε"));
        List<String> production2 = new ArrayList<>(Collections.singletonList("b"));
        testMap.put("S", production1);
        testMap.put("A", production2);

        cnf.setMapVariableProduction(testMap);
        cnf.removeEpselon();

        // Check if ε is removed correctly
        assertFalse(cnf.getMapVariableProduction().get("S").contains("ε"));
    }

    @Test
    public void testRemoveSingleVariable() {
        Map<String, List<String>> testMap = new LinkedHashMap<>();
        List<String> production1 = new ArrayList<>(Collections.singletonList("A"));
        List<String> production2 = new ArrayList<>(Collections.singletonList("B"));
        testMap.put("S", production1);
        testMap.put("A", production2);
        cnf.setMapVariableProduction(testMap);
        cnf.removeSingleVariable();
        assertFalse(cnf.getMapVariableProduction().get("S").contains("A"));
    }
    @Test
    public void testEliminateThreeTerminal() {
        Map<String, List<String>> productions = new LinkedHashMap<>();
        productions.put("S", Arrays.asList("aB", "A"));
        productions.put("A", Arrays.asList("bAb", "aS", "a"));
        productions.put("B", Arrays.asList("AbB", "BS", "a", "ε"));
        productions.put("C", Collections.singletonList("BA"));
        productions.put("D", Collections.singletonList("a"));
        CNFConverter cnfConverter = new CNFConverter();
        cnfConverter.setMapVariableProduction(productions);
        cnfConverter.setLineCount(productions.size());

        assertFalse(cnfConverter.getMapVariableProduction().get("S").contains("BS"));
    }


}
