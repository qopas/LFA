package Lab5;

import java.util.*;

public class CNFConverter {

    private Map<String, List<String>> mapVariableProduction = new LinkedHashMap<>();
    private String epselonFound = "";
    private int lineCount;
    private String input;
    public CNFConverter() {
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public void setMapVariableProduction(Map<String, List<String>> mapVariableProduction) {
        this.mapVariableProduction = mapVariableProduction;
    }

    public Map<String, List<String>> getMapVariableProduction() {
        return mapVariableProduction;
    }

    public void convertCFGtoCNF() {
        System.out.println("Converting CFG to CNF...");
        System.out.println("Original Grammar:");
        insertNewStartSymbol();
        convertStringtoMap();
        printMap();
        eliminateEpselon();
        removeDuplicateKeyValue();
        eliminateSingleVariable();
        onlyTwoTerminalandOneVariable();
        eliminateThreeTerminal();
        System.out.println("\nCNF Grammar:");
        printMap();
    }
    private void printMap() {

        Iterator<Map.Entry<String, List<String>>> it = mapVariableProduction.entrySet().iterator();
        boolean firstPairSkipped = false;
        while (it.hasNext()) {
            Map.Entry<String, List<String>> pair = it.next();
            if (!firstPairSkipped) {
                firstPairSkipped = true;
                continue;
            }
            System.out.println(pair.getKey() + " -> " + pair.getValue());
        }

        System.out.println(" ");
    }
    private void eliminateEpselon() {

        System.out.println("Remove Epselon....");

        for (int i = 0; i < lineCount; i++) {
            removeEpselon();
        }

        printMap();

    }
     void removeEpselon() {

        Iterator itr = mapVariableProduction.entrySet().iterator();
        Iterator itr2 = mapVariableProduction.entrySet().iterator();

        while (itr.hasNext()) {
            Map.Entry entry = (Map.Entry) itr.next();
            ArrayList<String> productionRow = (ArrayList<String>) entry.getValue();

            if (productionRow.contains("ε")) {
                if (productionRow.size() > 1) {
                    productionRow.remove("ε");
                    epselonFound = entry.getKey().toString();


                } else {

                    // remove if less than 1
                    epselonFound = entry.getKey().toString();
                    mapVariableProduction.remove(epselonFound);
                }
            }
        }

        // find B and eliminate them
        while (itr2.hasNext()) {

            Map.Entry entry = (Map.Entry) itr2.next();
            ArrayList<String> productionList = (ArrayList<String>) entry.getValue();

            for (int i = 0; i < productionList.size(); i++) {
                String temp = productionList.get(i);

                for (int j = 0; j < temp.length(); j++) {
                    if (epselonFound.equals(Character.toString(productionList.get(i).charAt(j)))) {

                        if (temp.length() == 2) {

                            // remove specific character in string
                            temp = temp.replace(epselonFound, "");

                            if (!mapVariableProduction.get(entry.getKey().toString()).contains(temp)) {
                                mapVariableProduction.get(entry.getKey().toString()).add(temp);
                            }

                        } else if (temp.length() == 3) {

                            String deletedTemp = new StringBuilder(temp).deleteCharAt(j).toString();

                            if (!mapVariableProduction.get(entry.getKey().toString()).contains(deletedTemp)) {
                                mapVariableProduction.get(entry.getKey().toString()).add(deletedTemp);
                            }

                        } else if (temp.length() == 4) {

                            String deletedTemp = new StringBuilder(temp).deleteCharAt(j).toString();

                            if (!mapVariableProduction.get(entry.getKey().toString()).contains(deletedTemp)) {
                                mapVariableProduction.get(entry.getKey().toString()).add(deletedTemp);
                            }
                        } else {

                            if (!mapVariableProduction.get(entry.getKey().toString()).contains("ε")) {
                                mapVariableProduction.get(entry.getKey().toString()).add("ε");
                            }
                        }
                    }
                }
            }
        }
    }
     public void removeDuplicateKeyValue() {

        System.out.println("Remove Duplicate Key Value ... ");

        Iterator<Map.Entry<String, List<String>>> itr3 = mapVariableProduction.entrySet().iterator();

        while (itr3.hasNext()) {
            Map.Entry entry = (Map.Entry) itr3.next();
            ArrayList<String> productionRow = (ArrayList<String>) entry.getValue();

            for (int i = 0; i < productionRow.size(); i++) {
                if (productionRow.get(i).contains(entry.getKey().toString())) {
                    productionRow.remove(entry.getKey().toString());
                }
            }
        }

        printMap();
    }
        private void eliminateSingleVariable() {

            System.out.println("Remove Single Variable in Every Production ... ");

            for (int i = 0; i < lineCount; i++) {
                removeSingleVariable();
            }

            printMap();

        }
    void removeSingleVariable() {

        Iterator<Map.Entry<String, List<String>>> itr4 = mapVariableProduction.entrySet().iterator();
        String key = null;


        while (itr4.hasNext()) {

            Map.Entry entry = (Map.Entry) itr4.next();
            Set<String> set = mapVariableProduction.keySet();
            ArrayList<String> keySet = new ArrayList<String>(set);
            ArrayList<String> productionList = (ArrayList<String>) entry.getValue();

            for (int i = 0; i < productionList.size(); i++) {
                String temp = productionList.get(i);

                for (int j = 0; j < temp.length(); j++) {

                    for (int k = 0; k < keySet.size(); k++) {
                        if (keySet.get(k).equals(temp)) {

                            key = entry.getKey().toString();
                            List<String> productionValue = mapVariableProduction.get(temp);
                            productionList.remove(temp);

                            for (int l = 0; l < productionValue.size(); l++) {
                                mapVariableProduction.get(key).add(productionValue.get(l));
                            }
                        }
                    }
                }
            }
        }
    }



    private Boolean checkDuplicateInProductionList(Map<String, List<String>> map, String key) {

        Boolean notFound = true;

        Iterator itr = map.entrySet().iterator();
        outerloop:

        while (itr.hasNext()) {

            Map.Entry entry = (Map.Entry) itr.next();
            ArrayList<String> productionList = (ArrayList<String>) entry.getValue();

            for (int i = 0; i < productionList.size(); i++) {
                if (productionList.size() < 2) {

                    if (productionList.get(i).equals(key)) {
                        notFound = false;
                        break outerloop;
                    } else {
                        notFound = true;
                    }
                }
            }
        }

        return notFound;
    }
    private void onlyTwoTerminalandOneVariable() {
        System.out.println("Assign new variable for two non-terminal or one terminal ... ");

        Iterator<Map.Entry<String, List<String>>> itr5 = mapVariableProduction.entrySet().iterator();
        String key = null;
        int asciiBegin = 71; // 'G'

        Map<String, List<String>> tempList = new LinkedHashMap<>();

        while (itr5.hasNext()) {
            Map.Entry<String, List<String>> entry = itr5.next();
            List<String> productionList = entry.getValue();

            for (String temp : productionList) {
                if (temp.length() == 3) {
                    String newProduction = temp.substring(1, 3);

                    if (!checkDuplicateInProductionList(tempList, newProduction) && !checkDuplicateInProductionList(mapVariableProduction, newProduction)) {
                        ArrayList<String> newVariable = new ArrayList<>();
                        newVariable.add(newProduction);
                        key = Character.toString((char) asciiBegin);
                        tempList.put(key, newVariable);
                        asciiBegin++;
                    }
                } else if (temp.length() == 2) {
                    String newProduction = Character.toString(temp.charAt(1));

                    if (!checkDuplicateInProductionList(tempList, newProduction) && !checkDuplicateInProductionList(mapVariableProduction, newProduction)) {
                        ArrayList<String> newVariable = new ArrayList<>();
                        newVariable.add(newProduction);
                        key = Character.toString((char) asciiBegin);
                        tempList.put(key, newVariable);
                        asciiBegin++;
                    }
                } else if (temp.length() == 4) {
                    String newProduction1 = temp.substring(0, 2);
                    String newProduction2 = temp.substring(2, 4);

                    if (!checkDuplicateInProductionList(tempList, newProduction1) && !checkDuplicateInProductionList(mapVariableProduction, newProduction1)) {
                        ArrayList<String> newVariable1 = new ArrayList<>();
                        newVariable1.add(newProduction1);
                        key = Character.toString((char) asciiBegin);
                        tempList.put(key, newVariable1);
                        asciiBegin++;
                    }

                    if (!checkDuplicateInProductionList(tempList, newProduction2) && !checkDuplicateInProductionList(mapVariableProduction, newProduction2)) {
                        ArrayList<String> newVariable2 = new ArrayList<>();
                        newVariable2.add(newProduction2);
                        key = Character.toString((char) asciiBegin);
                        tempList.put(key, newVariable2);
                        asciiBegin++;
                    }
                }
            }
        }

        mapVariableProduction.putAll(tempList);
        printMap();
    }

    void eliminateThreeTerminal() {

        System.out.println("Replace two terminal variable with new variable ... ");

        for (int i = 0; i < lineCount; i++) {
            removeThreeTerminal();
        }

        printMap();

    }
    private void removeThreeTerminal() {

        Iterator itr = mapVariableProduction.entrySet().iterator();
        ArrayList<String> keyList = new ArrayList<>();
        Iterator itr2 = mapVariableProduction.entrySet().iterator();

        // obtain key that use to eliminate two terminal and above
        while (itr.hasNext()) {
            Map.Entry entry = (Map.Entry) itr.next();
            ArrayList<String> productionRow = (ArrayList<String>) entry.getValue();

            if (productionRow.size() < 2) {
                keyList.add(entry.getKey().toString());
            }
        }

        // find more than three terminal or combination of variable and terminal to eliminate them
        while (itr2.hasNext()) {

            Map.Entry entry = (Map.Entry) itr2.next();
            ArrayList<String> productionList = (ArrayList<String>) entry.getValue();

            if (productionList.size() > 1) {
                for (int i = 0; i < productionList.size(); i++) {
                    String temp = productionList.get(i);

                    for (int j = 0; j < temp.length(); j++) {

                        if (temp.length() > 2) {
                            String stringToBeReplaced1 = temp.substring(j, temp.length());
                            String stringToBeReplaced2 = temp.substring(0, temp.length() - j);

                            for (String key : keyList) {

                                List<String> keyValues = new ArrayList<>();
                                keyValues = mapVariableProduction.get(key);
                                String[] values = keyValues.toArray(new String[keyValues.size()]);
                                String value = values[0];

                                if (stringToBeReplaced1.equals(value)) {

                                    mapVariableProduction.get(entry.getKey().toString()).remove(temp);
                                    temp = temp.replace(stringToBeReplaced1, key);

                                    if (!mapVariableProduction.get(entry.getKey().toString()).contains(temp)) {
                                        mapVariableProduction.get(entry.getKey().toString()).add(i, temp);
                                    }
                                } else if (stringToBeReplaced2.equals(value)) {

                                    mapVariableProduction.get(entry.getKey().toString()).remove(temp);
                                    temp = temp.replace(stringToBeReplaced2, key);

                                    if (!mapVariableProduction.get(entry.getKey().toString()).contains(temp)) {
                                        mapVariableProduction.get(entry.getKey().toString()).add(i, temp);
                                    }
                                }
                            }
                        } else if (temp.length() == 2) {

                            for (String key : keyList) {

                                List<String> keyValues = new ArrayList<>();
                                keyValues = mapVariableProduction.get(key);
                                String[] values = keyValues.toArray(new String[keyValues.size()]);
                                String value = values[0];


                                for (int pos = 0; pos < temp.length(); pos++) {
                                    String tempChar = Character.toString(temp.charAt(pos));


                                    if (value.equals(tempChar)) {

                                        mapVariableProduction.get(entry.getKey().toString()).remove(temp);
                                        temp = temp.replace(tempChar, key);

                                        if (!mapVariableProduction.get(entry.getKey().toString()).contains(temp)) {
                                            mapVariableProduction.get(entry.getKey().toString()).add(i, temp);
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            } else if (productionList.size() == 1) {

                for (int i = 0; i < productionList.size(); i++) {
                    String temp = productionList.get(i);

                    if (temp.length() == 2) {

                        for (String key : keyList) {

                            List<String> keyValues = new ArrayList<>();
                            keyValues = mapVariableProduction.get(key);
                            String[] values = keyValues.toArray(new String[keyValues.size()]);
                            String value = values[0];


                            for (int pos = 0; pos < temp.length(); pos++) {
                                String tempChar = Character.toString(temp.charAt(pos));


                                if (value.equals(tempChar)) {

                                    mapVariableProduction.get(entry.getKey().toString()).remove(temp);
                                    temp = temp.replace(tempChar, key);

                                    if (!mapVariableProduction.get(entry.getKey().toString()).contains(temp)) {
                                        mapVariableProduction.get(entry.getKey().toString()).add(i, temp);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
    private void insertNewStartSymbol() {

        String newStart = "S0";
        ArrayList<String> newProduction = new ArrayList<>();
        newProduction.add("S");

        mapVariableProduction.put(newStart, newProduction);
    }
    private String[] splitEnter(String input) {

        String[] tmpArray = new String[lineCount];
        for (int i = 0; i < lineCount; i++) {
            tmpArray = input.split("\\n");
        }
        return tmpArray;
    }
    private void convertStringtoMap() {

        String[] splitedEnterInput = splitEnter(input);

        for (int i = 0; i < splitedEnterInput.length; i++) {

            String[] tempString = splitedEnterInput[i].split("->|\\|");
            String variable = tempString[0].trim();

            String[] production = Arrays.copyOfRange(tempString, 1, tempString.length);
            List<String> productionList = new ArrayList<String>();

            // trim the empty space
            for (int k = 0; k < production.length; k++) {
                production[k] = production[k].trim();
            }

            // import array into ArrayList
            for (int j = 0; j < production.length; j++) {
                productionList.add(production[j]);
            }

            //insert element into map
            mapVariableProduction.put(variable, productionList);
        }
    }

    public void setInput(String input) {
        this.input = input;
    }

    public static void main(String[] args) {
        String productions = "S -> aB | A\n" +
        "A -> bAb | aS | A\n" +
                "B -> AbB | BS | a | ε\n" +
                "C -> BA\n" +
                "D -> a";

        /*
        S -> aB | A\n" +
                "A -> bAb | aS | A\n" +
                "B -> AbB | BS | a | ε\n" +
                "C -> BA\n" +
                "D -> a
         */
        CNFConverter cnf = new CNFConverter();
        cnf.setInput(productions);
        cnf.setLineCount(productions.lines().toList().size());
        cnf.convertCFGtoCNF();
    }
}
