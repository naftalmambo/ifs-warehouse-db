import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class WarehouseApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("IFS Warehouse Management Engine Initialized!");

        while (true) {
            System.out.println("");
            System.out.println("=== WAREHOUSE MAIN MENU ===");
            System.out.println("1. View Analytical Reports");
            System.out.println("2. Exit System");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.println("Routing to Database Analytics Suite...");

                String url = "jdbc:postgresql://localhost:5432/ifs_warehouse";
                String user = "postgres";
                String password = "postgres";
                try {
                    Connection connection = DriverManager.getConnection(url, user, password);
                    Statement statement = connection.createStatement();

                    System.out.println("Database Connection Established Flawlessly!\n");

                    // 1. Pack your master analytical query text into a clean Java string
                    String query = "SELECT products.product_name, products.stock_level, " +
                            "SUM(purchase_orders.order_quantity) AS total_ordered " +
                            "FROM purchase_orders " +
                            "INNER JOIN products ON purchase_orders.product_id = products.product_id " +
                            "GROUP BY products.product_name, products.stock_level " +
                            "ORDER BY total_ordered DESC;";

                    // 2. Fire the query across the network and trap the incoming data matrix grid
                    ResultSet resultSet = statement.executeQuery(query);

                    // 3. Print a beautiful visual dashboard header grid onto the screen
                    System.out.println("----------------------------------------------------------------------");
                    System.out.printf("%-30s | %-12s | %-12s\n", "PRODUCT NAME", "STOCK LEVEL", "TOTAL ORDERED");
                    System.out.println("----------------------------------------------------------------------");

                    // 4. Loop through the rows one-by-one (Just like iterating an ArrayList in MOOC
                    // Part 3!)
                    while (resultSet.next()) {
                        String name = resultSet.getString("product_name");
                        int stock = resultSet.getInt("stock_level");
                        int ordered = resultSet.getInt("total_ordered");

                        // Print the row values cleanly aligned inside the terminal console panel
                        System.out.printf("%-30s | %-12d | %-12d\n", name, stock, ordered);
                    }
                    System.out.println("----------------------------------------------------------------------");

                    // Safely close connection lines to save memory
                    resultSet.close();
                    statement.close();
                    connection.close();

                } catch (Exception e) {
                    System.out.println("Database connection failed: " + e.getMessage());
                }

            } else if (choice.equals("2")) {
                System.out.println("Shutting down warehouse engine. Goodbye!");
                break;

            } else {
                System.out.println("Invalid selection. Please try again.");
            }
        }
    }
}
