import java.io.*;

public class TerminalHandler {
    static Process process, process1;
    static StringBuilder inputStream;
    static StringBuilder errorStream;

    private static void printLogs() {
        try {
            if (!inputStream.toString().equals("\n")) System.out.print(inputStream);
        } catch (NullPointerException nullPointerException) {
            System.out.println("There is nothing in input stream");
        }
        try {
            if (!inputStream.toString().equals("\n")) System.out.print(errorStream);
        } catch (NullPointerException nullPointerException) {
            System.out.println("There is nothing in output stream");
        }
    }

    public static String getInputStream() {
        if (inputStream != null) return inputStream.toString();
        return "";
    }

    public static String getErrorStream() {
        if (errorStream != null) return errorStream.toString();
        return "";
    }
//    public static void executeCmd(String cmd) throws IOException, InterruptedException {
//        String currentPath = new File("").getAbsolutePath();
//        ProcessBuilder processBuilder = new ProcessBuilder();
//        processBuilder.command("cmd", "/c", cmd).directory(new File(currentPath));
//        process = processBuilder.start();
//        process.waitFor();
//        if (process.exitValue() == 0) {
//            System.out.println("command executed successfully!");
//        } else {
//            System.out.println("compilation error!");
//        }
//    }

    public static int executeJava(File filepath) throws IOException, InterruptedException {
        String filename = filepath.getName().replace(".java", "");
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("cmd", "/c", "javac", filepath.getAbsolutePath());
        process = processBuilder.start();
        process.waitFor();
        if (process.exitValue() == 0) {
            ProcessBuilder processBuilder1 = new ProcessBuilder();
            processBuilder1.command("cmd", "/c", "java", filename);
            process1 = processBuilder1.start();
            storeResults(process1);
            return 1; // if compiled then it must be successful
        } else {
            storeResults(process);
            System.out.println("compilation error!");
            return 0; // failure
        }
    }

    public static void storeResults(Process process) throws IOException {
        inputStream = new StringBuilder();
        errorStream = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            inputStream.append(line).append("\n");
        }
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String line2 = "";
        while ((line2 = reader2.readLine()) != null) {
            errorStream.append(line2).append("\n");
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        executeJava(new File("BytesEditorTemp.java"));
    }
}
