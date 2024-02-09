package org.example;

import org.example.entities.Category;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

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
                    showCategories();
                    break;
                case 2:
                    System.out.println("===============Add a Category================");
                    insertCategory();
                    break;

                case 3:
                    System.out.println("===============Edit a Category================");
                    updateCategory();
                    break;

                case 4:
                    System.out.println("===============Delete a Category================");
                    deleteCategory();
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

    private  static  void insertCategory(){
        Scanner scanner = new Scanner(System.in);
        var sf = HibernateUtil.getSessionFactory();
        Calendar calendar = Calendar.getInstance();

        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Category category = new Category();
            System.out.println("Insert Name!");
            category.setName(scanner.nextLine());
            System.out.println("Insert Photo!");
            category.setImage(scanner.nextLine());
            category.setDateCreated(calendar.getTime());
            session.save(category);
            session.getTransaction().commit();
        }
    }

    private static void showCategories(){
        SessionFactory sf = HibernateUtil.getSessionFactory();

        try(Session session = sf.openSession()) {
            session.beginTransaction();

            List<Category> Ilist = session.createQuery("from Category", Category.class).getResultList();

            for (var i : Ilist){
                System.out.println(i);
            }
            session.getTransaction().commit();
        }
    }

    private static void updateCategory() {
        Scanner scanner = new Scanner(System.in);
        var sf = HibernateUtil.getSessionFactory();

        try (Session session = sf.openSession()) {
            session.beginTransaction();

            // Assuming you have the category ID you want to edit
            System.out.println("Enter the ID of the category you want to edit:");
            int categoryId = scanner.nextInt();

            scanner.nextLine();

            Category category = session.get(Category.class, categoryId);

            if (category != null) {
                // Display the current details
                System.out.println("Current Name: " + category.getName());
                System.out.println("Current Photo: " + category.getImage());

                // Allow the user to update the details
                System.out.println("Enter new Name (or press Enter to keep the current one):");
                String newName = scanner.nextLine().trim(); // Consume the newline character
                if (!newName.isEmpty()) {
                    category.setName(newName);
                }

                System.out.println("Enter new Photo (or press Enter to keep the current one):");
                String newPhoto = scanner.nextLine().trim();
                if (!newPhoto.isEmpty()) {
                    category.setImage(newPhoto);
                }

                // Save the changes
                session.update(category);
                session.getTransaction().commit();

                System.out.println("Category updated successfully.");
            } else {
                System.out.println("Category not found with the given ID.");
            }
        }
    }

    private static void deleteCategory() {
        Scanner scanner = new Scanner(System.in);
        var sf = HibernateUtil.getSessionFactory();

        try (Session session = sf.openSession()) {
            session.beginTransaction();

            System.out.println("Enter the ID of the category you want to delete:");
            int categoryId = scanner.nextInt();

            // Consume the newline character
            scanner.nextLine();

            Category category = session.get(Category.class, categoryId);

            if (category == null) {
                System.out.println("Category not found with the given ID.");
                return;
            }

            System.out.println("Confirm of deleting press [y]: ");
            System.out.println("Cancer - press any key: ");
            String confirmation = scanner.nextLine();
            if(confirmation.equals("y" )  || confirmation.equals("Y" )) {
                session.delete(category);
                session.getTransaction().commit();
                System.out.println("Category deleted successfully.");
            } else {
                System.out.println("Deletion canceled.");
            }
        }
    }
}
