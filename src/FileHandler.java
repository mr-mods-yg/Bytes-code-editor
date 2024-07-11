import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHandler {
    static File getFile(String filepath) throws FileNotFoundException {
        if (filepath.isEmpty()){
            throw new FileNotFoundException("File not found");
        }
        File file = new File(filepath);
        if (file.isFile()) {
            return file;
        }
        throw new FileNotFoundException("File not found");
    }

    static File createFile(String filepath) throws FileNotFoundException {
        if (filepath.isEmpty()) throw new FileNotFoundException("File not found : " + filepath);
        try {
            File file = new File(filepath);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
            return file;
        } catch (Exception e) {
            System.out.println("An error has occurred.");
            throw new RuntimeException(e);
        }

    }

    static String readFile(String filepath) throws IOException, FileNotFoundException{
        StringBuilder content = new StringBuilder();
        try {
            File file = FileHandler.getFile(filepath);
            Scanner Reader = new Scanner(file);
            while (Reader.hasNextLine()) {
                content.append(Reader.nextLine());
                // if there is next line then add new file character
                if (Reader.hasNextLine()) content.append("\n");
            }
            Reader.close();
            return content.toString();
        } catch (IOException e) {
            throw new FileNotFoundException("File not found");
        }catch (Exception exception){
            System.out.println("some error has been occurred!");
            return null;
        }
    }

    static int writeFile(String filepath, String content) {
        try {
            File file = FileHandler.getFile(filepath);
            FileWriter writer
                    = new FileWriter(file);
            writer.write(content);
            writer.close();
            System.out.println("Successfully written.");
            return 1;
        } catch (IOException e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
            return 0;
        }
    }

    public static void main(String[] args) {

    }
}
