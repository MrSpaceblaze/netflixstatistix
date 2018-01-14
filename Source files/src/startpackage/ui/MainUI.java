package startpackage.ui;

import startpackage.connection.Database;
import startpackage.ui.panes.*;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class MainUI implements Runnable{

    private JFrame frame;
    private Serie tabSerie;
    private Database database;
    private AccountSerie tab2;
    private LangsteFilm film;
    private EnkelProfiel profiel;
    private Movies movies;
    private FullMovies fullMovies;

    public MainUI(String database){
        this.database = new Database(database);
        this.tabSerie = new Serie(this.database);
        this.tab2 = new AccountSerie(this.database);
        this.profiel = new EnkelProfiel(this.database);
        this.film = new LangsteFilm(this.database);
        this.movies = new Movies(this.database);
        this.fullMovies = new FullMovies(this.database);
    }

    public ResultSet runDatabase(String SQL){
        return database.run(SQL);
    }

    // Zorgt voor de basisinformatie die de frame nodig heeft
    @Override
    public void run() {
        frame = new JFrame("Netflix Statistix");
        frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    // Geeft aan wat er in de frame te zien is.
    private void createComponents(Container container){
        BorderLayout layout = new BorderLayout();
        container.setLayout(layout);
        JPanel tabs = new JPanel();
        JPanel botText = new JPanel();
        JLabel text = new JLabel("NetFlix Statistics        Informatica Jaar 1 23IVT4 Fer Schmidt, Banujan Paramasamy");
        botText.add(text);
        JTabbedPane tabbedPanes = new JTabbedPane(SwingConstants.LEFT);
        tabbedPanes.setSize(frame.getSize());
        tabs.add(tabbedPanes);
        tabbedPanes.setTabPlacement(SwingConstants.LEFT);

        tabbedPanes.addTab("Serie", tabSerie);
        tabbedPanes.addTab("Account + Serie",tab2);
        tabbedPanes.addTab("Langste film <16 jaar",film);
        tabbedPanes.addTab("Accounts met maar één profiel", profiel);
        tabbedPanes.addTab("Films per account",movies);
        tabbedPanes.addTab("Films die volledig gezien zijn",fullMovies);

        container.add(tabs, BorderLayout.LINE_START);
        container.add(botText, BorderLayout.SOUTH);
    }

    public JFrame getFrame() {
        return frame;
    }
}
