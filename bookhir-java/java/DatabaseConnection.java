import java.sql.*;

public class DatabaseConnection {

    private Connection connection;
    public DatabaseConnection(String url, String username, String password) throws SQLException {
        try{
            connection = DriverManager.getConnection(url, username, password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //creiamo questo metodo che riutilizzeremo spesso
    public ResultSet resultSet(String query) {
        try{
            return connection.createStatement().executeQuery(query);
        }catch (Exception e){}
        return null;
    }

    public void sendResult(String query){
        try{
            connection.createStatement().executeUpdate(query);
        }catch (Exception e){}
    }

    //Utilizziamo questo metodo, quando dobbiamo utilizzare una query che restituirà dei valori
    public ResultSet preparedResultSet(String query, Object... params) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            //cicliamo ogni parametro che diamo utilizzando Object... (che serve per parametri multipli)
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Utilizziamo questo metodo, quando dobbiamo utilizzare una query che modificheranno dei valori e non dovranno restituire nulla (tra l'altro è VOID)
    public void preparedSendResult(String query, Object... parametri) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            //cicliamo ogni parametro che diamo utilizzando Object... (che serve per parametri multipli)
            for (int i = 0; i < parametri.length; i++) {
                statement.setObject(i + 1, parametri[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
