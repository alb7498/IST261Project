package IST261Project.Frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class FilterPage extends JFrame {

    private JPanel filterPageJPanel;
    private JTextField minYearTF;
    private JTextField maxYearTF;
    private JComboBox<String> makeCB;
    private JTextField minPriceTF;
    private JTextField maxPriceTF;
    private JComboBox<String> colorCB;
    private JButton applybutton;
    private JButton clearFilterButton;

    public FilterPage() {
        setTitle("Filter Page");
        setContentPane(filterPageJPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        filterPageJPanel.setOpaque(true);
        filterPageJPanel.setBackground(Color.WHITE);

        makeCB.setOpaque(true);
        colorCB.setOpaque(true);

        makeCB.setLightWeightPopupEnabled(false);
        colorCB.setLightWeightPopupEnabled(false);

        addPlaceholder(minYearTF, "Minimum:");
        addPlaceholder(maxYearTF, "Maximum:");
        addPlaceholder(minPriceTF, "Minimum:");
        addPlaceholder(maxPriceTF, "Maximum:");

        pack();
        setLocationRelativeTo(null);
    }

    private void addPlaceholder(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().trim().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }

    public JPanel getFilterPageJPanel() {
        return filterPageJPanel;
    }

    public JTextField getMinYearTF() {
        return minYearTF;
    }

    public JTextField getMaxYearTF() {
        return maxYearTF;
    }

    public JComboBox<String> getMakeCB() {
        return makeCB;
    }

    public JTextField getMinPriceTF() {
        return minPriceTF;
    }

    public JTextField getMaxPriceTF() {
        return maxPriceTF;
    }

    public JComboBox<String> getColorCB() {
        return colorCB;
    }

    public JButton getApplybutton() {
        return applybutton;
    }

    public JButton getClearFilterButton() {
        return clearFilterButton;
    }
}