package IST261Project.Frontend;

import javax.swing.*;

public class AppView {
    private JPanel MainFrame;
    private JPanel MainPagePanel;
    private JLabel DealershipTitle;
    private JButton buttonRemove;
    private JButton buttonAdd;
    private JButton buttonPurchase;
    private JButton buttonSearch;
    private JButton buttonFilter;
    private JButton buttonResetView;

    public JPanel getMainFrame() {
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

    public JButton getButtonPurchase() {
        return buttonPurchase;
    }

    public JButton getButtonSearch() {
        return buttonSearch;
    }

    public JButton getButtonFilter() {
        return buttonFilter;
    }

    public JButton getButtonResetView() {
        return buttonResetView;
    }
}
