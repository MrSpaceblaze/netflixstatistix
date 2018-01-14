package startpackage;

import startpackage.connection.Database;
import startpackage.ui.MainUI;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        MainUI ui = new MainUI("Netflix");
        SwingUtilities.invokeLater(ui);
    }
}
