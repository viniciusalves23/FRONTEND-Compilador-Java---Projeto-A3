import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.text.DecimalFormat;
import java.util.List;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Main extends Application {
    private TextArea mathExpressionArea;
    private TextArea studentLanguageArea;
    private TextArea outputArea;

    @Override
    public void start(Stage primaryStage) {
        // Criando os elementos da interface
        Label titleLabel = new Label("Compilador");
        Label optionLabel = new Label("Escolha uma opção:");
        Button option1Button = new Button("1 - Resolver expressão matemática");
        Button option2Button = new Button("2 - Converter linguagem aluno em Java");
        mathExpressionArea = new TextArea();
        mathExpressionArea.setPromptText("Digite a expressão matemática...");
        Button solveMathButton = new Button("Resolver Expressão");
        studentLanguageArea = new TextArea();
        studentLanguageArea.setPromptText("Digite o código na linguagem aluno...");
        Button convertLanguageButton = new Button("Converter Linguagem Aluno");
        outputArea = new TextArea();
        outputArea.setEditable(false);

        // Ocultando os elementos inicialmente
        mathExpressionArea.setVisible(false);
        solveMathButton.setVisible(false);
        studentLanguageArea.setVisible(false);
        convertLanguageButton.setVisible(false);
        outputArea.setVisible(false);

        // Configurando os eventos dos botões
        option1Button.setOnAction(event -> {
            clearTextAreas();
            mathExpressionArea.setVisible(true);
            solveMathButton.setVisible(true);
            studentLanguageArea.setVisible(false);
            convertLanguageButton.setVisible(false);
            outputArea.setVisible(true);
        });

        option2Button.setOnAction(event -> {
            clearTextAreas();
            mathExpressionArea.setVisible(false);
            solveMathButton.setVisible(false);
            studentLanguageArea.setVisible(true);
            convertLanguageButton.setVisible(true);
            outputArea.setVisible(true);
        });

        solveMathButton.setOnAction(event -> compileExpression());
        convertLanguageButton.setOnAction(event -> compileStudentLanguage());


        // Adicionando o EventHandler para suportar Ctrl+V nos textareas
        mathExpressionArea.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.V) {
                pasteFromClipboard(mathExpressionArea);
            }
        });
  
        studentLanguageArea.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.V) {
                pasteFromClipboard(studentLanguageArea);
            }
        });
      
        // Criando o layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        gridPane.add(titleLabel, 0, 0, 2, 1);
        gridPane.add(optionLabel, 0, 1);
        gridPane.add(option1Button, 1, 1);
        gridPane.add(option2Button, 1, 2);
        gridPane.add(mathExpressionArea, 0, 3, 2, 1);
        gridPane.add(solveMathButton, 0, 4, 2, 1);
        gridPane.add(studentLanguageArea, 0, 3, 2, 1);
        gridPane.add(convertLanguageButton, 0, 4, 2, 1);
        gridPane.add(outputArea, 0, 5, 2, 1);

        Scene scene = new Scene(gridPane, 600, 400);
        primaryStage.setTitle("Compilador");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void compileExpression() {
        String expression = mathExpressionArea.getText();
        if (!expression.isEmpty()) {
            MathScanner sc = new MathScanner(expression);
            List<ScannedToken> scanExp = sc.scan();
            Parser parser = new Parser(scanExp);
            List<ScannedToken> parsed = parser.parse();
  
            VariableScanner variableScanner = new VariableScanner();
            List<Variable> variables = variableScanner.scanVariables(expression);
  
            JavaCodeGenerator javaCodeGenerator = new JavaCodeGenerator();
            String javaCode = javaCodeGenerator.generateJavaCode(parsed);
  
            double result = sc.evaluate(parsed);
            DecimalFormat df = new DecimalFormat("#.##");
            String formattedResult = df.format(result).replace('.', ',');
  
            outputArea.setText("Resultado: " + formattedResult + "\n\nCódigo Java gerado:\n" + javaCode);
        } else {
            outputArea.setText("Por favor, insira uma expressão matemática válida.");
        }
    }
  
    private void compileStudentLanguage() {
        String input = studentLanguageArea.getText();
        if (!input.isEmpty()) {
            String javaCode = ConversorLinguagemAluno.compile(input);
            outputArea.setText("Código em Java gerado:\n\n" + javaCode);
        } else {
            outputArea.setText("Por favor, insira um código na linguagem aluno válido.");
        }
    }
  
    private void clearTextAreas() {
          mathExpressionArea.clear();
          studentLanguageArea.clear();
          outputArea.clear();
    }

    private void pasteFromClipboard(TextArea textArea) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        if (clipboard.hasString()) {
            String clipboardString = clipboard.getString();
            textArea.insertText(textArea.getCaretPosition(), clipboardString);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/*  CODIGO FUNCIONAL] */
/* import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.List;

public class Main extends Application {
    private TextArea inputArea;
    private TextArea outputArea;

    @Override
    public void start(Stage primaryStage) {
        // Criando os elementos da interface
        Label titleLabel = new Label("Compilador");
        Label optionLabel = new Label("Escolha uma opção:");
        Button option1Button = new Button("1 - Resolver expressão matemática");
        Button option2Button = new Button("2 - Converter linguagem aluno em Java");
        inputArea = new TextArea();
        inputArea.setPromptText("Digite aqui...");
        Button compileButton = new Button("Compilar");
        outputArea = new TextArea();
        outputArea.setEditable(false);

        // Configurando os eventos dos botões
        option1Button.setOnAction(event -> {
            inputArea.setPromptText("Digite a expressão matemática...");
            compileButton.setOnAction(e -> compileExpression());
        });

        option2Button.setOnAction(event -> {
            inputArea.setPromptText("Digite o código na linguagem aluno...");
            compileButton.setOnAction(e -> compileStudentLanguage());
        });

        // Criando o layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        gridPane.add(titleLabel, 0, 0, 2, 1);
        gridPane.add(optionLabel, 0, 1);
        gridPane.add(option1Button, 1, 1);
        gridPane.add(option2Button, 1, 2);
        gridPane.add(inputArea, 0, 3, 2, 1);
        gridPane.add(compileButton, 0, 4, 2, 1);
        gridPane.add(outputArea, 0, 5, 2, 1);

        Scene scene = new Scene(gridPane, 600, 400);
        primaryStage.setTitle("Compilador");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void compileExpression() {
        String expression = inputArea.getText();
        MathScanner sc = new MathScanner(expression);
        List<ScannedToken> scanExp = sc.scan();
        Parser parser = new Parser(scanExp);
        List<ScannedToken> parsed = parser.parse();

        VariableScanner variableScanner = new VariableScanner();
        List<Variable> variables = variableScanner.scanVariables(expression);

        JavaCodeGenerator javaCodeGenerator = new JavaCodeGenerator();
        String javaCode = javaCodeGenerator.generateJavaCode(parsed);

        double result = sc.evaluate(parsed);
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedResult = df.format(result).replace('.', ',');

        outputArea.setText("Resultado: " + formattedResult + "\n\nCódigo Java gerado:\n" + javaCode);
    }

    private void compileStudentLanguage() {
        String input = inputArea.getText();
        String javaCode = ConversorLinguagemAluno.compile(input);
        outputArea.setText("Código em Java gerado:\n\n" + javaCode);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

 */