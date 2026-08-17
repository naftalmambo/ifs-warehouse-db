# IFS Industrial Warehouse Management Engine

This is a full-stack web application for warehouse reporting. It connects a frontend user interface to a Java backend server, allowing users to stream live inventory data directly from a local PostgreSQL database.

---

## 📸 System Preview

Below is a preview of the minimalist, high-contrast operations dashboard streaming live data from the backend server across port 8080:

![IFS Warehouse Dashboard Preview](---)  
_(Note: To use a custom screenshot, create a folder named `images/`, save your image inside it, and change this path to `images/your_screenshot.png`!)_

---

## 🛠️ Tech Stack

- **Operating System:** Linux Ubuntu (VirtualBox Environment)
- **Database:** PostgreSQL SQL Database
- **Backend Language:** Java (Version 11)
- **Web Server Engine:** Java Built-in HttpServer Framework
- **Frontend Interface:** HTML5 and JavaScript (Fetch API)

---

## 🗂️ Project Directory Structure

This map shows the location of every file created inside the project:

```text
~/ifs-warehouse-db/
├── index.html               # Frontend dashboard viewport page (HTML, Local CSS, JS Fetch)
├── postgresql.jar           # Database driver package file (The Translator Bridge)
├── README.md                # System documentation and setup guide
├── schema/
│   └── schema.sql           # Database table structural blueprints
├── seeding/
│   └── seeding.sql          # Core inventory data records (19 initial entries)
├── queries/
│   └── analytics.sql        # Reference SQL JOIN calculation script
└── src/
    └── WarehouseApp.java    # Monolithic Java Backend Source Code
```

---

## 📋 System Prerequisites

You must install the following software packages before running the application:

1. **Java Development Kit (JDK 11 or higher):** Required to compile and run the Java server.
   - _Ubuntu command to install:_ `sudo apt install default-jdk`
2. **PostgreSQL Server:** Required to host the local database vault.
   - _Ubuntu command to install:_ `sudo apt install postgresql postgresql-contrib`
3. **Git:** Required to clone the project files from GitHub.
   - _Ubuntu command to install:_ `sudo apt install git`

---

## 📟 Setup and Execution Guide

Follow these steps in order to download, build, and run the application:

### Step 0: Clone and Enter the Project Folder

Open your Linux terminal, download the repository, and enter the main project folder:

```bash
# Clone the repository onto your machine
git clone https://github.com

# Move into the project directory
cd ifs-warehouse-db
```

### Step 1: Initialize and Seed the Database

Run this command to create your database tables and insert the 19 default transaction records:

```bash
sudo -u postgres psql -d ifs_warehouse -f schema/schema.sql -f seeding/seeding.sql
```

### Step 2: Configure Database Password Security

Update your PostgreSQL user permissions to authorize backend data connectivity:

```bash
sudo -u postgres psql -c "ALTER USER postgres WITH PASSWORD 'postgres';"
```

### Step 3: Compile the Java Backend Code

Compile the Java source code while linking the required PostgreSQL driver package:

```bash
javac -cp .:postgresql.jar src/WarehouseApp.java
```

### Step 4: Run the Application Engine

Launch the application and open the interactive main menu:

```bash
java -cp src:postgresql.jar WarehouseApp
```

### Step 5: Start the Web Server API Panel

When the terminal menu appears, **type option 2 and press Enter** [1.13]. This instructs Java to listen for incoming web requests on port 8080 [1.13].

### Step 6: Open the Dashboard in Your Browser

You can open the user interface using **either** of the following methods:

- **Method A (Java Server):** Open a browser tab and go to: 👉 **`http://localhost:8080/`**
- **Method B (VS Code Live Server):** Right-click `index.html` in VS Code and select **Open with Live Server** to access: 👉 **`http://127.0.0.1:5500/index.html`**.

Click the **`EXECUTE LIVE PORT POLL`** button. The dashboard will instantly fetch your live database records without reloading the page [1.13]!

---

## 📊 Core Analytical Query (`queries/analytics.sql`)

This is the database query the backend executes to calculate inventory metrics across multiple tables:

```sql
SELECT products.product_name, products.stock_level,
       SUM(purchase_orders.order_quantity) AS total_ordered
FROM purchase_orders
INNER JOIN products ON purchase_orders.product_id = products.product_id
GROUP BY products.product_name, products.stock_level
ORDER BY total_ordered DESC;
```

---

## 🧠 Lessons Learned

---

## 🚀 Future Upgrades

1.  **Two-Way Data Flow (Write Access):** Add an HTML input form to allow operators to insert new records into the database directly from the browser window.
2.  **JSON Data Serialization:** Convert the Java API output from raw text tables into standard JSON format arrays.
3.  **Data Visualizations:** Connect the JSON stream to a JavaScript charting library (like Chart.js) to display dynamic inventory graphs.
