/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoreapp;

/**
 * @author Tariq, Hamaad, Harsanjam, Parva
 */
public abstract class State {
    
    public abstract int redeem(double totalCost);
    
    public abstract int buy(double totalCost, int points);
    
    public abstract double getDiscount(double totalCost);
}