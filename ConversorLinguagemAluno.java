import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConversorLinguagemAluno {
    private static final Pattern VAR_PATTERN = Pattern.compile("var:\\s*([a-zA-Z,]+);");
    private static final Pattern ASSIGNMENT_PATTERN = Pattern.compile("([a-zA-Z]+)\\s*<-\\s*([^;]+);");

    public static String compile(String input) {
        StringBuilder javaCode = new StringBuilder();
        javaCode.append("public class Main {\n");
        javaCode.append("    public static void main(String[] args) {\n");

        List<String> variables = new ArrayList<>();
        Matcher varMatcher = VAR_PATTERN.matcher(input);
        if (varMatcher.find()) {
            String[] varDeclarations = varMatcher.group(1).split(",");
            for (String var : varDeclarations) {
                variables.add(var.trim());
                javaCode.append("        double ").append(var.trim()).append(" = 0;\n");
            }
        }

        Scanner scanner = new Scanner(input);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.startsWith("inicio") || line.startsWith("fim") || line.isEmpty()) {
                continue;
            }
            Matcher assignmentMatcher = ASSIGNMENT_PATTERN.matcher(line);
            if (assignmentMatcher.find()) {
                String variable = assignmentMatcher.group(1).trim();
                String expression = assignmentMatcher.group(2).trim();
                javaCode.append("        ").append(variable).append(" = ").append(expression).append(";\n");
            }
        }
        scanner.close();

        javaCode.append("        System.out.println(").append(variables.get(variables.size() - 1)).append(");\n");
        javaCode.append("    }\n");
        javaCode.append("}\n");

        return javaCode.toString();
    }
}
