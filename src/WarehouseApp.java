import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

// Web Server API Framework Infrastructure
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class WarehouseApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("IFS Warehouse Management Engine Initialized!");

        while (true) {
            System.out.println("");
            System.out.println("=== WAREHOUSE MAIN MENU ===");
            System.out.println("1. View Analytical Reports (Terminal)");
            System.out.println("2. Start Live Web Server API Panel");
            System.out.println("3. Exit System");
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

                    String query = "SELECT products.product_name, products.stock_level, " +
                            "SUM(purchase_orders.order_quantity) AS total_ordered " +
                            "FROM purchase_orders " +
                            "INNER JOIN products ON purchase_orders.product_id = products.product_id " +
                            "GROUP BY products.product_name, products.stock_level " +
                            "ORDER BY total_ordered DESC;";

                    ResultSet resultSet = statement.executeQuery(query);

                    System.out.println("----------------------------------------------------------------------");
                    System.out.printf("%-30s | %-12s | %-12s\n", "PRODUCT NAME", "STOCK LEVEL", "TOTAL ORDERED");
                    System.out.println("----------------------------------------------------------------------");

                    while (resultSet.next()) {
                        String name = resultSet.getString("product_name");
                        int stock = resultSet.getInt("stock_level");
                        int ordered = resultSet.getInt("total_ordered");

                        System.out.printf("%-30s | %-12d | %-12d\n", name, stock, ordered);
                    }
                    System.out.println("----------------------------------------------------------------------");

                    resultSet.close();
                    statement.close();
                    connection.close();

                } catch (Exception e) {
                    System.out.println("Database connection failed: " + e.getMessage());
                }

            } else if (choice.equals("2")) {
                System.out.println("Initializing Live Web Server API Panel on Port 8080...");

                try {
                    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

                    // 2. Define the web address portal context route path with active DB query
                    server.createContext("/api/reports", new HttpHandler() {
                        public void handle(HttpExchange exchange) throws IOException {
                            StringBuilder webResponse = new StringBuilder();
                            webResponse.append("=== IFS WAREHOUSE LIVE WEB API SUITE ===\n\n");
                            webResponse.append(String.format("%-30s | %-12s | %-12s\n", "PRODUCT NAME", "STOCK LEVEL",
                                    "TOTAL ORDERED"));
                            webResponse
                                    .append("----------------------------------------------------------------------\n");

                            String url = "jdbc:postgresql://localhost:5432/ifs_warehouse";
                            String user = "postgres";
                            String password = "postgres";

                            try {
                                Connection connection = DriverManager.getConnection(url, user, password);
                                Statement statement = connection.createStatement();

                                String query = "SELECT products.product_name, products.stock_level, " +
                                        "SUM(purchase_orders.order_quantity) AS total_ordered " +
                                        "FROM purchase_orders " +
                                        "INNER JOIN products ON purchase_orders.product_id = products.product_id " +
                                        "GROUP BY products.product_name, products.stock_level " +
                                        "ORDER BY total_ordered DESC;";

                                ResultSet resultSet = statement.executeQuery(query);

                                while (resultSet.next()) {
                                    String name = resultSet.getString("product_name");
                                    int stock = resultSet.getInt("stock_level");
                                    int ordered = resultSet.getInt("total_ordered");

                                    webResponse.append(String.format("%-30s | %-12d | %-12d\n", name, stock, ordered));
                                }

                                resultSet.close();
                                statement.close();
                                connection.close();

                            } catch (Exception e) {
                                webResponse.append("Database extraction failed: ").append(e.getMessage());
                            }

                            webResponse
                                    .append("----------------------------------------------------------------------\n");

                            String finalResponse = webResponse.toString();

                            // Set Content-Type to plain text so the browser formats our table alignment
                            // nicely
                            exchange.getResponseHeaders().set("Content-Type", "text/plain");
                            exchange.sendResponseHeaders(200, finalResponse.length());
                            OutputStream os = exchange.getResponseBody();
                            os.write(finalResponse.getBytes());
                            os.close();
                        }
                    });

                    server.setExecutor(null);
                    server.start();

                    System.out.println(
                            "Web Server API is Live! Open your browser and visit: http://localhost:8080/api/reports");

                } catch (Exception e) {
                    System.out.println("Failed to launch web server engine: " + e.getMessage());
                }

            } else if (choice.equals("3")) {
                System.out.println("Shutting down warehouse engine. Goodbye!");
                System.exit(0);

            } else {
                System.out.println("Invalid selection. Please try again.");
            }
        }
    }
}
