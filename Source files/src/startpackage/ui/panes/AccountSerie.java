package startpackage.ui.panes;

import startpackage.connection.Database;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class AccountSerie extends JPanel{
    private Database database;
    private ResultSet input;
    private Set<String> entries = new HashSet<>();
    private JComboBox dropDown = new JComboBox();
    private JTextArea text;
    private JButton button = new JButton("Search");

    public AccountSerie(Database database){
        this.database = database;
        text = new JTextArea();
        input = database.run("SELECT ACCOUNT.NAAM, AFLEVERING.Serie, AVG(BEKEKEN.Percentage) AS Percy FROM ACCOUNT JOIN PROFIEL ON ACCOUNT.Abonneenummer = PROFIEL.Abonneenummer JOIN BEKEKEN ON BEKEKEN.Abonneenummer = ACCOUNT.Abonneenummer JOIN AFLEVERING ON BEKEKEN.Gezien = AFLEVERING.Id GROUP BY ACCOUNT.NAAM, AFLEVERING.Serie");
        try {
            while (input.next()) {
                entries.add(input.getString("Naam") + ", " + input.getString("Serie"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        for (String entry: entries){
            dropDown.addItem(entry);
        }
        super.add(dropDown);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text.setText("");
                setText();
            }
        });
        super.add(text);
        super.add(button);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    }
    private void setText(){
        try {
            input.beforeFirst();
            while (input.next()){
                if((input.getString("Naam") +", " + input.getString("Serie")).equals(dropDown.getSelectedItem())){
                    text.append(input.getString("Percy"));
                }
            }
        }catch (SQLException e){
            text.setText("DATABASE NOT FOUND");
        }
    }
}
