import java.sql.*;
public class Conexion {
    public Connection estConexion(){
        Connection con=null;
        /*Datos para la conecion*/
        String base="Facturacion";
        String url="jdbc:mysql://localhost:3306/"+base;
        String user="root";
        String password="3264";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException | SQLException e){
            System.err.println(e);
        }
        return con;
    }
}