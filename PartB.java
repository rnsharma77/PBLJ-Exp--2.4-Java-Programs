import java.sql.*;
import java.util.*;

public class ProductCRUD {
    static final String URL = "jdbc:mysql://localhost:3306/college_db";
    static final String USER = "root";
    static final String PASS = "your_password";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== PRODUCT MANAGEMENT MENU ====");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> viewProducts();
                case 3 -> updateProduct();
                case 4 -> deleteProduct();
                case 5 -> {
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void addProduct() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            String query = "INSERT INTO Product VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Product ID: ");
            ps.setInt(1, sc.nextInt());
            sc.nextLine();
            System.out.print("Enter Product Name: ");
            ps.setString(2, sc.nextLine());
            System.out.print("Enter Price: ");
            ps.setDouble(3, sc.nextDouble());
            System.out.print("Enter Quantity: ");
            ps.setInt(4, sc.nextInt());

            ps.executeUpdate();
            System.out.println("✅ Product added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void viewProducts() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Product")) {

            System.out.println("\nProductID\tProductName\tPrice\tQuantity");
            System.out.println("-------------------------------------------");
            while (rs.next()) {
                System.out.printf("%d\t\t%s\t\t%.2f\t%d\n",
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updateProduct() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            con.setAutoCommit(false);
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Product ID to Update: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter new Product Name: ");
            String name = sc.nextLine();
            System.out.print("Enter new Price: ");
            double price = sc.nextDouble();
            System.out.print("Enter new Quantity: ");
            int qty = sc.nextInt();

            String query = "UPDATE Product SET ProductName=?, Price=?, Quantity=? WHERE ProductID=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, qty);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                con.commit();
                System.out.println("✅ Product updated successfully!");
            } else {
                con.rollback();
                System.out.println("❌ Product not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void deleteProduct() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            con.setAutoCommit(false);
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Product ID to Delete: ");
            int id = sc.nextInt();

            String query = "DELETE FROM Product WHERE ProductID=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                con.commit();
                System.out.println("✅ Product deleted successfully!");
            } else {
                con.rollback();
                System.out.println("❌ Product not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
