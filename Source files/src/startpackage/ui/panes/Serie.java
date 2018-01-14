package startpackage.ui.panes;

import startpackage.connection.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Serie extends JPanel{
    private ResultSet input;
    private Database database;
    private JTextArea text;
    private JComboBox serie = new JComboBox();
    private JButton button = new JButton("Search");
    private Set<String> serienames = new HashSet<>();

    public Serie(Database database) {
        this.database = database;
        //Zorgt ervoor dat alle kiesbare opties in het menu komen.
        try {
            input = database.run("SELECT AFLEVERING.Serie, AVG(BEKEKEN.Percentage) FROM AFLEVERING JOIN BEKEKEN ON BEKEKEN.GEZIEN = AFLEVERING.ID GROUP BY AFLEVERING.SERIE");
            serie.addItem("Select the show");
            while(input.next()) {
                serienames.add(input.getString("Serie"));
            }
            for (String name: serienames){
                serie.addItem(name);
            }
        } catch (SQLException e){
        }

        //Zet alles in plaats voor de text
        this.text = new JTextArea();
        this.text.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n");
        text.setEditable(false);

        //Zet alles in motie voor de bovenstaande user interface
        BoxLayout layout = new BoxLayout(this,BoxLayout.Y_AXIS);
        this.setLayout(layout);
        serie.setSelectedIndex(0);
        super.add(serie);
        super.add(text);
        super.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text.setText("");
                setText();
            }
        });
    }

    private void setText(){
        int i = 0;
        try {
            input = database.run("SELECT AFLEVERING.Titel, Aflevering.Serie,Aflevering.Id, AVG(BEKEKEN.Percentage) AS Percentage FROM AFLEVERING JOIN BEKEKEN ON BEKEKEN.GEZIEN = AFLEVERING.ID GROUP BY AFLEVERING.Titel, Aflevering.Serie, Aflevering.Id");
            input.beforeFirst();
            while(input.next()){
                if (input.getString("Serie").equals(serie.getSelectedItem())) {
                    text.append("Mensen hebben gemiddeld " + input.getString("Percentage") + "% van " + input.getString("Titel") + " gekeken\n");
                }
            }
            if (text.getText().equals("") || text.getText().equals(null)){
                text.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n");
            }
        } catch (SQLException e){
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }
}
