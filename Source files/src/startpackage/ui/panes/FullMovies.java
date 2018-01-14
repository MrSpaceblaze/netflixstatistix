package startpackage.ui.panes;

import startpackage.connection.Database;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;


public class FullMovies extends JPanel{
    private ResultSet input;
    private Database database;
    private JTextArea text;
    private JComboBox serie = new JComboBox();
    private JButton button = new JButton("Search");
    private Set<String> serienames = new HashSet<>();

    public FullMovies(Database database){
    this.database = database;
    text = new JTextArea();

        try{
        input = database.run("SELECT ACCOUNT.Naam, FILM.Titel FROM ACCOUNT JOIN PROFIEL ON ACCOUNT.Abonneenummer = PROFIEL.Abonneenummer JOIN BEKEKEN ON PROFIEL.Profielnaam = BEKEKEN.Profielnaam JOIN FILM ON BEKEKEN.Gezien = FILM.Id WHERE Bekeken.Percentage = 100");
        while (input.next()){
            serienames.add(input.getString("Titel"));
        }
        for(String string: serienames){
            serie.addItem(string);
        }
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text.setText("");
                setText();
            }
        });
        text.setText("");
        super.add(serie);
        super.add(text);
        super.add(button);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    } catch (SQLException e){
    }
}

    private void setText(){
        try {
            input.first();
            while (input.next()){
                if(input.getString("Titel").equals(serie.getSelectedItem())){
                    text.append(input.getString("Naam") + "\n");
                }
            }
            if (text.getText().equals("")){
                text.setText("None found");
            }
        }catch (SQLException e){
            text.setText("DATABASE NOT FOUND");
        }
    }
}
