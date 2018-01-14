package startpackage.ui.panes;

import startpackage.connection.Database;
import sun.swing.SwingAccessor;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LangsteFilm extends JPanel{
    private ResultSet input;
    private Database database;
    private JLabel label;
    public LangsteFilm(Database database){
        this.database = database;
        label = new JLabel("");
        input = database.run("SELECT DISTINCT TOP 1 Titel, MAX(Tijdsduur) AS Langste FROM FILM WHERE Leefdtijdsindicatie != '16 jaar en ouder' GROUP BY Titel");
        try{
            while (input.next()){
                label.setText(input.getString("Titel")+" in " + input.getString("Langste"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        super.add(label);
    }
}
