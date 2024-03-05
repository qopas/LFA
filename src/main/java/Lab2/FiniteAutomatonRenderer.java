package Lab2;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.security.SFile;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class FiniteAutomatonRenderer {

    public static void main(String[] args) throws IOException {
        // Define your finite automaton
        String plantUmlCode = generatePlantUMLCode();

        // Render the PlantUML diagram
        generateFromStringSource(new File("from-string.png"));
    }

    private static String generatePlantUMLCode() {
        StringBuilder sb = new StringBuilder();
        sb.append("@startuml\n");
        sb.append("title Finite Automaton\n");

        // Define states and transitions
        sb.append("[*] --> q0\n");
        sb.append("q0 --> q0 : a\n");
        sb.append("q0 --> q1 : b\n");
        sb.append("q1 --> q1 : c\n");
        sb.append("q1 --> q2 : c\n");
        sb.append("q2 --> q0 : a\n");
        sb.append("q1 --> q1 : a\n");

        // Define final states
        sb.append("q2 --> [*]\n");

        sb.append("@enduml\n");

        return sb.toString();
    }

    private static void generateFromStringSource(File file) throws IOException {

        SourceStringReader reader = new SourceStringReader(generatePlantUMLCode());
        // Write the first image to "png"
        String desc = reader.generateImage(SFile.fromFile(file));
        // Return a null string if no generation
        System.out.println("generateFromStringSource: " + desc);
    }
}
