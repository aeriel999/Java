package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
   public static void main(String[] args) {
//        String jdbcUrl = "jdbc:mariadb://localhost:3306/java_pd112";
//        String username = "root";
//        String password = "123456";
//
//        // SQL statement to create the 'category' table
//        String createTableSQL = "CREATE TABLE category (" +
//                "id INT PRIMARY KEY AUTO_INCREMENT," +
//                "name VARCHAR(255) NOT NULL," +
//                "description VARCHAR(500)," +
//                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
//                ")";
//        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
//             Statement statement = connection.createStatement()) {
//            // Execute the SQL statement to create the table
//            statement.executeUpdate(createTableSQL);
//
//            System.out.println("Table 'category' created successfully.");
//
//        } catch (SQLException e) {
//            System.out.println("Error working database");
//        }

        Scanner scanner = new Scanner(System.in);

        int numberOfexamples = getRandom(10, 15);

        double mark = 12;

        double point = mark / (double)numberOfexamples;

        int firstNum, secondNum, userNum;

        int falseRes = 0;
        int trueRes = 0;

        String[] examples = new String[numberOfexamples];

        for (int i = 0; i < numberOfexamples; i++) {
            firstNum = getRandom(1,10);
            secondNum = getRandom(1,10);

            int res = printQuastion(firstNum, secondNum, examples, i);

            userNum = Integer.parseInt(scanner.nextLine());

            if(res != userNum)
            {
                falseRes++;
            }
            else {
                trueRes++;
            }
        }

        System.out.println("True = " + trueRes + " False = " + falseRes + " Mark = " + (int)(trueRes * point));
    }

    private static int getRandom(int min, int max){
        Random random = new Random();
        return random.nextInt(max-min)+min;
    }

    private static int printQuastion(int numberFirst, int numberSecond, String[] examples, int iteration)
    {
        String example =  numberFirst + " * " +  numberSecond + " = ";

        if(!containsString(examples, example))
        {
            examples[iteration] = example;
            System.out.printf(numberFirst + " * " +  numberSecond + " = ");

            return numberFirst * numberSecond;
        }
       else {
            return  printQuastion(++numberFirst, numberSecond, examples, iteration);
        }
    }
    private static boolean containsString(String[] array, String target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i].equals(target)) {
                return true;
            }
        }
        return false;
    }

}