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

                    // ROUTE 1: Serve your index.html dashboard file
                    server.createContext("/", new HttpHandler() {
                        public void handle(HttpExchange exchange) throws IOException {
                            try {
                                byte[] htmlBytes = java.nio.file.Files
                                        .readAllBytes(java.nio.file.Paths.get("index.html"));
                                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                                exchange.sendResponseHeaders(200, htmlBytes.length);
                                OutputStream os = exchange.getResponseBody();
                                os.write(htmlBytes);
                                os.close();
                            } catch (Exception e) {
                                String errorMsg = "Missing index.html file inside project root directory!";
                                exchange.sendResponseHeaders(404, errorMsg.length());
                                OutputStream os = exchange.getResponseBody();
                                os.write(errorMsg.getBytes());
                                os.close();
                            }
                        }
                    });

                    // ROUTE 2: Serve your live database metrics stream
                    server.createContext("/api/reports", new HttpHandler() {
                        public void handle(HttpExchange exchange) throws IOException {
                            // Handle Browser Pre-flight Options Check (Mandatory for Cross-Port Requests)
                            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                                exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, OPTIONS, POST");
                                exchange.getResponseHeaders().set("Access-Control-Allow-Headers",
                                        "Content-Type, Authorization");
                                exchange.sendResponseHeaders(204, -1);
                                exchange.close();
                                return;
                            }

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
                            byte[] responseBytes = finalResponse.getBytes("UTF-8");

                            // Set strict sequence origin tags to authorize browser streams
                            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, OPTIONS, POST");
                            exchange.getResponseHeaders().set("Access-Control-Allow-Headers",
                                    "Content-Type, Authorization");
                            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");

                            exchange.sendResponseHeaders(200, responseBytes.length);
                            OutputStream os = exchange.getResponseBody();
                            os.write(responseBytes);
                            os.close();
                        }
                    });

                    server.setExecutor(null);
                    server.start();
                    System.out
                            .println("Web Server Portal is Live! Open your browser and visit: http://localhost:8080/");

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
