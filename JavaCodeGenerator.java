import java.util.List;

public class JavaCodeGenerator {
    public String generateJavaCode(List<ScannedToken> parsedExpression) {
        StringBuilder javaCode = new StringBuilder();
        javaCode.append("public class ExpressaoMatematica {\n");
        javaCode.append("    public static void main(String[] args) {\n");

        // Variáveis únicas para cada valor na expressão
        char varName = 'a';
        for (ScannedToken token : parsedExpression) {
            if (token.type() == TokenType.VALUE) {
                javaCode.append("        double " + varName + " = " + token.expression() + ";\n");
                varName++;
            }
        }

        // Expressão final usando as variáveis criadas
        javaCode.append("        double result = ");
        javaCode.append(buildExpression(parsedExpression, 'a'));
        javaCode.append(";\n");

        // Impressão do resultado
        javaCode.append("        System.out.println(result);\n");

        javaCode.append("    }\n");
        javaCode.append("}\n");

        return javaCode.toString();
    }

    private String buildExpression(List<ScannedToken> parsedExpression, char varName) {
        StringBuilder expression = new StringBuilder();

        for (ScannedToken token : parsedExpression) {
            if (token.type() == TokenType.VALUE) {
                expression.append(varName++);
            } else {
                expression.append(token.expression());
            }
        }

        return expression.toString();
    }
}