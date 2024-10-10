import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame {
    private JRadioButton thinCrust, regularCrust, deepDishCrust;
    private JComboBox<String> sizeBox;
    private JCheckBox topping1, topping2, topping3, topping4, topping5, topping6;
    private JEditorPane orderArea;
    private JButton orderButton, clearButton, quitButton;
    private ButtonGroup crustGroup;

    public PizzaGUIFrame() {
        setTitle("Pizza Order Form");
        setSize(800, 500);  // Adjusting the size to fit all components, increased width
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crust Panel
        JPanel crustPanel = new JPanel();
        crustPanel.setBorder(BorderFactory.createTitledBorder("Choose Crust"));
        thinCrust = new JRadioButton("Thin Crust");
        regularCrust = new JRadioButton("Regular Crust");
        deepDishCrust = new JRadioButton("Deep Dish");
        crustGroup = new ButtonGroup();
        crustGroup.add(thinCrust);
        crustGroup.add(regularCrust);
        crustGroup.add(deepDishCrust);
        crustPanel.add(thinCrust);
        crustPanel.add(regularCrust);
        crustPanel.add(deepDishCrust);

        // Size Panel
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(BorderFactory.createTitledBorder("Choose Size"));
        String[] sizes = {"Small ($8)", "Medium ($12)", "Large ($16)", "Super ($20)"};
        sizeBox = new JComboBox<>(sizes);
        sizePanel.add(sizeBox);

        // Toppings Panel - Ensuring visibility of checkboxes
        JPanel toppingsPanel = new JPanel();
        toppingsPanel.setBorder(BorderFactory.createTitledBorder("Choose Toppings"));
        topping1 = new JCheckBox("Pepperoni ($1)");
        topping2 = new JCheckBox("Mushrooms ($1)");
        topping3 = new JCheckBox("Onions ($1)");
        topping4 = new JCheckBox("Sausage ($1)");
        topping5 = new JCheckBox("Bacon ($1)");
        topping6 = new JCheckBox("Extra Cheese ($1)");
        toppingsPanel.setLayout(new GridLayout(3, 2, 5, 5));  // Adjusted grid layout for clear visibility
        toppingsPanel.add(topping1);
        toppingsPanel.add(topping2);
        toppingsPanel.add(topping3);
        toppingsPanel.add(topping4);
        toppingsPanel.add(topping5);
        toppingsPanel.add(topping6);

        // Order Area using JEditorPane to support HTML for bold text
        JPanel orderPanel = new JPanel(new BorderLayout());
        orderArea = new JEditorPane();
        orderArea.setContentType("text/html");  // Set content type to HTML
        orderArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderArea);
        scrollPane.setPreferredSize(new Dimension(350, 250));  // Set preferred size to make it visible
        orderPanel.add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");
        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        // Add panels to the frame
        setLayout(new BorderLayout());
        add(crustPanel, BorderLayout.NORTH);
        add(sizePanel, BorderLayout.WEST);
        add(toppingsPanel, BorderLayout.CENTER);  // Ensure toppings panel is visible
        add(orderPanel, BorderLayout.EAST);  // Make sure the order summary is visible
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Listeners
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayOrder();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    private void displayOrder() {
        String crust = "";
        if (thinCrust.isSelected()) crust = "Thin Crust";
        else if (regularCrust.isSelected()) crust = "Regular Crust";
        else if (deepDishCrust.isSelected()) crust = "Deep Dish";

        String size = (String) sizeBox.getSelectedItem();
        int baseCost = 8;
        if (size.equals("Medium ($12)")) baseCost = 12;
        else if (size.equals("Large ($16)")) baseCost = 16;
        else if (size.equals("Super ($20)")) baseCost = 20;

        StringBuilder toppings = new StringBuilder();
        int toppingsCost = 0;
        if (topping1.isSelected()) { toppings.append("Pepperoni ($1)<br>"); toppingsCost += 1; }
        if (topping2.isSelected()) { toppings.append("Mushrooms ($1)<br>"); toppingsCost += 1; }
        if (topping3.isSelected()) { toppings.append("Onions ($1)<br>"); toppingsCost += 1; }
        if (topping4.isSelected()) { toppings.append("Sausage ($1)<br>"); toppingsCost += 1; }
        if (topping5.isSelected()) { toppings.append("Bacon ($1)<br>"); toppingsCost += 1; }
        if (topping6.isSelected()) { toppings.append("Extra Cheese ($1)<br>"); toppingsCost += 1; }

        double subtotal = baseCost + toppingsCost;
        double tax = subtotal * 0.07;
        double total = subtotal + tax;

        // Using HTML to make "Subtotal", "Tax", and "Total" bold
        orderArea.setText("<html>" +
                "<p>=========================================</p>" +
                "<p>Type of Crust & Size: " + crust + " & " + size + "</p>" +
                "<p>Toppings:<br>" + toppings.toString() + "</p>" +
                "<p><b>Sub-total:</b> $" + String.format("%.2f", subtotal) + "</p>" +
                "<p><b>Tax:</b> $" + String.format("%.2f", tax) + "</p>" +
                "<p>-----------------------------------------</p>" +
                "<p><b>Total:</b> $" + String.format("%.2f", total) + "</p>" +
                "<p>=========================================</p>" +
                "</html>");
    }

    private void clearForm() {
        crustGroup.clearSelection();
        sizeBox.setSelectedIndex(0);
        topping1.setSelected(false);
        topping2.setSelected(false);
        topping3.setSelected(false);
        topping4.setSelected(false);
        topping5.setSelected(false);
        topping6.setSelected(false);
        orderArea.setText("");
    }
}
