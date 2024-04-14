package Lab4;

import com.mifmif.common.regex.Generex;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegexWordGenerator {

    public static void main(String[] args) {
        String regex = "M?N{2}(O|P){3}Q*R+(X|Y|Z){3}8+(9|0){2}(H|i)(J|K)L*N?";
        Generex generex = new Generex(regex);
        int numberOfStrings = 5;
        for (int i = 0; i < numberOfStrings; i++) {
            String word = generex.random();
            System.out.println(word);
        }
    }

//    public static void main(String[] args) {
//        String regex = "M?N{2}(O|P){3}Q*R+(X|Y|Z){3}8+(9|0){2}(H|i)(J|K)L*N?";
//        List<String> validWords = generateValidWords(regex);
//        System.out.println("Valid words generated from the regular expression:");
//        if (validWords.isEmpty()) {
//            System.out.println("No valid words generated.");
//        } else {
//            for (String word : validWords) {
//                System.out.println(word);
//            }
//        }
//    }

    public static List<String> generateValidWords(String regex) {
        List<String> validWords = new ArrayList<>();
        Random random = new Random();
        while (true) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < regex.length(); i++) {
                char c = regex.charAt(i);
                if (c == 'M') {
                    if(random.nextInt(0,2) == 1) {
                        sb.append("M");
                    }
                } else if (c == 'N') {
                    sb.append("NN");
                } else if (c == 'P') {
                    sb.append("PPP");
                } else if (c == 'R') {
                    int length = random.nextInt(5) + 1; // Random length from 1 to 5
                    for (int j = 0; j < length; j++) {
                        sb.append("R");
                    }
                } else if (c == 'X' || c == 'Y') {
                    sb.append(c);
                } else if (c == '8') {
                    int length = random.nextInt(5) + 1; // Random length from 1 to 5
                    for (int j = 0; j < length; j++) {
                        sb.append("8");
                    }
                } else if (c == '9' || c == '0') {
                    sb.append(random.nextInt(2) == 0 ? '9' : '0'); // Randomly choose '9' or '0'
                } else if (c == 'H' || c == 'i') {
                    sb.append(random.nextInt(2) == 0 ? 'H' : 'i'); // Randomly choose 'H' or 'i'
                } else if (c == 'J' || c == 'K') {
                    sb.append(random.nextInt(2) == 0 ? 'J' : 'K'); // Randomly choose 'J' or 'K'
                } else if (c == 'L') {
                    sb.append("L"); // Optional 'L'
                } else if (c == 'N' && random.nextInt(2) == 0) {
                    sb.append("N"); // Optional 'N'
                }
            }
            String word = sb.toString();
            validWords.add(word);
            break;
        }
        return validWords;
    }
}