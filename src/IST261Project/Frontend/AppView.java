package IST261Project.Frontend;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class AppView {
    private JPanel MainFrame;
    private JPanel MainPagePanel;
    private JLabel DealershipTitle;
    private JButton buttonRemove;
    private JButton buttonAdd;

    private JButton buttonFilter;
    private JButton buttonResetView;
    private JPanel topPanel;
    private JPanel inventoryPanel;
    private JScrollPane mainScrollPanel;
    private JPanel PurchasePage;

    public AppView() {
        styleActionButtons();
    }

    private void styleActionButtons() {
        MainPagePanel.setBackground(new Color(245, 247, 250));
        topPanel.setBackground(new Color(245, 247, 250));

        styleButton(buttonRemove, "Remove Car", new Color(231, 76, 60), Color.WHITE);
        styleButton(buttonAdd, "Add Car", new Color(46, 125, 50), Color.WHITE);
        styleButton(buttonFilter, "Filter", new Color(33, 150, 243), Color.WHITE);
        styleButton(buttonResetView, "Clear Filter", new Color(238, 242, 247), new Color(45, 55, 72));
    }

    private void styleButton(JButton button, String text, Color background, Color foreground) {
        button.setText(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        button.setForeground(foreground);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(8, 14, 8, 14));
        button.setBorderPainted(true);
        button.setBorder(new CompoundBorder(
                new LineBorder(Color.BLACK, 1),
                new EmptyBorder(7, 13, 7, 13)
        ));
        button.setPreferredSize(new Dimension(112, 34));
    }

    public JPanel getMainPanel() {
        return MainFrame;
    }

    public JPanel getMainPagePanel() {
        return MainPagePanel;
    }

    public JLabel getDealershipTitle() {
        return DealershipTitle;
    }

    public JButton getButtonRemove() {
        return buttonRemove;
    }

    public JButton getButtonAdd() {
        return buttonAdd;
    }


    public JButton getButtonFilter() {
        return buttonFilter;
    }

    public JButton getButtonResetView() {
        return buttonResetView;
    }

    public JPanel getTopPanel() {
        return topPanel;
    }

    public JPanel getInventoryPanel() {
        return inventoryPanel;
    }

    public JPanel getPurchasePage() {
        return PurchasePage;
    }

    public JPanel getMainFrame() {
        return MainFrame;
    }

    public JScrollPane getMainScrollPanel() {
        return mainScrollPanel;
    }
}
