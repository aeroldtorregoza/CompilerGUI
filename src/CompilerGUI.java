import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class CompilerGUI extends JFrame implements ActionListener {

    private JButton openFileButton, lexicalButton, syntaxButton, semanticButton, clearButton;
    private JTextArea codeTextArea, resultTextArea;

    public static void main(String[] args) {
        // Construct and show the program window.
        CompilerGUI gui = new CompilerGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close window on exit
        gui.setLocationRelativeTo(null); // Screen window in the center
        gui.setVisible(true);
    }

    public CompilerGUI() {
        super("Analysis Tool");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Construct and include elements
        openFileButton = new JButton("Open File");
        lexicalButton = new JButton("Lexical Analysis");
        lexicalButton.setEnabled(false);
        syntaxButton = new JButton("Syntax Analysis");
        syntaxButton.setEnabled(false);
        semanticButton = new JButton("Semantic Analysis");
        semanticButton.setEnabled(false);
        clearButton = new JButton("Clear");

        codeTextArea = new JTextArea(10, 30);
        resultTextArea = new JTextArea(5, 30);

        add(openFileButton);
        add(new JLabel("Code:"));
        add(new JScrollPane(codeTextArea));
        add(lexicalButton);
        add(syntaxButton);
        add(semanticButton);
        add(new JLabel("Analysis Result:"));
        add(new JScrollPane(resultTextArea));
        add(clearButton);

        openFileButton.addActionListener(this);
        lexicalButton.addActionListener(this);
        syntaxButton.addActionListener(this);
        semanticButton.addActionListener(this);
        clearButton.addActionListener(this);

        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        boolean lexicalPassed = false;
        if (event.getSource() == openFileButton) {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    Scanner scanner = new Scanner(file);
                    StringBuilder codeContent = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        codeContent.append(scanner.nextLine() + "\n");
                    }
                    codeTextArea.setText(codeContent.toString());
                    lexicalButton.setEnabled(true); // Button for lexical analysis enabled
                    lexicalPassed = false; // Clear the analysis phase flags.
                } catch (FileNotFoundException ex) {
                    resultTextArea.setText("Error opening file: " + ex.getMessage());
                }
            }
        } else if (event.getSource() == lexicalButton) {
            // Sample Lexical Analysis using Regular Expressions
            String code = codeTextArea.getText();
            Pattern keywordPattern = compile("(if|else|while|for|int|float|return)<span class=");
            Pattern identifierPattern = compile(("^[a-zA-Z][a-zA-Z0-9]*</span>"));
            Pattern operatorPattern = compile("^[+*-/=<>]$");

            boolean lexPass = true; // Assume early success.
            resultTextArea.setText(""); // Clear previous results

            for (String line : code.split("\n")) {
                for (String token : line.split("\\s+")) { // Line split by whitespace
                    if (keywordPattern.matcher(token).matches() &&
                            identifierPattern.matcher(token).matches() &&
                            operatorPattern.matcher(token).matches()) {
                        lexPass = false; //Invalid token discovered
                        String lineNumber = null;
                        resultTextArea.append("Lexical error: Unrecognized token \"" + token + "\" on line " + lineNumber + "\n");
                        break; // Stop examining this line for errors.
                    }
                }
            }
            String tokens = String.valueOf(codeTextArea);
            if (lexPass) {
                lexicalPassed = true;
                resultTextArea.setText("Lexical Phase Passed!");
                syntaxButton.setEnabled(true); // Button for syntax analysis enabled
            } else
            {
                resultTextArea.setText("Lexical Phase Failed!");
                lexicalButton.setEnabled(false); // Continue to disable lexical analysis.
            }


        } else if (event.getSource() == syntaxButton) {
            if (lexicalPassed) {
            } else if (event.getSource() == syntaxButton) {
                String code = codeTextArea.getText();
                Pattern keywordPattern = compile("(if|else|while|for|int|float|return)<span class=");
                Pattern identifierPattern = compile(("^[a-zA-Z][a-zA-Z0-9]*</span>"));
                Pattern operatorPattern = compile("^[+*-/=<>]$");

                boolean SynPass = true; // Assume early success.
                resultTextArea.setText(""); // Clear previous results

                for (String lines : code.split("\n")) {
                    for (String tokens : lines.split("\\s+")) { // Line split by whitespace
                        if (keywordPattern.matcher(tokens).matches() &&
                                identifierPattern.matcher(tokens).matches() &&
                                operatorPattern.matcher(tokens).matches()) {
                            SynPass = false; // Invalid token discovered
                            String lineNumber = null;
                            resultTextArea.append("Syntax error: Unrecognized token \"" + tokens + "\" on line " + lineNumber + "\n");
                            break; // Stop examining this line for errors.
                        }
                    }
                }
                if (SynPass) {
                    resultTextArea.setText("Syntax Phase Passed!");
                    boolean SyntaxPass = true;
                    semanticButton.setEnabled(true); // Enable syntax analysis button
                } else {
                    resultTextArea.setText("Snytax Phase Failed!");
                    semanticButton.setEnabled(false); // Keep lexical analysis disabled
                }
            }
            else if (event.getSource() == semanticButton) {
                if(lexicalPassed){
                }
            }
        } else if (event.getSource() == semanticButton) {
            String code = codeTextArea.getText();
            Pattern keywordPattern = compile("(if|else|while|for|int|float|return)<span class=");
            Pattern identifierPattern = compile(("^[a-zA-Z][a-zA-Z0-9]*</span>"));
            Pattern operatorPattern = compile("^[+*-/=<>]$");

            boolean SemPass = true; // Assume success initially
            resultTextArea.setText(""); // Clear previous results

            for (String lines : code.split("\n")) {
                for (String tokenss : lines.split("\\s+")) { // Line split by whitespace
                    if (keywordPattern.matcher(tokenss).matches() &&
                            identifierPattern.matcher(tokenss).matches() &&
                            operatorPattern.matcher(tokenss).matches()) {
                        SemPass = false; //  Invalid token discovered
                        String lineNumber = null;
                        resultTextArea.append("Syntax error: Unrecognized token \"" + tokenss + "\" on line " + lineNumber + "\n");
                        break; //  Stop examining this line for errors.
                    }
                }
                if (SemPass) {
                    resultTextArea.setText("Semantic Analysis Passed!");
                    boolean SemanticPass = true;
                }
            }
        } if (event.getSource() == clearButton) {

            codeTextArea.setText(" ");
            resultTextArea.setText(" ");
            lexicalButton.setEnabled(false);
            syntaxButton.setEnabled(false);
            semanticButton.setEnabled(false);
        }
    }
}

