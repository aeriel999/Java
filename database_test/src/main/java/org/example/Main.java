package org.example;

import com.sun.jna.platform.unix.X11;
import org.checkerframework.checker.units.qual.C;

import java.sql.*;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        String jdbcUrl = "jdbc:mariadb://localhost:3306/java_pd112";
        String username = "root";
        String password = "123456";

        Scanner scanner = new Scanner(System.in);

        String name, description, confirmation;
        int id;

        int choice = 0;

        do {
            System.out.println("=============Menu Items================");

            System.out.println("See List of Categories - Press [1]");
            System.out.println("Add New Item in Categories - Press [2]");
            System.out.println("Edit Item in Categories - Press [3]");
            System.out.println("Delete Item in Categories - Press [4]");
            System.out.println("Exit from menu - Press [0]");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("===============List of Categories================");
                    printList(jdbcUrl, username, password);
                    break;
                case 2:
                    System.out.println("Enter name of category: ");
                    name = scanner.nextLine();
                    System.out.println("Enter description for category: ");
                    description = scanner.nextLine();
                    Category category = new Category(name, description);

                    addItem(jdbcUrl, username, password, category);
                    break;

                case 3:
                    System.out.println("Enter id of category for editing: ");
                    id = Integer.parseInt(scanner.nextLine());
                    System.out.println("You want to update: ");
                    getCategoryById(jdbcUrl, username, password,id);
                    System.out.println("Enter new name for  category: ");
                    name = scanner.nextLine();
                    System.out.println("Enter new description for category: ");
                    description = scanner.nextLine();
                    updateCategory(jdbcUrl, username, password, id, name, description);
                    break;

                case 4:
                    System.out.println("Enter id of category for deleting");
                    id = Integer.parseInt(scanner.nextLine());
                    System.out.println("You want to delete: ");
                    getCategoryById(jdbcUrl, username, password,id);
                    System.out.println("Confirm of deleting press [y]: ");
                    System.out.println("Cancer - press any key: ");
                    confirmation = scanner.nextLine();
                    if(confirmation.equals("y" )  || confirmation.equals("Y" ))
                    {
                        deleteCategory(jdbcUrl, username, password, id);
                    }
                    break;

                case 0:
                    System.out.println("Exit");
                    break;

                default:
                    System.out.println("Nothing");
                    break;


            }
        } while (choice != 0);
    }

    public static class  Category
    {
        String name;
        String description;
//        public Category(  ){
//
//        }
        public Category(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }
    private static void printList(String jdbcUrl,  String username, String password) {

        String selectAllSQL = "SELECT * FROM category";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
        PreparedStatement preparedStatement = connection.prepareStatement(selectAllSQL)) {

                // Execute the select statement
                ResultSet resultSet = preparedStatement.executeQuery();

                // Iterate through the result set and print the data
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    String createdAt = resultSet.getString("created_at");

                    System.out.println("ID: " + id);
                    System.out.println("Name: " + name);
                    System.out.println("Description: " + description);
                    System.out.println("Created At: " + createdAt);
                    System.out.println("-----------------------------");


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void addItem(String jdbcUrl,  String username, String password,  Category category)
    {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

            // Inserting a row into the 'category' table
            String insertRowSQL = "INSERT INTO category (name, description) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertRowSQL)) {
                // Set values for the parameters
                preparedStatement.setString(1, category.name);
                preparedStatement.setString(2, category.description);

                // Execute the insert statement
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Row inserted successfully!");
                } else {
                    System.out.println("Failed to insert row.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Category getCategoryById(String jdbcUrl,  String username, String password, int categoryId) {
        String selectByIdSQL = "SELECT * FROM category WHERE id = ?";
        Category category = null;

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(selectByIdSQL)) {

            preparedStatement.setInt(1, categoryId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    String createdAt = resultSet.getString("created_at");

                    System.out.println("ID: " + id);
                    System.out.println("Name: " + name);
                    System.out.println("Description: " + description);
                    System.out.println("Created At: " + createdAt);
                    System.out.println("-----------------------------");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }
    public static void updateCategory(String jdbcUrl,  String username, String password, int categoryId, String newName, String newDescription) {
        String updateSQL = "UPDATE category SET name = ?, description = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newDescription);
            preparedStatement.setInt(3, categoryId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Category updated successfully.");
            } else {
                System.out.println("Category not found or no changes made.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCategory(String jdbcUrl,  String username, String password, int categoryId) {
        String deleteSQL = "DELETE FROM category WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

            preparedStatement.setInt(1, categoryId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Category deleted successfully.");
            } else {
                System.out.println("Category not found or no changes made.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}