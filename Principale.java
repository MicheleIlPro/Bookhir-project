import java.sql.*;
import java.util.Scanner;

public class Principale {

    public static void main(String[] args) throws SQLException {

        // Informazioni di connessione al database
        String url = "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7620373";
        String username = "sql7620373";
        String password = "EyJkktKdgA";

        // Classe che utilizzeremo per utilizzare i database
        DatabaseConnection database = new DatabaseConnection(url,username,password);

        Connection connection = DriverManager.getConnection(url, username, password);
/*

        // Query SQL
        String query = "SELECT `titolo`, `autore`, `linkImmagine`, `descrizione`, `quantita` FROM `libri`";

        //ResultSet resultSet = database.resultSet(query);
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        // Itera attraverso i risultati della query
        while (resultSet.next()) {
            String titolo = resultSet.getString("titolo");
            String autore = resultSet.getString("autore");
            String linkImmagine = resultSet.getString("linkImmagine");
            String descrizione = resultSet.getString("descrizione");
            int quantita = resultSet.getInt("quantita");

            // Fai qualcosa con i dati ottenuti dalla query
            System.out.println("Titolo: " + titolo);
            System.out.println("Autore: " + autore);
            System.out.println("Link immagine: " + linkImmagine);
            System.out.println("Descrizione: " + descrizione);
            System.out.println("Quantità: " + quantita);
            System.out.println("----------------------");
        }
        String query1 = "SELECT * FROM libri";

        ResultSet resultSet1 = database.resultSet(query1);
        while (resultSet1.next()){
            System.out.println("Titolo: "+resultSet1.getString("titolo"));
        }
*/



        //Inizia il programma, eliminare tutto ciò che c'è sopra.
        System.out.println("Benvenuto su Bookhir. La biblioteca che contiene tutto!");

        //variabile per iniziare il ciclo.
        boolean cicloIniziale = true;
        while (cicloIniziale){
            Scanner scannerIniziale = new Scanner(System.in);
            //inizializzo dei boolean che utilizzerò per superare il login.
            boolean esisteUtente = false;
            boolean admin = false;

            //login.
            System.out.println("Devi eseguire il login con un account.");
            System.out.println("Inserire l'email.");
            String emailSito = scannerIniziale.nextLine();

            System.out.println("Inserire la password.");
            String passwordSito = scannerIniziale.nextLine();


            ResultSet datiSensibili = database.resultSet("SELECT email, password, admin FROM utenti WHERE email = '"+emailSito+"' AND password = '"+passwordSito+"'");
            while (datiSensibili.next()){
                System.out.println(datiSensibili.getString("email") + " "+ datiSensibili.getString("password")+" "+ datiSensibili.getBoolean("admin"));
            }

            //selezioniamo mail e pass per eseguire un controllo, in caso siano presenti effettuiamo il login anche quì, sennò si consiglia la registrazione.
            String queryLogin = "SELECT email, password, nome, cognome, admin FROM utenti WHERE email = ? AND password = ?";
            ResultSet controlloLogin = database.preparedResultSet(queryLogin, emailSito, passwordSito);

            //se la query restituisce qualcosa significa che i dati esistono e la persona esiste.
            if (controlloLogin.next()) {
                esisteUtente = true; //l'utente esiste, non serve registrarlo.
                admin=controlloLogin.getBoolean("admin");  //quì capiamo se l'utente è un admin o un utente normale.
            }


            if (esisteUtente && admin){  //accesso da admin
                Admin adminTab = new Admin(emailSito,passwordSito,controlloLogin.getString("nome"),controlloLogin.getString("cognome"));
                adminTab.scelteAdmin();
            }else if (esisteUtente){   //accesso da user
                User userTab = new User(emailSito,passwordSito,controlloLogin.getString("nome"),controlloLogin.getString("cognome"));
                userTab.scelteUser();
            }else{   //registrazione
                System.out.println("Dati errati.");
                System.out.println("Vuoi registrarti? \n1) registrati \n2) ritorna al login");


                if (scannerIniziale.nextInt()==1){  //controlliamo la risposta, se è 1 proseguiamo con la registrazione, qualunque altra cosa farà ripartire il programma

                boolean registrazione = true;
                while (registrazione){
                    System.out.println("Registrati è gratuito!");
                    System.out.print("Inserisci una email: ");
                    scannerIniziale.nextLine();
                    String emailRegister = scannerIniziale.nextLine();
                    System.out.print("Inserisci il tuo nome: ");
                    String nomeRegister = scannerIniziale.nextLine();
                    System.out.print("Inserisci il tuo cognome: ");
                    String cognomeRegister = scannerIniziale.nextLine();
                    System.out.print("Inserisci password: ");
                    String passwordRegister = scannerIniziale.nextLine();
                    System.out.print("Ripeti password: ");
                    String password2Register = scannerIniziale.nextLine();
                    System.out.println();

                    if (!passwordRegister.equals(password2Register)){
                        System.out.println("Errore. Le password non corrispondono.");
                        break;
                    }  //se le password non corrispondono errore

                    String queryCheckUser = "SELECT email, password FROM utenti WHERE email = ?";
                    PreparedStatement statementControllo = connection.prepareStatement(queryCheckUser);
                    statementControllo.setString(1, emailRegister);
                    ResultSet datiRegister = statementControllo.executeQuery();

                    if (datiRegister.next()) {
                        System.out.println("Sei già registrato al nostro sito! Esegui direttamente il login.");
                        break;
                    }

                    String queryRegistrazione = "INSERT INTO utenti (email, nome, cognome, password, admin) VALUES (?, ?, ?, ?, false)";
                    database.preparedSendResult(queryRegistrazione,emailRegister,nomeRegister,cognomeRegister,passwordRegister);
                    /*
                    PreparedStatement statementRegistrazione = connection.prepareStatement(queryRegistrazione);
                    statementRegistrazione.setString(1, emailRegister);
                    statementRegistrazione.setString(2, nomeRegister);
                    statementRegistrazione.setString(3, cognomeRegister);
                    statementRegistrazione.setString(4, passwordRegister);
                    statementRegistrazione.executeUpdate();
                    */
                    System.out.println("\nAdesso puoi eseguire l'accesso.");
                    registrazione = false;
                }
                }
            }


        }

    }
}
