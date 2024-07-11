import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontManager {
    static void createFont(String pathname) throws IOException, FontFormatException {
        File fontpath = new File(pathname);
        Font font = Font.createFont(Font.PLAIN,fontpath);
    }

}
