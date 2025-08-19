package Control;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author edgar
 */
public class Conexion {
    // Datos de conexión al servidor

    String url = "jdbc:sqlserver://localhost:1433;databaseName=Almacen;encrypt=true;trustServerCertificate=true;"; //URL
    String usuario = "sa";   // Usuario
    String password = "12345"; // Contraseña

    public Conexion() {
    }

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Driver JDBC registrado correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: No se encontró el driver JDBC");
            e.printStackTrace();
        }

    }

    public Connection Conectar() {
        // Intentar conectar
        Connection conn=null;
        try{
            conn = DriverManager.getConnection(url, usuario, password);
            System.out.println("Conexión exitosa a la base de datos.");

        } catch (Exception e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        return conn;    
    }

}
