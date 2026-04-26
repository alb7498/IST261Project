package IST261Project.Frontend;

import javax.swing.*;

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