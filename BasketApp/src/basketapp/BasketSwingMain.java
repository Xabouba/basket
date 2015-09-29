package basketapp;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;

public class BasketSwingMain extends JPanel implements ActionListener {
    static private final String newline = "\n";
    static private Basket basket;
    JButton displayBasketButton, displayUnitCostButton, addFruitButton, removeFruitButton, resetButton, openButton;
    JTextArea log;
    JFileChooser fc;
    
    public BasketSwingMain() {
        super(new BorderLayout());
        basket = new Basket();
        
        //Define the log box containing all information to display
        log = new JTextArea(20,20);
        PrintStream printStream = new PrintStream(new CustomOutputStream(log));
        System.setOut(printStream);
        System.setErr(printStream);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(log);

        //Display Basket
        displayBasketButton = new JButton("Display basket");
        displayBasketButton.addActionListener(this);
        //Display Unit cost
        displayUnitCostButton = new JButton("Display fruit cost");
        displayUnitCostButton.addActionListener(this);
        //Add fruit 
        addFruitButton = new JButton("Add fruit");
        addFruitButton.addActionListener(this);
        //Remove fruit
        removeFruitButton = new JButton("Remove fruit");
        removeFruitButton.addActionListener(this);
        //Reset basket
        resetButton = new JButton("Reset basket");
        resetButton.addActionListener(this);
        //Create a file chooser
        fc = new JFileChooser();
        openButton = new JButton("Import from CSV file");
        openButton.addActionListener(this);

        //Add butotn to the panel created
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(displayBasketButton);
        buttonPanel.add(displayUnitCostButton);
        buttonPanel.add(addFruitButton);
        buttonPanel.add(removeFruitButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(openButton);

        //Add the buttons and the log to this panel.
        add(buttonPanel, BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Contains all actions available
     * 
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {  
        log.append(newline);
        //Handle open button action.
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(BasketSwingMain.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                if (file.getName().substring(file.getName().lastIndexOf(".") + 1).equals("csv")){
                    log.append("Opening: " + file.getName() + "." + newline);                
                    basket.readCSVToBasket(file.getPath());
                } else {
                    log.append("Try to open a non CSV file");
                }
            } else {
                log.append("Open command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());

        //Handle display basket button action.
        } else if (e.getSource() == displayBasketButton) {
            basket.displayBasket();
        } else if (e.getSource() == displayUnitCostButton) {
            Fruit.displayUnitCost();
        } else if (e.getSource() == addFruitButton) {
            createAndShowChangeBasket(true);
        } else if (e.getSource() == removeFruitButton) {
            createAndShowChangeBasket(false);
        } else if (e.getSource() == resetButton) {
            log.append("The basket is now empty");
            basket = new Basket();
        }
    }

    /**
     * Show pop up for add/remove fruits from basket
     * First, choose the fruit
     * Second, enter number to add/remove if possible (input must be an integer)
     * 
     * @param isAdd 
     */
    public static void createAndShowChangeBasket(boolean isAdd){    
        int number = 0;
        String resultStr = null;

        //First, select the fruit among the list
        JPanel panel = new JPanel();
        panel.add(new JLabel("Please choose a fruit:"));
        //Create a Combo Box containing all the available fruits
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (Fruit fruit: Fruit.values()){
            model.addElement(fruit.name());
        }
        JComboBox comboBox = new JComboBox(model);
        panel.add(comboBox);
        
        int result = JOptionPane.showConfirmDialog(null, panel, "Fruit", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (result) {
            case JOptionPane.OK_OPTION:
                resultStr = (String) comboBox.getSelectedItem();
                break;
        }
        
        //Second, get the number of fruits to add/remove (must be an integer)
        try {
            number = Integer.parseInt(JOptionPane.showInputDialog("How many fruit?", null));
        } catch (NumberFormatException e){
        }

        //
        if (isAdd){
            basket.addFruit(Fruit.valueOf(resultStr), number);
        } else {
            basket.removeFruit(Fruit.valueOf(resultStr), number);
        }
    }
    
    /**
     * Create the 2 frames
     * The main application creates a GUI containing all buttons and will exit on close
     */
    private static void createAndShowGUI() {
        JFrame jframe = new JFrame("Basket Application");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(new BasketSwingMain());
        jframe.pack();
        jframe.setVisible(true);
        
        JFrame jframeFruit = new JFrame("Change Basket");
        jframeFruit.pack();
        jframeFruit.setVisible(false);
    }

    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }
}