import javax.swing.*;
import java.awt.*;

public class ErrorHandler {
    Frame frame;
    ErrorHandler(Frame frame){
        this.frame = frame;
    }
    void generateError(String message){
        JOptionPane.showMessageDialog(frame,message,"Error",JOptionPane.ERROR_MESSAGE,null);
    }
}
