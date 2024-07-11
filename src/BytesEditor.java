import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;

public class BytesEditor implements ActionListener {
    String filename = "BytesEditorTemp.java";
    String pathname = "";
    JFrame frame;
    JMenuBar jMenuBar;
    JMenu file, runBtn, editorBtn, terminalBtn, helpBtn;
    JMenuItem newFileItem, openFileItem, exitItem, saveItem, runItem, saveAndRunItem, increaseEditorFontSize, decreaseEditorFontSize, increaseTerminalFontSize, decreaseTerminalFontSize;
    JTextArea codePane, terminalPane;
    Font codeFont, terminalFont;
    ErrorHandler errorHandler;
    //String tabspace = "    ";

    BytesEditor() {
        System.out.println("Starting Editor...");
        addFonts();
        frame = new JFrame("BytesEditor");
        addMenuBarWithComponents();
        addSubMenuBar();
        setActionListenerToMenu();
        // setting menuBar to the frame
        frame.setJMenuBar(jMenuBar);
        addItemsToMenuBar();
        setUpSplitPane(filename.replace(".java",""));
        setFrameProperties();
        errorHandler = new ErrorHandler(frame);
        System.out.println("Editor Loading Completed...");
    }

    BytesEditor(String cFilename) {
        frame = new JFrame(filename);
        errorHandler = new ErrorHandler(frame);
        if (!cFilename.isEmpty()) {
            filename = cFilename;
            System.out.println("Starting Editor... : " + filename);
            addFonts();
            addMenuBarWithComponents();
            addSubMenuBar();
            setActionListenerToMenu();
            // setting menuBar to the frame
            frame.setJMenuBar(jMenuBar);
            addItemsToMenuBar();
            setUpSplitPane(filename.replace(".java",""));
            setFrameProperties();
            System.out.println("Editor Loading Completed... : " + filename);
        } else {
            errorHandler.generateError("Error : Filename must not be empty");
            System.out.println("Error : Filename not found");
        }
    }

    private void setFrameProperties() {
        frame.setResizable(true);
        frame.setSize(1000, 700);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private void setUpSplitPane(String className) {
        String startingText = "";
        pathname = new File(filename).getAbsolutePath();
        try {
            startingText = FileHandler.readFile(pathname);
        } catch (IOException exception) {
            startingText = String.format("public class %s{\n\tpublic static void main(String args[]){\n\t\tSystem.out.println(\"hello world\");\n\t}\n}", className);
        }
        // code editor
        codePane = new JTextArea(
                null,
                startingText,
                80,
                30
        );
        codePane.setFont(codeFont);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        // Create the coding pane
        JScrollPane codingScrollPane = new JScrollPane(codePane);
        // Create the terminal pane
        terminalPane = new JTextArea("Terminal Pane");
        terminalPane.setFont(terminalFont);
        JScrollPane terminalScrollPane = new JScrollPane(terminalPane);

        // Add panes to the split pane
        splitPane.setTopComponent(codingScrollPane);
        splitPane.setBottomComponent(terminalScrollPane);

        // Set the split pane divider location to 70% of the screen height
        splitPane.setDividerLocation(0.7);

        // Set resize weight
        splitPane.setResizeWeight(0.7);
        // Add the split pane to the frame
        frame.getContentPane().add(splitPane);
    }

    private void addItemsToMenuBar() {
        // --------------adding sub-items---------------
        // File Menu
        file.add(newFileItem);
        file.add(openFileItem);
        file.add(saveItem);
        file.add(exitItem);
        // Run Menu
        runBtn.add(runItem);
        runBtn.add(saveAndRunItem);
        // Editor Menu
        editorBtn.add(increaseEditorFontSize);
        editorBtn.add(decreaseEditorFontSize);
        // Terminal Menu
        terminalBtn.add(increaseTerminalFontSize);
        terminalBtn.add(decreaseTerminalFontSize);
        // -----------------------------------------------

        // adding items to menu bar
        jMenuBar.add(file);
        jMenuBar.add(runBtn);
        jMenuBar.add(editorBtn);
        jMenuBar.add(terminalBtn);
        jMenuBar.add(helpBtn);
    }

    private void setActionListenerToMenu() {
        // actions to sub-menu bar
        newFileItem.addActionListener(this);
        openFileItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        runItem.addActionListener(this);
        saveAndRunItem.addActionListener(this);
        increaseEditorFontSize.addActionListener(this);
        decreaseEditorFontSize.addActionListener(this);
        increaseTerminalFontSize.addActionListener(this);
        decreaseTerminalFontSize.addActionListener(this);
    }

    private void addSubMenuBar() {
        // ------- sub-menu bar -----------------------
        // File
        newFileItem = new JMenuItem("New File");
        openFileItem = new JMenuItem("Open File");
        saveItem = new JMenuItem("Save File");
        exitItem = new JMenuItem("Exit");
        // Run
        runItem = new JMenuItem("Run");
        saveAndRunItem = new JMenuItem("Save and Run");
        // Editor
        increaseEditorFontSize = new JMenuItem("Increase Font Size");
        decreaseEditorFontSize = new JMenuItem("Decrease Font Size");
        // Terminal
        increaseTerminalFontSize = new JMenuItem("Increase Font Size");
        decreaseTerminalFontSize = new JMenuItem("Decrease Font Size");
        // --------------------------------------------
    }

    private void addMenuBarWithComponents() {
        // menu bar
        jMenuBar = new JMenuBar();
        file = new JMenu("File");
        runBtn = new JMenu("Run");
        editorBtn = new JMenu("Editor");
        terminalBtn = new JMenu("Terminal");
        helpBtn = new JMenu("Help");
    }

    private void addFonts() {
        // frame and font properties
        try {
            FontManager.createFont("fonts/MonaspaceNeonVarVF.ttf");
            FontManager.createFont("fonts/JetBrainsMonoNL-Regular.ttf");
            codeFont = new Font("JetBrainsMonoNL-Regular", Font.PLAIN, 20);
            terminalFont = new Font("JetBrainsMonoNL-Regular", Font.PLAIN, 18);
            System.out.println("Fonts loading completed..");
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            System.out.println("Warning : Fonts cannot be loaded! (using default fonts)");
            codeFont = new Font("Monospaced", Font.BOLD, 20);
            terminalFont = new Font("Monospaced", Font.BOLD, 18);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveItem) {
            System.out.println("Saving the file..");
            saveFile();
        } else if (e.getSource() == exitItem) {
            frame.dispose();
        } else if (e.getSource() == runItem) {
            System.out.println("Running the file..");
            runFile();
        } else if (e.getSource() == openFileItem) {
            System.out.println("Trying to open the file");
            JFileChooser fileChooser = new JFileChooser(new File(filename));
            int r = fileChooser.showOpenDialog(frame);
            if (r==JFileChooser.APPROVE_OPTION){
                pathname=fileChooser.getSelectedFile().getAbsolutePath();
                filename=fileChooser.getSelectedFile().getName();
                updateCodePane();
            }
        } else if (e.getSource() == saveAndRunItem) {
            System.out.println("Saving the file..");
            File file = saveFile();
            System.out.println("Running the file..");
            runFile(file);
        } else if (e.getSource() == increaseEditorFontSize) {
            codeFont = new Font(codeFont.getFontName(), codeFont.getStyle(), codeFont.getSize() + 1);
            codePane.setFont(codeFont);
        } else if (e.getSource() == decreaseEditorFontSize) {
            codeFont = new Font(codeFont.getFontName(), codeFont.getStyle(), codeFont.getSize() - 1);
            codePane.setFont(codeFont);
        } else if (e.getSource() == increaseTerminalFontSize) {
            terminalFont = new Font(terminalFont.getFontName(), terminalFont.getStyle(), terminalFont.getSize() + 1);
            terminalPane.setFont(terminalFont);
        } else if (e.getSource() == decreaseTerminalFontSize) {
            terminalFont = new Font(terminalFont.getFontName(), terminalFont.getStyle(), terminalFont.getSize() - 1);
            terminalPane.setFont(terminalFont);
        } else if (e.getSource() == newFileItem) {
            String javaClassName = (String) JOptionPane.showInputDialog(frame, "Enter class name\n eg. Hello (we will create Hello.java)", "Create new file", JOptionPane.QUESTION_MESSAGE, null, null, "Untitled");
            if ((javaClassName != null) && (!javaClassName.isEmpty())) {
                javaClassName = javaClassName.replace(" ", "_") + ".java";
                new BytesEditor(javaClassName);
            }
        } else {
            System.out.println("action performed : " + e.getActionCommand());
        }
    }
    private void updateCodePane(){
        try {
            codePane.setText(FileHandler.readFile(pathname));
            codePane.update(codePane.getGraphics());
        } catch (IOException e) {
            errorHandler.generateError(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    private void runFile() {
        terminalPane.setText("Compiling the file..");
        terminalPane.update(terminalPane.getGraphics());
        try {
            File file = FileHandler.getFile(filename);
            TerminalHandler.executeJava(file);
            String terminalText = "Output : \n"
                    + TerminalHandler.getErrorStream()
                    + TerminalHandler.getInputStream();
            terminalPane.setText(terminalText);
            terminalPane.update(terminalPane.getGraphics());
        } catch (FileNotFoundException ex) {
            errorHandler.generateError("File Not Found!\n(POSSIBLE ISSUE : Check whether you have the file saved or not)");
            throw new RuntimeException(ex);
        } catch (Exception exception) {
            errorHandler.generateError("Error while running the file\n"+exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    private void runFile(File file) {
        try {
            TerminalHandler.executeJava(file);
            terminalPane.setText(
                    "Output : \n"
                            + TerminalHandler.getErrorStream()
                            + TerminalHandler.getInputStream()
            );
        } catch (Exception exception) {
            errorHandler.generateError("Error while running the file\n"+exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    private File saveFile() {
        try {
            File file = FileHandler.createFile(filename);
            FileHandler.writeFile(filename, codePane.getText());
            System.out.println("Saved the file : " + filename);
            return file;
        } catch (FileNotFoundException ex) {
            errorHandler.generateError("Error while saving the file\n"+ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    // main function
    public static void main(String[] args) {
        new BytesEditor();
    }


}

