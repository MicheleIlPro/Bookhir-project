import java.sql.*;
import java.util.Scanner;

public class Principale {

    public static void main(String[] args) throws SQLException {

        // Informazioni di connessione al database
        String url = "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7620373";
        String username = "sql7620373";
        String password = "EyJkktKdgA";

        // Classe che utilizzeremo per utilizzare i database
        DatabaseConnection database = new DatabaseConnection(url, username, password);

        //OGNI separatore(); equivale a stampare: ------------------------------------------
        separatore();

        //Inizia il programma, eliminare tutto ciò che c'è sopra.
        System.out.println("Benvenuto su Bookhir. La biblioteca che contiene tutto!");
        separatore();
        //variabile per iniziare il ciclo.
        boolean cicloIniziale = true;
        while (cicloIniziale) {
            Scanner scannerIniziale = new Scanner(System.in);
            //inizializzo dei boolean che utilizzerò per superare il login.
            boolean esisteUtente = false;
            boolean admin = false;

            //login.
            System.out.println("Devi eseguire il login con un account.");

            //chiediamo email
            System.out.print("Inserire l'email: ");
            String emailSito = scannerIniziale.nextLine();

            //chiediamo password
            System.out.print("Inserire la password: ");
            String passwordSito = scannerIniziale.nextLine();

            separatore();

            //selezioniamo mail e pass per eseguire un controllo, in caso siano presenti effettuiamo il login anche quì, sennò si consiglia la registrazione.
            String queryLogin = "SELECT email, password, nome, cognome, admin FROM utenti WHERE email = ? AND password = ?";
            ResultSet controlloLogin = database.preparedResultSet(queryLogin, emailSito, passwordSito);

            //se la query restituisce qualcosa significa che i dati esistono e la persona esiste.
            if (controlloLogin.next()) {
                esisteUtente = true; //l'utente esiste, non serve registrarlo.
                admin = controlloLogin.getBoolean("admin");  //quì capiamo se l'utente è un admin o un utente normale.
            }

            if (admin) {
                System.out.print("Stai eseguendo il login, vuoi procedere come admin o come utente? \n1) Admin \n2+)Utente \nComando: ");
                int sceltaAdmin = scannerIniziale.nextInt();
                separatore();
                if (sceltaAdmin == 1) {
                    System.out.println("Stai accedendo come ADMIN");
                    separatore();
                } else {
                    admin = false;
                }
            }

            if (esisteUtente && admin) {  //accesso da admin
                Admin adminTab = new Admin(emailSito, passwordSito, controlloLogin.getString("nome"), controlloLogin.getString("cognome"));
                adminTab.scelteAdmin();
            } else if (esisteUtente) {   //accesso da user
                User userTab = new User(emailSito, passwordSito, controlloLogin.getString("nome"), controlloLogin.getString("cognome"));
                userTab.scelteUser();
            } else {   //registrazione
                System.out.println("Dati errati.");
                separatore();
                System.out.print("Vuoi registrarti? è gratuito! \n1) registrati \n2) ritorna al login \nComando: ");

                if (scannerIniziale.nextInt() == 1) {  //controlliamo la risposta, se è 1 proseguiamo con la registrazione, qualunque altra cosa farà ripartire il programma
                    separatore();
                    boolean registrazione = true;
                    while (registrazione) {
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
                        separatore();
                        if (!passwordRegister.equals(password2Register)) {
                            System.out.println("Errore. Le password non corrispondono.");
                            break;
                        }  //se le password non corrispondono errore

                        String queryCheckUser = "SELECT email, password FROM utenti WHERE email = ?";
                        ResultSet datiRegister = database.preparedResultSet(queryCheckUser,emailRegister);

                        if (datiRegister.next()) {
                            System.out.println("Sei già registrato al nostro sito! Esegui direttamente il login.");
                            break;
                        }

                        String queryRegistrazione = "INSERT INTO utenti (email, nome, cognome, password, admin) VALUES (?, ?, ?, ?, false)";
                        database.preparedSendResult(queryRegistrazione, emailRegister, nomeRegister, cognomeRegister, passwordRegister);
                    /* questo non serve perchè ho creato appositamente una classe che mi faccia scrivere TANTO codice in meno per tutte le queri
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
                    separatore();
                }else {
                separatore();
                }
            }


        }

    }

    public static void separatore() {
        System.out.println("------------------------------------------------------");
    }

}
