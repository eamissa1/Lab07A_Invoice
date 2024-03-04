import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InvoiceGUI extends JFrame
{
    private Invoice invoice = new Invoice();
    private JTextArea displayArea;
    private JTextField customerAddressInput,productNameInput, unitPriceInput, quantityInput;
    private boolean addressProcessed = false;
    private boolean headerAdded = false;

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            InvoiceGUI app = new InvoiceGUI();
            app.setVisible(true);
        });
    }

    public InvoiceGUI()
    {
        createUI();
    }

    private void createUI()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setTitle("Invoice Application");

        setLayout(new BorderLayout());

        // Inputs Panel
        JPanel inputsPanel = new JPanel();
        inputsPanel.setLayout(new GridLayout(0, 2));
        add(inputsPanel, BorderLayout.NORTH);

        customerAddressInput = new JTextField();
        productNameInput = new JTextField();
        unitPriceInput = new JTextField();
        quantityInput = new JTextField();

        inputsPanel.add(new JLabel("Customer Address:"));
        inputsPanel.add(customerAddressInput);
        inputsPanel.add(new JLabel("Product Name:"));
        inputsPanel.add(productNameInput);
        inputsPanel.add(new JLabel("Unit Price:"));
        inputsPanel.add(unitPriceInput);
        inputsPanel.add(new JLabel("Quantity:"));
        inputsPanel.add(quantityInput);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton("Add Line Item");
        JButton totalButton = new JButton("Calculate Total");
        buttonsPanel.add(addButton);
        buttonsPanel.add(totalButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Display Area
        displayArea = new JTextArea();
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Action Listeners
        addButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                processAddressInput();
                addLineItem();
            }
        });

        totalButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                calculateTotal();
            }
        });
    }

    private void processAddressInput()
    {
        // Check if the address has already been processed
        if (!addressProcessed)
        {
            String fullAddress = customerAddressInput.getText();
            String[] addressParts = fullAddress.split(",");

            if (addressParts.length == 5)
            { // Ensure there are exactly 5 parts
                String companyName = addressParts[0].trim();
                String streetName = addressParts[1].trim();
                String city = addressParts[2].trim();
                String state = addressParts[3].trim();
                String zip = addressParts[4].trim();

                // Format the address for display
                displayArea.append("                            INVOICE\n\n");
                displayArea.append(companyName + "\n");
                displayArea.append(streetName + "\n");
                displayArea.append(city + ", " + state + " " + zip);
                // Display the formatted address, for example, in a JTextArea
                displayArea.append("\n\n");

                // Set the flag to true to prevent re-processing
                addressProcessed = true;
            } else {
                // Handle error, maybe the input format was incorrect
                displayArea.append("Invalid address format. Please enter as: Company Name, Street Name, City, State, ZIP\n");
            }
        }
    }

    private void addLineItem()
    {
        String name = productNameInput.getText();
        double price = Double.parseDouble(unitPriceInput.getText());
        int quantity = Integer.parseInt(quantityInput.getText());

        Product product = new Product(name, price);
        LineItem item = new LineItem(product, quantity);

        invoice.addLineItem(item);

        if (!headerAdded)
        {
            // Add the separator and headers only once
            displayArea.append("=======================================================\n\n");
            displayArea.append(String.format("%-35s %18s %18s %18s\n", "Item", "Qty", "Price", "Total"));
            displayArea.append("\n");
            headerAdded = true; // Ensure this section is not repeated
        }

        String lineItemDetails = String.format("%-35s %15d %18s %19s\n", name, quantity, String.format("$%.2f", price), String.format("$%.2f", item.getTotalForLineItem()));
        displayArea.append(lineItemDetails + "\n");


        productNameInput.setText("");
        unitPriceInput.setText("");
        quantityInput.setText("");
    }


    private void calculateTotal()
    {
        displayArea.append("=======================================================\n\n");
        displayArea.append("\nAMOUNT DUE:   $" + String.format("%.2f", invoice.getTotalAmountDue()) + "\n");
        customerAddressInput.setText("");
    }
}
