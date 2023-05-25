import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Admin {

    private String mail, password, nome, cognome;

    public Admin(String mail, String password, String nome, String cognome) {
        this.mail = mail;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }

    //utilizziamo questo per far scegliere all'admin cosa vuole fare.
    public void scelteAdmin() throws SQLException {
        String url = "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7620373";
        String username = "sql7620373";
        String password = "EyJkktKdgA";
        DatabaseConnection database = new DatabaseConnection(url, username, password);

        //boolean per il ciclo delle scelte
        boolean cicloIniziale = true;

        while (cicloIniziale) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Cosa vuoi fare? \n1) Menù libri\n2) Visualizza gli utenti registrati \n3) Controllare la validità della carta o crearla/aggiornarla\n4) Noleggiare un libro\n5) Logout");
            int scelta = scanner.nextInt();

            switch (scelta) {
                case 1:  //menu i libri
                    menuLibri(scanner, database);
                    break;
                case 2:  //utenti registrati presenti
                    ResultSet resultSetUtenti = database.resultSet("SELECT nome, cognome, email, password FROM utenti");
                    while (resultSetUtenti.next()) {
                        System.out.println("Utente: " + resultSetUtenti.getString("nome") + " " + resultSetUtenti.getString("cognome") + " | Email: " + resultSetUtenti.getString("email") + " Pass: " + resultSetUtenti.getString("password"));
                    }  //visitiamo tutti i risultati stampandoli


                    break;
                case 3:  //lista tessere emesse
                    //query per selezionare ciò che vogliamo vedere
                    ResultSet resultSet = database.resultSet("SELECT id, utenteMail, data_emissione, data_scadenza FROM tessere");
                    while (resultSet.next()) {
                        System.out.println(resultSet.getInt("id") + ") mail:" + resultSet.getString("utenteMail") + " | " + "Data emissione: " + resultSet.getString("data_emissione") + " | " + " Data scadenza: " + resultSet.getString("data_scadenza"));
                    }  //visitiamo tutti i risultati stampandoli

                    break;
                case 4: //visualizzare il noleggio libri e forse rimuovere un noleggio libri.
                    break;
                default:  //logout
                    System.out.println("Logout eseguito con successo.");
                    cicloIniziale = false;
                    break;
            }
        }

    }

    private void menuLibri(Scanner scanner, DatabaseConnection database) throws SQLException {

        boolean cicloLibri = true;
        while (cicloLibri) {

            System.out.println("Cosa vuoi farei? \n1) Visualizzare la lista dei libri \n2) Eliminare un libro utilizzando il nome \n3) Modifica un libro utilizzando \n4) Aggiungi un libro \n5) Esci dal menù libri");
            int scelta = scanner.nextInt();
            switch (scelta) {
                case 1:  //visualizziamo la lista dei libri
                    mostraLibri(database);
                    break;
                case 2: //eliminiamo un libro partendo dal nome
                    mostraLibri(database);
                    eliminaLibro(scanner, database);

                    break;
                case 3:  //menu per modificare i campi del libro.
                    break;
                case 4: //aggiungi un libro
                    break;
                case 5:
                    System.out.println("Stai uscendo dal menù libri.");
                    cicloLibri = false;
                    break;
                default:
                    break;
            }
        }
    }

    private void menuAutori(Scanner scanner) {


    }

    private void mostraLibri(DatabaseConnection database) throws SQLException {
        //query per selezionare ciò che vogliamo vedere
        ResultSet resultSet = database.resultSet("SELECT titolo, autore, descrizione FROM libri");
        int i = 1; //serve per dare un numero a tutti i libri visualizzati.
        while (resultSet.next()) {
            System.out.println(i++ + ") Titolo: -" + resultSet.getString("titolo") + "- | " + "Autore: " + resultSet.getString("autore") + " | " + " Desc: " + resultSet.getString("descrizione"));
        }  //visitiamo tutti i risultati stampandoli
    }

    private void eliminaLibro(Scanner scanner, DatabaseConnection database) throws SQLException {

        boolean cicloEliminaLibro = true;
        while (cicloEliminaLibro) {
            scanner.nextLine();
            System.out.print("Digita il nome del libro che vuoi eliminare: ");
            String libroDaEliminare = scanner.nextLine();

            //prendiamo il primo risultato più vicino al nome richiesto
            String querySelezionaLibro = "SELECT titolo FROM libri WHERE titolo LIKE '%"+libroDaEliminare+"%' LIMIT 1;";
            ResultSet resultSet = database.resultSet(querySelezionaLibro);
            if (!resultSet.next()){ //se trova qualcosa continua, sennò blocca il ciclo.
                System.out.println("Errore, Nessuna corrispondenza trovata!");
                break;
            }
            String titoloDaEliminare = resultSet.getString("titolo");
            System.out.println("Vuoi eliminare: " + titoloDaEliminare + "\n1) Confermo \n2+) Riprova");

            if (scanner.nextInt() == 1) {
                String queryEliminaLibro = "DELETE FROM libri WHERE titolo LIKE ?";
                database.preparedSendResult(queryEliminaLibro, titoloDaEliminare);
                System.out.println("Libro eliminato con successo dal database.");
                cicloEliminaLibro = false;
            }
        }


    }

}
