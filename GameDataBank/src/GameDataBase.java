import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;

/**
 * NOCH EXISTENTE PROBLEME:
 * Wenn ich vom Impressum auf einen anderen Button klicken mˆchte, dann sind andere Textfelder etc. nicht mehr benutzbar. Ich vermute das
 * Problem beim Impresum
 *
 * ‹berlegungen wie man das Spiel verbessern kann:
 *
 * Wenn auf ein Spiel geklickt wird, dann sollen wietere Informationen in einem neuen Fenster geˆffnet werden.
 * In dem Fenster wo die weiteren Informationen ausgegeben werden, sollen mehrere Tabs sein (Beschreibeung (titel, release etc), Bilder,
 * Bewertungen)
 *
 * Suchfeld erschaffen
 *
 * bereits bestehende Datens‰tze kˆnnen ¸berarbeitet werden
 *
 * Publisher und Entwicklerstudio hinzufuegen bei Add Game
 */

public class GameDataBase extends JFrame implements ActionListener {
    GameCollection game = new GameCollection();

    JPanel top, left, center, beschreibung, bewertung, bilder;
    JLabel IGameNummer, IGameTitle, IGameGenre, IGameRelease, IGamePlattform, IGameFsk, LAusgabe, LDelete, LInformation, LSuchen,
            LBearbeiten;
    JTextField benutzername, addGameNummer, addGameTitle, addGameRelease, addGamePlattform, addGameFsk, TDelete, TInformation, TSuchen,
            TBearbeiten;
    JButton anmelden, ausgabe, add, impressum, exit, GameAddToList, delete, loeschen, information, zurueck, weiter, BInforamtion,
            bearbeiten, bearbeitungsNummer, edit;
    JTabbedPane pane;
    JPasswordField passwort;
    JComboBox addGameGenre;
    JCheckBox komplett;
    JTable table;

    private final XMLReaderWriter readerWriter = new XMLReaderWriter();

    private final File file = new File("Users/thimonj/Programmieren/Java/Eclipse/Ausbildung/Betrieb/GameDataBank/game.xml");

    public GameDataBase(final String title) {
        super(title);
        readXML();

        /**
         * Generierung des Inhalts vom oberen Panel
         */

        ausgabe = new JButton("Ausgabe der Games");
        add = new JButton("Add Game");

        impressum = new JButton("Impressum");
        final JLabel LBenutzername = new JLabel("Benutzername");
        benutzername = new JTextField(10);
        final JLabel LPasswort = new JLabel("Passwort");
        passwort = new JPasswordField(10);
        anmelden = new JButton("Anmelden");
        exit = new JButton("Exit");
        delete = new JButton("Delete");
        information = new JButton("Information");
        bearbeiten = new JButton("Bearbeiten");

        /**
         * ActionCommand wurden den Buttons auf der linken Seite hinzugefuegt
         */

        ausgabe.setActionCommand("Ausgabe der Games");
        add.setActionCommand("GameAdd");
        impressum.setActionCommand("Impressum");
        exit.setActionCommand("Exit");
        anmelden.setActionCommand("Anmelden");
        delete.setActionCommand("Delete");
        information.setActionCommand("Information");
        bearbeiten.setActionCommand("Bearbeiten");

        /**
         * Alle Buttons bekommen einen ActionListener
         */

        ausgabe.addActionListener(this);
        add.addActionListener(this);
        impressum.addActionListener(this);
        exit.addActionListener(this);
        anmelden.addActionListener(this);
        benutzername.addActionListener(this);
        passwort.addActionListener(this);
        delete.addActionListener(this);
        information.addActionListener(this);
        bearbeiten.addActionListener(this);

        /**
         * Dem oberen Panel wurden die Labels und Buttons hinzugefuegt
         */

        top = new JPanel();

        top.add(LBenutzername);
        top.add(benutzername);
        top.add(LPasswort);
        top.add(passwort);
        top.add(anmelden);

        /**
         * Linkes Panel bekommt die Buttons
         */

        left = new JPanel();

        left.setLayout(new GridLayout(5, 2));
        left.add(add);
        left.add(delete);
        left.add(ausgabe);
        left.add(information);

        left.add(bearbeiten);
        left.add(impressum);
        left.add(exit);

        /**
         * Buttons auf dem linken Panel wurden zum Start des Programmes deaktiviert
         */

        add.setEnabled(false);
        delete.setEnabled(false);
        ausgabe.setEnabled(false);
        information.setEnabled(false);
        bearbeiten.setEnabled(false);
        impressum.setEnabled(false);
        exit.setEnabled(false);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(left, BorderLayout.WEST);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getActionCommand().equals("Anmelden")) {

            String VBenutzername;
            VBenutzername = benutzername.getText();

            /**
             * Wenn das Passwort nicht Milch ist, dann soll eine Fehlermeldung erfolgen in Form eines Dialoges
             */

            if (!passwort.equals("Milch") && !VBenutzername.equals("jthimon")) {
                final JDialog FAnmeldung = new JDialog();
                final JLabel FFehler = new JLabel("Das Passwort oder der Benutzername ist falsch");
                FAnmeldung.setTitle("Fehlermeldung");
                FAnmeldung.setSize(300, 100);
                FAnmeldung.add(FFehler);
                FAnmeldung.setVisible(true);
                FAnmeldung.setLocation(700, 500);

            } else {

                /**
                 * Die Buttons auf der linken Seite sollen aktiviert werden, wenn das eingegebene Passwort richtig ist
                 */
                add.setEnabled(true);
                delete.setEnabled(true);
                ausgabe.setEnabled(true);
                information.setEnabled(true);

                bearbeiten.setEnabled(true);
                impressum.setEnabled(true);
                exit.setEnabled(true);
                validate();
            }
        }
        /**
         * Labels und Textfelder mit der groesse von 15 Zeichen werden hier generiert ebenso ein Button
         * Hier werden die Eingaben der Array List hinzugefuegt
         */
        else if (e.getActionCommand().equals("GameAdd")) {

            clearCenter();

            final String GenreTable[] =
                    {"Action", "Abenteuer", "Beat them up", "Brawler", "Horror", "Jump and Run", "MMORPG", "MOBA", "RPG", "Rogue-like",
                            "Shooter",
                            "Science-Fiction", "Strategie", "Sport", "Wimmelbild"};

            IGameNummer = new JLabel("Bitte geben Sie die Nummer des Spieles ein");
            addGameNummer = new JTextField(15);

            IGameTitle = new JLabel("Bitte geben Sie den Titel des Spieles ein, welches hinzugefuegt werden soll");
            addGameTitle = new JTextField(15);

            IGameRelease = new JLabel("Bitte geben Sie das Erscheinungsdatum des Spieles ein");
            addGameRelease = new JTextField(15);

            IGameGenre = new JLabel("Bitte geben Sie das Genre des Spiels ein");
            addGameGenre = new JComboBox(GenreTable);

            IGamePlattform = new JLabel("Bitte geben Sie die Plattform des Spieles ein");
            addGamePlattform = new JTextField(15);

            IGameFsk = new JLabel("Bitte geben Sie die Altersbeschraenkung des Spieles ein");
            addGameFsk = new JTextField(5);

            komplett = new JCheckBox("Platin Trophy erreicht?");

            GameAddToList = new JButton("Spiel der Liste adden");

            addGameNummer.setActionCommand("addGameNumber");
            addGameTitle.setActionCommand("addGameTitle");
            addGameRelease.setActionCommand("addGameRelease");
            addGamePlattform.setActionCommand("addGamePlattform");
            addGameGenre.setActionCommand("addGameGenre");
            addGameFsk.setActionCommand("addGameFsk");
            komplett.setActionCommand("Platin Trophy erreicht?");
            GameAddToList.setActionCommand("Spiel der Liste adden");

            addGameNummer.addActionListener(this);
            addGameTitle.addActionListener(this);
            addGameRelease.addActionListener(this);
            addGameGenre.addActionListener(this);
            addGamePlattform.addActionListener(this);
            addGameFsk.addActionListener(this);
            komplett.addActionListener(this);
            GameAddToList.addActionListener(this);

            /**
             * Hinzufuegung aller Textfelder, Labels und des Buttons
             */

            center = new JPanel();

            center.setLayout(new GridLayout(8, 2));

            center.add(IGameNummer);
            center.add(addGameNummer);
            center.add(IGameTitle);
            center.add(addGameTitle);
            center.add(IGameRelease);
            center.add(addGameRelease);
            center.add(IGameFsk);
            center.add(addGameFsk);
            center.add(IGameGenre);
            center.add(addGameGenre);
            center.add(IGamePlattform);
            center.add(addGamePlattform);
            center.add(komplett);
            center.add(GameAddToList);

            getContentPane().add(center, BorderLayout.CENTER);
            validate();

            /**
             * Ueberpruefung, ob das Spiel mit der Spielenummer (Schluessel) bereits in der Datenbank existiert
             */
        } else if (e.getActionCommand().equals("Spiel der Liste adden")) {
            String Nummer;
            Nummer = addGameNummer.getText();
            boolean contains = false;
            for (int i = 0; i < game.getGameCollection().size(); i++) {
                if (game.getGameCollection().get(i).getNummer().equals(Nummer)) {
                    contains = true;
                    break;
                }
            }

            /**
             * Hier ist die eigentliche Ueberpruefung ob das Spiel bereits in der Datenbank existiert
             */
            if (!contains) {
                final Game game1 =
                        new Game(addGameNummer.getText(), addGameTitle.getText(), addGameRelease.getText(),
                                addGameGenre.getSelectedItem().toString(),
                                addGamePlattform.getText(),
                                addGameFsk.getText(), komplett.isSelected());
                game.getGameCollection().add(game1);

                writeXML();
                /**
                 * Dialog soll erzeugt werden, wenn die Nummer des Spieles bereits existiert
                 */
            } else {
                final JDialog fehler = new JDialog();
                fehler.setTitle("Fehler");
                fehler.setSize(650, 100);
                final JLabel LFehler = new JLabel(
                        "Diese Nummer existiert bereits in der Datenbank. Bitte geben Sie die Daten neu ein mit einer anderen Nummer!");
                fehler.add(LFehler);
                fehler.setVisible(true);
                fehler.setLocation(550, 500);
            }
        }

        /**
         * Hier sind die Befehle die ein Spiel aus der Liste loeschen
         */
        else if (e.getActionCommand().equals("Delete")) {

            clearCenter();

            loeschen = new JButton("Spiel loeschen");
            center = new JPanel();

            LDelete = new JLabel("Bitte geben Sie die Nummer des Spieles ein, welches geloescht werden soll");
            TDelete = new JTextField(15);
            TDelete.addActionListener(this);

            loeschen.setActionCommand("loeschen");
            loeschen.addActionListener(this);

            center.add(LDelete);
            center.add(TDelete);
            center.add(loeschen);

            getContentPane().add(center, BorderLayout.CENTER);

            validate();

        }
        /**
         * Datensatz wird geloescht
         */

        else if (e.getActionCommand().equals("loeschen")) {
            clearCenter();

            String Nummer;
            Nummer = TDelete.getText();

            for (int i = 0; i < game.getGameCollection().size(); i++) {
                if (game.getGameCollection().get(i).getNummer().equals(Nummer)) {
                    game.getGameCollection().remove(i);

                }
            }
            writeXML();
            /**
             * Ausgabe der Spiele in Form einer Tabelle
             */
        } else if (e.getActionCommand().equals("Ausgabe der Games")) {
            clearCenter();
            readXML();

            final JTable ATable;
            DefaultTableModel atabellenmodellGruppeA;

            final Object[] aSpalten =
                    {"Gamenummer", "Titel", "Erscheinungsdatum", "Aktersfreigabe (Angabe in FSK)", "Genre", "Plattform", "Komplett?"};

            final Object[][] AGruppeA = new Object[game.getGameCollection().size()][7];
            for (int i = 0; i < game.getGameCollection().size(); i++) {

                AGruppeA[i][0] = game.getGameCollection().get(i).getNummer();
                AGruppeA[i][1] = game.getGameCollection().get(i).getGameTitle();
                AGruppeA[i][2] = game.getGameCollection().get(i).getRealease();
                AGruppeA[i][3] = game.getGameCollection().get(i).getFsk();
                AGruppeA[i][4] = game.getGameCollection().get(i).getGenre();
                AGruppeA[i][5] = game.getGameCollection().get(i).getPlattform();
                AGruppeA[i][6] = game.getGameCollection().get(i).getKomplett();
            }

            atabellenmodellGruppeA = new DefaultTableModel(AGruppeA, aSpalten);
            ATable = new JTable(atabellenmodellGruppeA) {
                private static final long serialVersionUID = 1L;

                @Override
                public Class getColumnClass(final int column) {
                    switch (column) {
                        case 0:
                            return String.class;
                        case 1:
                            return String.class;
                        case 2:
                            return String.class;
                        case 3:
                            return String.class;
                        case 4:
                            return String.class;
                        case 5:
                            return String.class;
                        default:
                            return Boolean.class;
                    }
                }
            };
            final JScrollPane scrollPane = new JScrollPane(ATable);

            ATable.setAutoCreateRowSorter(true);
            ATable.setEnabled(false);

            center = new JPanel();
            center.add(scrollPane);
            center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

            getContentPane().add(center, BorderLayout.CENTER);
            validate();

            /**
             * Programm wird beendet
             */
        } else if (e.getActionCommand().equals("Exit")) {
            System.exit(0);
        }

        /**
         * neues Fenster Informationen.
         */

        else if (e.getActionCommand().equals("Information")) {
            clearCenter();

            center = new JPanel();

            weiter = new JButton("Weiter");

            LInformation = new JLabel("Bitte geben Sie die Nummer des Spieles ein.");
            TInformation = new JTextField(15);
            TInformation.addActionListener(this);

            weiter.setActionCommand("Weiter");
            weiter.addActionListener(this);

            center.add(LInformation);
            center.add(TInformation);
            center.add(weiter);
            getContentPane().add(center, BorderLayout.CENTER);
            validate();
        }
        /**
         * JTabbedPane wird geˆffnet sobald die Daten eingetragen wurden und auf weiter geklickt wurde
         */

        else if (e.getActionCommand().equals("Weiter")) {
            clearCenter();

            String Nummer;
            Nummer = TInformation.getText();

            for (int i = 0; i < game.getGameCollection().size(); i++) {
                if (game.getGameCollection().get(i).getNummer().equals(Nummer)) {

                    pane = new JTabbedPane(SwingConstants.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

                    center = new JPanel();
                    zurueck = new JButton("zurueck");
                    zurueck.setActionCommand("zurueck");
                    zurueck.addActionListener(this);

                    pane.addTab("Beschreibung", new JTextArea());
                    pane.addTab("Bewertungen", new JLabel("Moinsen"));
                    pane.addTab("Bilder", zurueck);
                    center.add(pane);

                    getContentPane().add(center, BorderLayout.CENTER);

                }

            }

            validate();
        }

       

        /**
         * Soll zum Informationsbildschirm zurueck kehren
         */

        else if (e.getActionCommand().equals("Zurueck")) {
            clearCenter();

            center = new JPanel();

            weiter = new JButton("Weiter");

            LInformation = new JLabel("Bitte geben Sie die Nummer des Spieles ein");
            TInformation = new JTextField(15);
            TInformation.addActionListener(this);

            weiter.setActionCommand("Weiter");
            weiter.addActionListener(this);

            center.add(LInformation);
            center.add(TInformation);

            center.add(weiter);
            getContentPane().add(center, BorderLayout.CENTER);
            validate();

        }

        /**
         * Hier wird das Fenster geoeffnet, um einen bestehenden Datensatz zu bearbeiten
         */

        else if (e.getActionCommand().equals("Bearbeiten")) {
            clearCenter();

            center = new JPanel();

            LBearbeiten = new JLabel("F¸r Bearbeitung: Nummer eingeben");
            TBearbeiten = new JTextField(15);
            TBearbeiten.addActionListener(this);

            edit = new JButton("edit");
            edit.setActionCommand("edit");
            edit.addActionListener(this);

            center.add(LBearbeiten);
            center.add(TBearbeiten);
            center.add(edit);

            getContentPane().add(center, BorderLayout.CENTER);

            validate();

        }

        /**
         * Hier wird der Datensatz editiert
         */

        else if (e.getActionCommand().equals("edit")) {

        }

        /**
         * Hier soll ein Suchfeld erschaffen werden.
         */

        /**
         * Ausgabe des Impressums in Form eine Tabelle
         */
        else if (e.getActionCommand().equals("Impressum")) {

            clearCenter();
            center = new JPanel();
            final JTable iTabelle;

            //Spalten anlegen
            final String spalten[] = {"Bezeichnung", "Information"};

            DefaultTableModel tabellenmodellGruppeA;

            /**
             * Inhalt des Impressums
             */
            final String gruppeA[][] =
                    {
                            {"Vorname:", "Thimon"},
                            {"Nachname:", "Johannsen"},
                            {"Stra?e:", "Alte Gaertnerei 33"},
                            {"Postleitzahl:", "24232"},
                            {"Ort:", "Schoenkirchen"},
                            {"Land:", "Schleswig-Holstein"},
                            {"Geburtstag:", "06.08.1996"},
                            {"Geburtsort:", "Kiel"},
                            {"Staatsangehoerigkeit:", "deutsch"},
                            {"Muttersprache:", "deutsch"}
                    };

            tabellenmodellGruppeA = new DefaultTableModel(gruppeA, spalten);

            iTabelle = new JTable(tabellenmodellGruppeA);

            final JScrollPane scrollPane = new JScrollPane(iTabelle);
            iTabelle.setEnabled(false);

            center.add(scrollPane);
            getContentPane().add(center, BorderLayout.CENTER);
            validate();

        }
    }

    /**
     * Das Panel Center wird geleert
     */

    private void clearCenter() {
        if (center != null) {
            remove(center);

        }
    }

    private void readXML() {
        if (readerWriter.read(file) != null) {
            game = readerWriter.read(file);
        }
    }

    private void writeXML() {
        try {
            readerWriter.write(new File("Users/thimonj/Programmieren/Java/Eclipse/Ausbildung/Betrieb/GameDataBank/game.xml"), game);
        } catch (final JAXBException e1) {
            e1.printStackTrace();
        }
    }

    public static void main(final String args[]) {
        final GameDataBase test = new GameDataBase("Spieldatenbank");
        test.setSize(600, 350);
        test.setTitle("Spieldatenbank");
        test.setVisible(true);
    }

}
