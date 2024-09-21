/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoreapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Scanner;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;

/**
 *
 * @author Tariq, Hamaad, Harsanjam, Parva
 */
public class BookStoreApp extends Application {

    TextField txtusername = new TextField();
    TextField txtpassword = new TextField();
    Label labelWelcome = new Label("Welcome to the BookStore App");
    Label labelUser = new Label("Username:");
    Label labelPass = new Label("Password:");
    Label errorMsg = new Label();
    Label redeemMsg = new Label();
    Label invalidInput = new Label();

    Button btLogin = new Button("Login");

    ArrayList<Book> books = new ArrayList<Book>();
    String bookName;
    double bookPrice;
    ArrayList<Customer> customers = new ArrayList<Customer>();
    String username;
    String password;
    int points;
    String status;
    double totalCost;

    // Owner materials
    Button btBooks = new Button("Books");
    Button btCustomers = new Button("Customers");
    Button btOwnerLogout = new Button("Logout");
    Button btBookAdd = new Button("Add");
    Button btBookDelete = new Button("Delete");
    Button btBookBack = new Button("Back");
    Button btCustomerAdd = new Button("Add");
    Button btCustomerDelete = new Button("Delete");
    Button btCustomerBack = new Button("Back");
    Label labelBookName = new Label("Name:");
    Label labelBookPrice = new Label("Price:");
    TextField txtBookName = new TextField();
    TextField txtBookPrice = new TextField();
    Label labelCustomerName = new Label("Username");
    Label labelCustomerPass = new Label("Password:");
    TextField txtCustomerName = new TextField();
    TextField txtCustomerPass = new TextField();

    private TableView<Customer> tableCustomer = new TableView<Customer>();
    private final ObservableList<Customer> customerData = FXCollections.observableArrayList();

    // Customer materials
    Button btCustomerBuy = new Button("Buy");
    Button btCustomerRedeemBuy = new Button("Redeem points & Buy");
    Button btCustomerLogout = new Button("Logout");

    //Both
    private TableView<Book> tableBook = new TableView<Book>();
    private TableView<Book> tableBookCheck = new TableView<Book>();
    private final ObservableList<Book> bookData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("customers.txt"));

            String line = reader.readLine();
            while (line != null) {
                String info[] = line.split(", ");
                username = info[0];
                password = info[1];
                points = Integer.parseInt(info[2]);

                customers.add(new Customer(username, password, points));
                // read next line
                line = reader.readLine();
            }
            reader.close();

        } catch (Exception e) {
            System.out.println("Invalid");
        }

        for (Customer c : customers) {
            customerData.add(c);
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader("books.txt"));

            String line = reader.readLine();
            while (line != null) {
                String info[] = line.split(", ");
                bookName = info[0];
                bookPrice = Double.parseDouble(info[1]);

                books.add(new Book(bookName, bookPrice));

                // read next line 
                line = reader.readLine();
            }
            reader.close();

        } catch (Exception e) {
            System.out.println("Invalid");
        }

        for (Book b : books) {
            bookData.add(b);
        }

        tableBook.setEditable(true);
        TableColumn bookNameCol = new TableColumn("Book Name");
        TableColumn bookPriceCol = new TableColumn("Book Price");
        tableBook.getColumns().clear();
        tableBook.getColumns().addAll(bookNameCol, bookPriceCol);
        bookNameCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("bookName")
        );
        bookPriceCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("price")
        );
        tableBook.setItems(bookData);

        tableCustomer.setEditable(true);
        TableColumn customerNameCol = new TableColumn("Username");
        TableColumn customerPassCol = new TableColumn("Password");
        TableColumn customerPointsCol = new TableColumn("Points");

        customerNameCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("username")
        );
        customerPassCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("password")
        );
        customerPointsCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("points")
        );
        tableCustomer.setItems(customerData);
        tableCustomer.getColumns().clear();
        tableCustomer.getColumns().addAll(customerNameCol, customerPassCol, customerPointsCol);

        tableBookCheck.setEditable(true);
        TableColumn bookNameCheckCol = new TableColumn("Book Name");
        TableColumn bookPriceCheckCol = new TableColumn("Book Price");
        TableColumn selectCol = new TableColumn("Select");
        tableBookCheck.getColumns().clear();
        tableBookCheck.getColumns().addAll(bookNameCheckCol, bookPriceCheckCol, selectCol);
        bookNameCheckCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("bookName")
        );
        bookPriceCheckCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("price")
        );
        selectCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("select")
        );
        tableBookCheck.setItems(bookData);

        primaryStage.setOnCloseRequest(
                new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                try {
                    File file = new File("customers.txt");
                    FileWriter fw = new FileWriter(file, false);
                    PrintWriter pw = new PrintWriter(fw);

                    for (Customer c : customers) {
                        pw.print(c.getUsername() + ", " + c.getPassword() + ", " + c.getPoints() + "\n");
                    }

                    pw.close();
                } catch (IOException k) {
                    System.out.println("An error occurred.");
                    k.printStackTrace();
                }
                try {
                    File file = new File("books.txt");
                    FileWriter fw = new FileWriter(file, false);
                    PrintWriter pw = new PrintWriter(fw);

                    for (Book b : books) {
                        pw.print(b.getBookName() + ", " + b.getPrice() + "\n");
                    }
                    pw.close();
                } catch (IOException k) {
                    System.out.println("An error occurred.");
                    k.printStackTrace();
                }
            }
        }
        );

        btLogin.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String user = txtusername.getText();
                String pass = txtpassword.getText();
                int verify = 0;
                totalCost = 0;

                for (Customer v : customers) {
                    if (v.getUsername().equals(user) && v.getPassword().equals(pass)) {
                        username = v.getUsername();
                        password = v.getPassword();
                        points = v.getPoints();
                        status = v.getStatus(points);
                        verify++;
                    }
                }
                if (user.equals("admin") && pass.equals("admin")) {
                    // Owner Screen
                    primaryStage.setScene(new Scene(ownerStartScreen(), 500, 400));
                    //Books Button
                    btBooks.setOnAction(
                            new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            primaryStage.setScene(new Scene(ownerBookScreen(), 500, 700));
                        }
                    }
                    );
                    // Books Add Button
                    btBookAdd.setOnAction(
                            new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            if (isNumeric(txtBookPrice.getText())) {
                                Book book = new Book(txtBookName.getText(), Double.parseDouble(txtBookPrice.getText()));
                                tableBook.getItems().add(book);
                                books.add(book);
                                txtBookName.clear();
                                txtBookPrice.clear();
                                invalidInput.setText("");
                            }
                            else {
                                invalidInput.setText("Invalid input");
                            }
                        }
                    }
                    );
                    // Books Delete Button
                    btBookDelete.setOnAction(
                            new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            ObservableList<Book> allBooks = FXCollections.observableArrayList();
                            allBooks = tableBook.getItems();
                            Book oneBook = tableBook.getSelectionModel().getSelectedItem();
                            allBooks.remove(oneBook);
                            ArrayList<Book> updatedBooks = new ArrayList<Book>();
                            for (Book b : books) {
                                if (!b.getBookName().equals(oneBook.getBookName()) && !(b.getPrice() == oneBook.getPrice())) {
                                    updatedBooks.add(b);
                                }
                            }
                            books = updatedBooks;
                        }
                    }
                    );
                    // Books Back Button
                    btBookBack.setOnAction(
                            new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            primaryStage.setScene(new Scene(ownerStartScreen(), 500, 400));
                        }
                    }
                    );

                    //Customers Button
                    btCustomers.setOnAction(
                            new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            primaryStage.setScene(new Scene(ownerCustomerScreen(), 500, 700));
                        }
                    }
                    );
                    // Customers Add Button
                    btCustomerAdd.setOnAction(
                            new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            Customer customer = new Customer(txtCustomerName.getText(), txtCustomerPass.getText(), 0);
                            tableCustomer.getItems().add(customer);
                            customers.add(customer);
                            txtCustomerName.clear();
                            txtCustomerPass.clear();
                        }
                    }
                    );
                    // Customers Delete Button
                    btCustomerDelete.setOnAction(
                            new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            ObservableList<Customer> allCustomers;
                            allCustomers = tableCustomer.getItems();
                            Customer oneCustomer = tableCustomer.getSelectionModel().getSelectedItem();
                            allCustomers.remove(oneCustomer);

                            ArrayList<Customer> updatedCustomers = new ArrayList<Customer>();
                            for (Customer c : customers) {
                                if (!c.getUsername().equals(oneCustomer.getUsername()) && !c.getPassword().equals(oneCustomer.getPassword()) && !(c.getPoints() == (oneCustomer.getPoints()))) {
                                    updatedCustomers.add(c);
                                }
                            }

                            customers = updatedCustomers;
                        }
                    }
                    );
                    // Customers Back Button
                    btCustomerBack.setOnAction(
                            new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            primaryStage.setScene(new Scene(ownerStartScreen(), 500, 400));
                        }
                    }
                    );
                    // Logout Button
                    btOwnerLogout.setOnAction(
                            new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            primaryStage.setScene(new Scene(loginScreen(), 400, 300));
                        }
                    }
                    );
                } else if (verify == 1) {
                    username = txtusername.getText();
                    password = txtpassword.getText();
                    // Customer Screen
                    primaryStage.setScene(new Scene(customerStartScreen(), 500, 700));
                    btCustomerBuy.setOnAction(
                            new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            ObservableList<Book> allBooks = FXCollections.observableArrayList();
                            for (Book book : bookData) {
                                if (book.getSelect().isSelected()) {
                                    allBooks.add(book);
                                    totalCost += book.getPrice();
                                }
                            }
                            if (totalCost > 0) {
                                for (int i = 0; i < customers.size(); i++) {
                                    if (customers.get(i).getUsername().equals(username)) {
                                        State s;
                                        if (status.equals("Silver")) {
                                            s = new Silver();
                                        } else {
                                            s = new Gold();
                                        }
                                        customers.get(i).setStatus(s);
                                        points += customers.get(i).redeem(totalCost);
                                        status = customers.get(i).getStatus(points);
                                        customers.get(i).setPoints(points);
                                    }
                                }
                                bookData.removeAll(allBooks);
                                books.removeAll(allBooks);
                                primaryStage.setScene(new Scene(customerBuyScreen(), 300, 400));
                            } else {
                                redeemMsg.setText("No books were selected.");
                            }
                        }
                    }
                    );
                    btCustomerRedeemBuy.setOnAction(
                            new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            ObservableList<Book> allBooks = FXCollections.observableArrayList();
                            for (Book book : bookData) {
                                if (book.getSelect().isSelected()) {
                                    allBooks.add(book);
                                    totalCost += book.getPrice();
                                }
                            }
                            if (points >= 100 && totalCost > 0) {
                                for (int i = 0; i < customers.size(); i++) {
                                    if (customers.get(i).getUsername().equals(username)) {
                                        State s;
                                        if (status.equals("Silver")) {
                                            s = new Silver();
                                        } else {
                                            s = new Gold();
                                        }
                                        customers.get(i).setStatus(s);
                                        points = customers.get(i).buy(totalCost, points);
                                        totalCost = customers.get(i).getDiscount(totalCost);
                                        status = customers.get(i).getStatus(points);
                                        customers.get(i).setPoints(points);
                                    }
                                }
                                bookData.removeAll(allBooks);
                                books.removeAll(allBooks);
                                primaryStage.setScene(new Scene(customerBuyScreen(), 300, 400));
                            } else if (points < 100) {
                                redeemMsg.setText("You must have 100 points or more to redeem.");
                            } else {
                                redeemMsg.setText("No books were selected.");
                            }
                        }
                    }
                    );
                    btCustomerLogout.setOnAction(
                            new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            primaryStage.setScene(new Scene(loginScreen(), 400, 300));
                        }
                    }
                    );
                } else {
                    errorMsg.setText("Wrong username or password");
                }
            }
        }
        );
        primaryStage.setTitle("Bookstore App");
        primaryStage.setScene(new Scene(loginScreen(), 400, 300));
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);
    }

    public GridPane loginScreen() {

        GridPane gp = new GridPane();
        errorMsg.setText("");
        errorMsg.setTextFill(Color.color(1, 0, 0));
        txtusername.clear();
        txtpassword.clear();
        gp.setVgap(15);
        gp.setAlignment(Pos.CENTER);
        gp.add(labelWelcome, 0, 0);
        gp.add(labelUser, 0, 1);
        gp.add(txtusername, 1, 1);
        gp.add(labelPass, 0, 2);
        gp.add(txtpassword, 1, 2);
        gp.add(errorMsg, 1, 3);
        gp.add(btLogin, 1, 4);
        return gp;
    }

    public GridPane ownerStartScreen() {
        GridPane gp = new GridPane();
        gp.setVgap(20);
        gp.setAlignment(Pos.CENTER);
        gp.add(btBooks, 0, 0);
        gp.add(btCustomers, 0, 1);
        gp.add(btOwnerLogout, 0, 2);
        return gp;
    }

    public GridPane ownerBookScreen() {
        GridPane gp = new GridPane();
        txtBookName.clear();
        txtBookPrice.clear();
        invalidInput.setText("");
        invalidInput.setTextFill(Color.color(1, 0, 0));
        gp.setAlignment(Pos.TOP_CENTER);
        gp.setVgap(10);
        gp.setHgap(10);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(tableBook);
        //////////

        gp.add(vbox, 2, 0);
        gp.add(labelBookName, 1, 1);
        gp.add(txtBookName, 2, 1);
        gp.add(labelBookPrice, 1, 2);
        gp.add(txtBookPrice, 2, 2);
        gp.add(btBookAdd, 0, 3);
        gp.add(btBookDelete, 1, 3);
        gp.add(btBookBack, 0, 4);
        gp.add(invalidInput, 0, 5);
        return gp;
    }

    public GridPane ownerCustomerScreen() {
        GridPane gp = new GridPane();
        txtCustomerName.clear();
        txtCustomerPass.clear();
        gp.setAlignment(Pos.TOP_CENTER);
        gp.setVgap(20);
        gp.setHgap(10);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(tableCustomer);
        //////////
        gp.add(vbox, 2, 0);
        gp.add(labelCustomerName, 1, 1);
        gp.add(txtCustomerName, 2, 1);
        gp.add(labelCustomerPass, 1, 2);
        gp.add(txtCustomerPass, 2, 2);
        gp.add(btCustomerAdd, 0, 4);
        gp.add(btCustomerDelete, 1, 4);
        gp.add(btCustomerBack, 0, 5);
        return gp;
    }

    public GridPane customerStartScreen() {
        GridPane gp = new GridPane();
        redeemMsg.setText("");
        gp.setVgap(20);
        gp.setAlignment(Pos.TOP_CENTER);
        Label labelCustomerWelcome = new Label("Welcome " + username + " you have " + points + " points. Your status is: " + status);
        redeemMsg.setTextFill(Color.color(1, 0, 0));
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(tableBookCheck);
        gp.add(labelCustomerWelcome, 0, 0);
        gp.add(vbox, 0, 1);
        gp.add(btCustomerBuy, 0, 2);
        gp.add(btCustomerRedeemBuy, 0, 3);
        gp.add(btCustomerLogout, 0, 4);
        gp.add(redeemMsg, 0, 5);
        return gp;
    }

    public GridPane customerBuyScreen() {
        GridPane gp = new GridPane();
        gp.setVgap(50);
        gp.setAlignment(Pos.CENTER);
        Label labelCustomerTC = new Label("Total Cost: " + totalCost);
        Label labelCustomerPoints = new Label("Points: " + points + " Status: " + status);
        gp.add(labelCustomerTC, 0, 0);
        gp.add(labelCustomerPoints, 0, 1);
        gp.add(btCustomerLogout, 0, 2);
        return gp;
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
