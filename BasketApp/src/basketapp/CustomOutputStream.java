package basketapp;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;

/**
 *
 * @author xavier.guerbet
 */
public class CustomOutputStream extends OutputStream {
    private JTextArea textArea;
     
    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }
     
    @Override
    public void write(int b) throws IOException {
        // Redirects the output to the log area
        textArea.append(String.valueOf((char)b));
        // Scrolls automatically the log area
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}