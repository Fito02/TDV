/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author lambda
 */
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EchoServer {

    private static Connection cn;
    
    public static void main(String[] args) {
        activarServer();
    }
    
    public static void activarServer () {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine = in.readLine();
            System.out.println("Servidor");
            System.out.println("Recibiendo: " + inputLine);
            String outputLine = "";
            if (!inputLine.equalsIgnoreCase("salir")) {
                outputLine = String.valueOf(evaluarExpresion(inputLine));
            }

            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://localhost/servidor", "root", "12345");
            PreparedStatement prStmt = cn.prepareStatement("INSERT INTO Operaciones (num1, num2, operacion, resultado) VALUES (?,?,?,?)");
            prStmt.setString(1, op1);
            prStmt.setString(2, op2);
            prStmt.setString(3, operacion);
            prStmt.setString(4, resultado);
            prStmt.executeUpdate();
            cn.close();

            System.out.println("Enviando: " + outputLine);
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();
            
            if (!inputLine.equalsIgnoreCase("salir")) {
                activarServer();
            }
        } catch (Exception ex) {
            System.out.println("Error");
        }
    }
    
    static String op1, op2, operacion, resultado;
    static Double res;
    private static double evaluarExpresion(String expresion) {
        op1 = "-";
        operacion="-";
        op2 = "-";
        resultado = "-";
        if (expresion.contains("+")) {
            op1 = expresion.substring(0, expresion.indexOf("+"));
            op2 = expresion.replace(op1+"+", "");
            
            operacion="+";
            res =   Double.parseDouble(op1) + Double.parseDouble(op2);
            resultado = res+"";
            return res;
        } else if (expresion.contains("-")) {
            op1 = expresion.substring(0, expresion.indexOf("-"));
            op2 = expresion.replace(op1+"-", "");

            operacion="-";
            res =   Double.parseDouble(op1) - Double.parseDouble(op2);
            resultado = res+"";
            return res;
        } else if (expresion.contains("*")) {
            op1 = expresion.substring(0, expresion.indexOf("*"));
            op2 = expresion.replace(op1+"*", "");
            
            operacion="*";
            res =   Double.parseDouble(op1) * Double.parseDouble(op2);
            resultado = res+"";
            return res;
        } else if (expresion.contains("/")) {
            op1 = expresion.substring(0, expresion.indexOf("/"));
            op2 = expresion.replace(op1+"/", "");
                        operacion="/";
            res =   Double.parseDouble(op1) / Double.parseDouble(op2);
            resultado = res+"";
            return res;
        }
        return 0.0;
    }
}
