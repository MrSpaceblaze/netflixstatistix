package startpackage.ui.panes;

import startpackage.connection.Database;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnkelProfiel extends JPanel{
    private JTextArea text;
    private Database database;
    private ResultSet input;

    public EnkelProfiel(Database database){
        text = new JTextArea();
        this.database = database;
        input = database.run("SELECT PROFIEL.Abonneenummer, ACCOUNT.Naam FROM ACCOUNT JOIN PROFIEL ON ACCOUNT.Abonneenummer = PROFIEL.Abonneenummer GROUP BY ACCOUNT.Naam, PROFIEL.Abonneenummer HAVING COUNT(PROFIEL.Abonneenummer) = 1");
        try{
            while (input.next()){
                text.append(" - " + input.getString("Abonneenummer"));
            }
        } catch (SQLException e){

        }
        if (text.getText().equals(null) || text.getText().equals("")){
            text.setText("None found");
        }
        super.add(text);
    }
}
