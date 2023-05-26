import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class User {

    private String mail, password, nome, cognome;

    //metodo di classe per creare un utente.
    public User(String mail, String password, String nome, String cognome) {
        System.out.println("\nLogin eseguito come: " + nome);
        this.mail = mail;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }

    //questo metodo lo usiamo per gestire la scelta dell'utente
    public void scelteUser() throws SQLException {
        String url = "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7620373";
        String username = "sql7620373";
        String password = "EyJkktKdgA";
        DatabaseConnection database = new DatabaseConnection(url, username, password);

        //boolean per il ciclo delle scelte
        boolean cicloIniziale = true;

        while (cicloIniziale) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Cosa vuoi fare? \n1) Visualizzare la lista dei libri.\n2) Controllare la validità della carta o crearla/aggiornarla\n3) Noleggiare un libro \n4) Lista libri noleggiati \n5) Logout");
            int scelta = scanner.nextInt();

            switch (scelta) {
                case 1: //visualizzare i libri
                    mostraLibri(database);

                    break;
                case 2: //controllare la validità della carta, se è scaduto o non presente, chiedi se vuole crearla l'utente.
                    database.resultSet("SELECT utenteMail, data_emissione, data_scadenza FROM tessere");
                    tessera(scanner, database);

                    break;
                case 3: //noleggiare un libro utilizzando il nome del libro.
                    noleggioLibro(scanner, database);

                    break;
                case 4:
                    visualizzaListaLibriNoleggiati(database);

                    break;
                case 5: //usciamo dal programma invertendo la condizione che lo fà iniziare.
                    cicloIniziale = false;
                    System.out.println("Usciamo dal programma.");
                    break;
                default:
                    System.out.println("Ti ho detto di digitare un numero da 1 a 5");
                    break;
            }
        }

    }

    private void mostraLibri(DatabaseConnection database) throws SQLException {
        //query per selezionare ciò che vogliamo vedere
        ResultSet resultSet = database.resultSet("SELECT titolo, autore, descrizione FROM libri");
        int i = 1; //serve per dare un numero a tutti i libri visualizzati.
        while (resultSet.next()) {
            System.out.println((i++) + ") Titolo:" + resultSet.getString("titolo") + " | " + "Autore: " + resultSet.getString("autore") + " | " + " Desc: " + resultSet.getString("descrizione"));
        }  //visitiamo tutti i risultati stampandoli
    }

    public void tessera(Scanner scanner, DatabaseConnection database) throws SQLException {
        ResultSet resultSet = database.resultSet("SELECT utenteMail, data_scadenza FROM tessere WHERE utenteMail = '" + this.mail + "'");
        if (resultSet.next()) {
            System.out.println("Possiedi la tessera, scadrà il: " + resultSet.getString("data_scadenza"));
            return;
        }

        System.out.println("non possiedi la tessera, vuoi crearla? \n1) Certo \n2) Torna indietro");
        int scelta = scanner.nextInt();

        //chiediamo all'utente se vuole creare una nuova tessera, se dice di si la creiamo, sennò usciamo dallo switch
        switch (scelta) {
            case 1:  //creiamo una tessera utilizzando l'email dell'utente, la data di oggi, e la data di oggi+1 anno.
                database.sendResult("INSERT INTO tessere (utenteMail, data_emissione, data_scadenza) VALUES ('" + this.mail + "', '" + dataOggi() + "', '" + dataScadenzaAnno() + "');");
                System.out.println("Adesso disponi di una nuova tessera che scadrà tra 1 anno.");
                break;
            default:
                break;
        }
    }

    private void noleggioLibro(Scanner scanner, DatabaseConnection database) throws SQLException {
        mostraLibri(database);

        boolean cicloNoleggioLibro = true;
        while (cicloNoleggioLibro) {

            ResultSet controlliamoSeCeLaTessera = database.preparedResultSet("SELECT id FROM tessere WHERE utenteMail = ?",this.mail);
            if (!controlliamoSeCeLaTessera.next()){
                System.out.println("Non puoi noleggiare un libro se non possiedi la tessera!");
                break;
            }

            scanner.nextLine();
            System.out.println("Scrivi il titolo del libro che vuoi noleggiare.");
            String titoloLibro = scanner.nextLine();

            ResultSet resultSet = database.resultSet("SELECT titolo,autore,quantita FROM libri WHERE titolo LIKE '%"+titoloLibro+"%' LIMIT 1;");

            if (!resultSet.next()) {
                System.out.println("Nessun Libro con l'autore fornito è stato trovato.");
                break;
            }

            int quantitaLibro = resultSet.getInt("quantita");
            if (quantitaLibro<=0){
                System.out.println("Purtroppo il libro selezionato non è più disponibile.");
                break;
            }

            String titoloLibroTrovato = resultSet.getString("titolo");

            System.out.println("Vuoi noleggiare: " + titoloLibroTrovato + "\n1) Confermo \n2+) Riprova");
            int scelta = scanner.nextInt();

            ResultSet resultSet1 = database.preparedResultSet("SELECT id FROM noleggi WHERE titoloLibro = ?  AND emailUtente = ?",titoloLibroTrovato,this.mail);
            if (resultSet1.next()){
                System.out.println("Errore, non puoi noleggiare di nuovo questo libro!");
                break;
            }

            if (scelta == 1) {
                //inseriamo i dati del noleggio nel db
                String query = "INSERT INTO noleggi (emailUtente, titoloLibro, data_noleggio, data_scadenza) VALUES (?, ?, ?, ?);";
                database.preparedSendResult(query, this.mail, titoloLibroTrovato, dataOggi(), dataScadenzaMese());
                //diminuiamo di 1 la quantita
                database.sendResult("UPDATE libri SET quantita = "+(--quantitaLibro)+" WHERE titolo = '"+titoloLibroTrovato+"'");
                System.out.println("Noleggiato con successo.");

                //usciamo dal ciclo.
                cicloNoleggioLibro = false;
            }
        }

    }

    public void visualizzaListaLibriNoleggiati(DatabaseConnection database) throws SQLException {
        ResultSet resultSet = database.preparedResultSet("SELECT titoloLibro, data_noleggio, data_scadenza FROM noleggi WHERE emailUtente = ?",this.mail);
        int i = 1;
        while (resultSet.next()){
            System.out.println((i++)+") Titolo: "+resultSet.getString("titoloLibro")+" dal "+resultSet.getString("data_noleggio")+" al "+resultSet.getString("data_scadenza"));
        }
    }

    public static String dataOggi() {
        LocalDate data = LocalDate.now();
        DateTimeFormatter formattamento = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return data.format(formattamento);
    }

    public static String dataScadenzaMese() {
        LocalDate data = LocalDate.now().plusMonths(1);
        DateTimeFormatter formattamento = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return data.format(formattamento);
    }

    public static String dataScadenzaAnno() {
        LocalDate data = LocalDate.now().plusYears(1);
        DateTimeFormatter formattamento = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return data.format(formattamento);
    }

}
