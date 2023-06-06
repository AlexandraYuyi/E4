/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author alexa
 */
public class conexionSQL {
    
        static String URL = "jdbc:mysql://localhost:3306/e4";
        static String USERNAME = "root";
        static String PASSWORD = "1234ASDFghjk1234";
        static Connection conectar = null;
    
    
    public Connection conexion(){
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("conexion exitosa");
            
        } catch (Exception e) {
            System.out.println("error en conexion" + e.toString());
        }
        return conectar;
    }
    
}
