package application.Model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class Database {
    private static String driver;
    private static String user;
    private static String password;
    private static String host;
    private static String database;
    private static int port;
    private static String type;

    private static Connection dbConnection = null;

    static{
        try (InputStream file = new FileInputStream("config.properties")){
            Properties config = new Properties();
            config.load(file);

            try {
                
                String driver = config.getProperty("driver");
                user = config.getProperty("user");
                password = config.getProperty("password");
                host = config.getProperty("host");
                port = Integer.parseInt(config.getProperty("port"));
                database = config.getProperty("database");
                type = config.getProperty("type");
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private Database(){

    }

    public static Connection getInstance(){
        try {
            if(dbConnection == null||dbConnection.isClosed()){  // if Timout then start new connection


                //stelle neue Verbindung her
                try {
                    dbConnection = DriverManager.getConnection("jdbc:" + type + "://" + host + ":"+ port +"/" + database, user, password);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbConnection;
    }

}