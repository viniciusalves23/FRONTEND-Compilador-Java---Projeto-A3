import java.util.ArrayList;
import java.util.List;

public class VariableScanner {
    public List<Variable> scanVariables(String expression) {
        List<Variable> variables = new ArrayList<>();
        String[] parts = expression.split(";");

        for (String part : parts) {
            if (part.contains("var:")) {
                String varPart = part.substring(part.indexOf("var:") + 4, part.indexOf(";")).trim();
                String[] varDeclarations = varPart.split(",");

                for (String varDecl : varDeclarations) {
                    String[] varInfo = varDecl.trim().split(" ");
                    String varName = varInfo[1];
                    double varValue = 0; // You can set an initial value here or prompt the user for input
                    Variable variable = new Variable(varName, varValue);
                    variables.add(variable);
                }
            }
        }

        return variables;
    }
}
