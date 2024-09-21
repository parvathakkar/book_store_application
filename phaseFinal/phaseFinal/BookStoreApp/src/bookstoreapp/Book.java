/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoreapp;

import javafx.scene.control.CheckBox;

/**
 *
 * @author Tariq, Hamaad, Harsanjam, Parva
 */
public class Book {
    private String bookName;
    private double price;
    private CheckBox select;
    
    public Book(String bookName, double price) {
        this.bookName = bookName;
        this.price = price;
        this.select = new CheckBox();
    }
    
    public String getBookName() {
        return this.bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public CheckBox getSelect() {
        return this.select;
    }
    
    public void setSelect(CheckBox select) {
        this.select = select;
    }
}
