package ru.javawebinar.topjava;

import java.sql.*;

public class SimpleMain {
    private static Connection connection = null;
    private static final String username = "postgres";
    private static final String password = "";
    private static final String URL = "jdbc:postgresql://localhost:5433/topjava";

    public static void main(String[] args) {
        System.out.println("Url = " + URL);
//        DriverManager.registerDriver();
        try {
            connection = DriverManager.getConnection(URL, username,password);
            if(connection == null){
                System.out.println("connection = null !");
            } else{
                System.out.println("connection is made ... ");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from users");
                int numberColumns = resultSet.getMetaData().getColumnCount();
                while (resultSet.next()){
                    for(int i = 1; i<= numberColumns; i++ ){
                        System.out.print(resultSet.getString(i) + "\t");
                    }
                    System.out.println();
                }
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Failed connection!");
            e.printStackTrace();
        }
    }
}
