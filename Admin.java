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
            System.out.println("Cosa vuoi fare? \n1) Menù libri\n2) Visualizza gli utenti registrati \n3) Controllare la lista di tessere emesse \n4) Menu Noleggi\n5) Logout");
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
                    noleggioScelta(scanner, database);


                    break;
                default:  //logout
                    System.out.println("Logout eseguito con successo.");
                    cicloIniziale = false;
                    break;
            }
        }

    }

    private void noleggioScelta(Scanner scanner, DatabaseConnection database) throws SQLException {
        boolean cicloNoleggio = true;

        while (cicloNoleggio) {
            System.out.println("Cosa vuoi fare? \n1)Visualizza lista noleggi \n2) Elimina un noleggio \n3) Esci dal menù");
            int scelta = scanner.nextInt();
            switch (scelta) {
                case 1: //lista noleggi
                    mostraNoleggi(database, false);
                    break;
                case 2: // elimina noleggio a partire di una email
                    mostraNoleggi(database, true);
                    eliminaNoleggio(scanner, database);

                    break;
                case 3: //esci dal menu
                    System.out.println("Stai uscendo dal menù Noleggio");
                    cicloNoleggio = false;
                    break;
            }
        }


    }

    private void mostraNoleggi(DatabaseConnection database, boolean indice) throws SQLException {
        if (indice) {  //se la condizione è true, vogliamo vedere gli indici e quì prendiamo anche gli indici con la query
            ResultSet resultSet = database.resultSet("SELECT id, emailUtente, titoloLibro, data_noleggio, data_scadenza FROM noleggi");
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getInt("id") + " Email: " + resultSet.getString("emailUtente") + " | Libro: " + resultSet.getString("titoloLibro") + " | Dal:" + resultSet.getString("data_noleggio") + " al:" + resultSet.getString("data_scadenza"));
            }  //visitiamo tutti i risultati stampandoli
        } else {  //se non è true la condizione, prendiamo l'intera lista SENZA indici
            ResultSet resultSet = database.resultSet("SELECT emailUtente, titoloLibro, data_noleggio, data_scadenza FROM noleggi");
            while (resultSet.next()) {
                System.out.println("Email: " + resultSet.getString("emailUtente") + " | Libro: " + resultSet.getString("titoloLibro") + " | Dal:" + resultSet.getString("data_noleggio") + " al:" + resultSet.getString("data_scadenza"));
            }  //visitiamo tutti i risultati stampandoli
        }

    }

    private void eliminaNoleggio(Scanner scanner, DatabaseConnection database) throws SQLException {
        boolean cicloEliminaNoleggio = true;
        while (cicloEliminaNoleggio) {
            scanner.nextLine();
            System.out.print("Scrivi l'id del noleggio che vuoi eliminare: ");
            int idNoleggioDaEliminare = scanner.nextInt();

            //prendiamo il primo risultato più vicino al nome richiesto
            String querySelezionaNoleggio = "SELECT id FROM noleggi WHERE id LIKE '" + idNoleggioDaEliminare + "' LIMIT 1;";
            ResultSet resultSet = database.resultSet(querySelezionaNoleggio);
            if (!resultSet.next()) { //se trova qualcosa continua, sennò blocca il ciclo.
                System.out.println("Errore, Nessuna corrispondenza trovata!");
                break;
            }
            System.out.println("Vuoi eliminare: " + idNoleggioDaEliminare + "\n1) Confermo \n2+) Riprova");

            if (scanner.nextInt() == 1) {
                String queryEliminaNoleggio = "DELETE FROM noleggi WHERE id LIKE ?";
                database.preparedSendResult(queryEliminaNoleggio, idNoleggioDaEliminare);
                System.out.println("Libro eliminato con successo dal database.");
                cicloEliminaNoleggio = false;
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
                    modificaLibro(scanner, database);

                    break;
                case 4: //aggiungi un libro
                    aggiungiLibro(scanner, database);


                    //aggiungiere titolo, autore, linkImmagine, descrizione, quantita

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

    private void aggiungiLibro(Scanner scanner, DatabaseConnection database) throws SQLException {
        System.out.println("Aggiunta libro in corso");
        scanner.nextLine(); //puliamo lo scanner
        System.out.print("Inserire il titolo: ");
        String titolo = scanner.nextLine();
        System.out.print("Inserire l'autore: ");
        String autore = scanner.nextLine();
        System.out.print("Inserire la descrizione: ");
        String descrizione = scanner.nextLine();
        System.out.print("Inserire link immagine: ");
        String linkImmagine = scanner.nextLine();
        System.out.println("Inserire la quantità di libri: ");
        int quantita = scanner.nextInt();

        //controllo autore
        ResultSet resultSetAutori = database.preparedResultSet("SELECT nome FROM autori WHERE nome = ? LIMIT 1;",autore);
        if (!resultSetAutori.next()){
            System.out.println("Errore, autore non trovato, vuoi crearne uno chiamato: "+autore+"? \n1) Si \n2+) Annulla operazione");
            int sceltaAutore = scanner.nextInt();
            if (sceltaAutore==1){ //se la scelta è 1 eseguirà questo if
                creaAutore(database,autore);
            }else {
                System.out.println("Operazione annullata correttamente.");
            }
        }


        boolean cicloModifica = true;
        while (cicloModifica) {


            System.out.println("Questi sono i dati che hai inserito \nTitolo: " + titolo + "\nAutore: " + autore + "\nDescrizione: " + descrizione + "\nLink immagine: " + linkImmagine + "\nQuantità: " + quantita);
            System.out.print("Vuoi modificare qualcosa? \n1) Titolo --Non sarà possibile modificarlo in seguito \n2) Autore --Non sarà possibile modificarlo in seguito \n3) Link Immagine \n4) Descrizione \n5) Quantità \n6+) Avanti \nComando: ");
            int sceltaModifica = scanner.nextInt();
            switch (sceltaModifica) {
                case 1:  //titolo
                    System.out.println("Attuale titolo: " + titolo);
                    System.out.print("Digitare il nuovo titolo: ");
                    scanner.nextLine(); //puliamo lo scanner prima di usarlo
                    titolo = scanner.nextLine();
                    System.out.println("Aggiornamento eseguito con successo.");

                    break;
                case 2: //per l'autore
                    listaAutori(database);
                    scanner.nextLine(); //puliamo lo scanner prima di usarlo
                    System.out.println("Auttale autore: " + autore);
                    System.out.print("Digita il nuovo autore: "); //ALTRI CONTROLLI SULL'AUTORE!
                    autore = scanner.nextLine();

                    ResultSet resultSetAutori2 = database.preparedResultSet("SELECT nome FROM autori WHERE nome = ? LIMIT 1;",autore);
                    if (resultSetAutori2.next()){
                        System.out.println("Aggiornamento eseguito con successo.");
                    }else {
                        System.out.println("Errore, autore non trovato, vuoi crearne uno chiamato: "+autore+"? \n1) Si \n2+) Annulla operazione");
                        int sceltaAutore = scanner.nextInt();
                        if (sceltaAutore!=1){ //se la scelta è 1 eseguirà questo if
                            System.out.println("Operazione annullata correttamente.");
                            break;
                        }
                        creaAutore(database,autore);
                    }


                    break;
                case 3: //link immagine
                    System.out.println("Attuale link: " + linkImmagine);
                    System.out.print("Digita il nuovo link immagine: ");
                    scanner.nextLine(); //puliamo lo scanner prima di usarlo
                    linkImmagine = scanner.nextLine();
                    System.out.println("Aggiornamento eseguito con successo.");

                    break;
                case 4: //descrizione
                    System.out.println("Attuale descrizione: " + descrizione);
                    System.out.print("Digita la descrizione aggiornata: ");
                    scanner.nextLine(); //puliamo lo scanner prima di usarlo
                    descrizione = scanner.nextLine();
                    System.out.println("Aggiornamento eseguito con successo.");

                    break;
                case 5: //quantità
                    System.out.println("Attuale quantità: " + quantita);
                    System.out.print("Digita la quantità aggiornata: ");
                    quantita = scanner.nextInt();
                    System.out.println("Aggiornamento eseguito con successo.");

                    break;
                default: //finiamo il programma senza modifiche
                    System.out.println("Fine modifiche");
                    cicloModifica = false;
                    break;
            }
        }

        database.preparedSendResult("INSERT INTO libri (titolo, autore, linkImmagine, descrizione, quantita) VALUES (?, ?, ?, ?, ?)",titolo,autore,linkImmagine,descrizione,quantita);
        //quì facciamo la query che manda tutti i dati



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
            String querySelezionaLibro = "SELECT titolo FROM libri WHERE titolo LIKE '%" + libroDaEliminare + "%' LIMIT 1;";
            ResultSet resultSet = database.resultSet(querySelezionaLibro);
            if (!resultSet.next()) { //se trova qualcosa continua, sennò blocca il ciclo.
                System.out.println("Errore, Nessuna corrispondenza trovata!");
                break;
            }
            String titoloDaEliminare = resultSet.getString("titolo");
            System.out.println("Vuoi eliminare: " + titoloDaEliminare + "\n1) Confermo \n2+) Riprova");

            if (scanner.nextInt() == 1) {
                String queryEliminaLibro = "DELETE FROM libri WHERE titolo LIKE ?";
                database.preparedSendResult(queryEliminaLibro, titoloDaEliminare);
                System.out.println("Noleggio eliminato con successo dal database.");
                cicloEliminaLibro = false;
            }
        }
    }

    private void modificaLibro(Scanner scanner, DatabaseConnection database) throws SQLException {
        mostraLibri(database);
        boolean cicloModificaLibro = true;


        while (cicloModificaLibro) {


            scanner.nextLine();
            System.out.println("Quale libro vuoi modificare?");
            System.out.print("Digita il nome: ");
            String libroDaModificare = scanner.nextLine();
            //prendiamo il primo risultato più vicino al nome richiesto
            String querySelezionaLibro = "SELECT titolo, autore, linkImmagine, descrizione, quantita FROM libri WHERE titolo LIKE '%" + libroDaModificare + "%' LIMIT 1;";
            ResultSet resultSet = database.resultSet(querySelezionaLibro);
            if (!resultSet.next()) { //se trova qualcosa continua, sennò blocca il ciclo.
                System.out.println("Errore, Nessuna corrispondenza trovata!");
                break;
            }
            String risultatoTitolo = resultSet.getString("titolo");
            String risultatoAutore = resultSet.getString("autore");
            String risultatoLink = resultSet.getString("linkImmagine");
            String risultatoDesc = resultSet.getString("descrizione");
            int risultatoQuantita = resultSet.getInt("quantita");

            System.out.println("Quale parametro vuoi modificare? \n1) Titolo --Non è possibile modificarlo \n2) Autore --Non è possibile modificarlo \n3) Link Immagine \n4) Descrizione \n5) Quantità \n6+) Annulla");
            int sceltaModifica = scanner.nextInt();
            switch (sceltaModifica) {
                case 1:  //titolo
                    System.out.println("Attuale titolo: " + risultatoTitolo);
                    System.out.print("Digitare il nuovo titolo: ");
                    scanner.nextLine(); //puliamo lo scanner prima di usarlo
                    String nuovoTitolo = scanner.nextLine();

                    System.out.println("HO SCRITTO CHE NON È POSSIBILE MODIFICARLO.");
                    //database.preparedSendResult("UPDATE libri SET titolo = ? WHERE titolo = ?",nuovoTitolo, risultatoTitolo);  è una FOREIGN KEY, e non è modificabile.

                    break;
                case 2: //per l'autore
                    listaAutori(database);
                    scanner.nextLine(); //puliamo lo scanner prima di usarlo
                    System.out.println("Auttale autore: " + risultatoAutore);
                    System.out.print("Digita il nuovo autore: "); //ALTRI CONTROLLI SULL'AUTORE!
                    String nuovoAutore = scanner.nextLine();
                    System.out.println("HO SCRITTO CHE NON È POSSIBILE MODIFICARLO.");
/*
                    ResultSet resultSetAutori = database.resultSet("SELECT nome FROM autori WHERE nome = '%"+nuovoAutore+"%' LIMIT 1;");
                    if (resultSetAutori.next()){
                        database.preparedSendResult("UPDATE libri SET autore = ? WHERE titolo = ?",nuovoAutore, risultatoTitolo);
                        System.out.println("Aggiornamento eseguito con successo.");
                    }else {
                        System.out.println("Errore, autore non trovato, vuoi crearne uno chiamato: "+nuovoAutore+"? \n1) Si \n2+) Annulla operazione");
                        int sceltaAutore = scanner.nextInt();
                        if (sceltaAutore!=1){ //se la scelta è 1 eseguirà questo if
                            System.out.println("Operazione annullata correttamente.");
                            break;
                        }
                        creaAutore(database,nuovoAutore);
                        database.preparedSendResult("UPDATE libri SET autore = ? WHERE libri.titolo = ? AND libri.autore = ?;",nuovoAutore, risultatoTitolo,risultatoAutore);
                        //UPDATE `libri` SET `autore` = 'Autore2' WHERE `libri`.`titolo` = 'Libro3' AND `libri`.`autore` = 'Autore3';
                    }
*/

                    break;
                case 3: //link immagine
                    System.out.println("Attuale link: " + risultatoLink);
                    System.out.print("Digita il nuovo link immagine: ");
                    scanner.nextLine(); //puliamo lo scanner prima di usarlo
                    String nuovoLink = scanner.nextLine();
                    database.preparedSendResult("UPDATE libri SET linkImmagine = ? WHERE titolo = ?", nuovoLink, risultatoTitolo);
                    System.out.println("Aggiornamento eseguito con successo.");

                    break;
                case 4: //descrizione
                    System.out.println("Attuale descrizione: " + risultatoDesc);
                    System.out.print("Digita la descrizione aggiornata: ");
                    scanner.nextLine(); //puliamo lo scanner prima di usarlo
                    String nuovaDesc = scanner.nextLine();
                    database.preparedSendResult("UPDATE libri SET descrizione = ? WHERE titolo = ?", nuovaDesc, risultatoTitolo);
                    System.out.println("Aggiornamento eseguito con successo.");

                    break;
                case 5: //quantità
                    System.out.println("Attuale quantità: " + risultatoQuantita);
                    System.out.print("Digita la quantità aggiornata: ");
                    int nuovaQuantita = scanner.nextInt();
                    database.preparedSendResult("UPDATE libri SET quantita = ? WHERE titolo = ?", nuovaQuantita, risultatoTitolo);
                    System.out.println("Aggiornamento eseguito con successo.");

                    break;
                default: //finiamo il programma senza modifiche
                    System.out.println("Hai annullato la modifica");
                    break;
            }
            cicloModificaLibro = false;

//-------------------------------------------------


        }

    }


    private void listaAutori(DatabaseConnection database) throws SQLException {

        ResultSet resultSet = database.resultSet("SELECT nome FROM `autori`");
        int i = 1;
        while (resultSet.next()) {
            System.out.println(i++ + ") " + resultSet.getString("nome"));
        }

    }

    private void creaAutore(DatabaseConnection database, String nuovoAutore) {
        database.preparedSendResult("INSERT INTO autori (nome) VALUES (?)", nuovoAutore);
        System.out.println("Nuovo autore aggiunto: " + nuovoAutore);
    }
}
