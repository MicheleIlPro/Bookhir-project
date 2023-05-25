import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class User {

    private String mail, password, nome, cognome;

    //metodo di classe per creare un utente.
    public User(String mail, String password, String nome, String cognome) {
        System.out.println("\nLogin eseguito come: "+nome);
        this.mail = mail;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }

    public void scelteUser() throws SQLException {
        String url = "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7620373";
        String username = "sql7620373";
        String password = "EyJkktKdgA";
        DatabaseConnection database = new DatabaseConnection(url,username,password);

        //boolean per il ciclo delle scelte
        boolean cicloIniziale = true;

        while (cicloIniziale) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Cosa vuoi fare? \n1) Visualizzare la lista dei libri.\n2) Controllare la validità della carta o crearla/aggiornarla\n3) Noleggiare un libro\n4) Logout");
            int scelta = scanner.nextInt();

            switch (scelta) {
                case 1: //visualizzare i libri

                    //query per selezionare ciò che vogliamo vedere
                    ResultSet resultSet = database.resultSet("SELECT titolo, autore, descrizione FROM libri");
                    int i=1; //serve per dare un numero a tutti i libri visualizzati.
                    while (resultSet.next()){
                        System.out.println(i+") Titolo:"+resultSet.getString("titolo")+" | "+"Autore: "+resultSet.getString("autore")+" | "+" Desc: "+resultSet.getString("descrizione"));
                    }  //visitiamo tutti i risultati stampandoli

                    break;
                case 2: //controllare la validità della carta, se è scaduto o non presente, chiedi se vuole crearla l'utente.
                    database.resultSet("SELECT utenteMail, data_emissione, data_scadenza FROM tessere");
                    tessera(scanner,database);

                    break;
                case 3: //noleggiare un libro utilizzando il nome del libro.
                    break;
                case 4: //usciamo dal programma invertendo la condizione che lo fà iniziare.
                    cicloIniziale = false;
                    System.out.println("Usciamo dal programma.");
                    break;
                default:
                    System.out.println("Ti ho detto di digitare un numero da 1 a 4");
                    break;
            }
        }

    }
    //questo metodo lo usiamo per gestire la scelta dell'utente

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void tessera(Scanner scanner, DatabaseConnection database) throws SQLException {
        ResultSet resultSet = database.resultSet("SELECT utenteMail, data_scadenza FROM tessere WHERE utenteMail = '"+this.mail+"'");
        if (resultSet.next()){
            System.out.println("Possiedi la tessera, scadrà il: "+resultSet.getString("data_scadenza"));
            return;
        }

        System.out.println("non possiedi la tessera, vuoi crearla? \n1) Certo \n2) Torna indietro");
        int scelta = scanner.nextInt();

        switch (scelta){
            case 1:

                database.sendResult("INSERT INTO tessere (utenteMail, data_emissione, data_scadenza) VALUES ('"+this.mail+"', '"+dataOggi()+"', '"+dataScadenza()+"');");
                System.out.println("Adesso disponi di una nuova tessera che scadrà tra 1 anno.");
                break;
            default:
                break;
        }
    }

    public String dataOggi(){
        LocalDate data = LocalDate.now();
        DateTimeFormatter formattamento = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return data.format(formattamento);
    }

    public static String dataScadenza() {
        LocalDate data = LocalDate.now().plusYears(1);
        DateTimeFormatter formattamento = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return data.format(formattamento);
    }

}
