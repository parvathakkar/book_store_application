/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoreapp;

/**
 * @author Tariq, Hamaad, Harsanjam, Parva
 */
public class Silver extends State{
    
    int discount;
    
    @Override
    public int redeem(double totalCost){
        int redeemedPoints = (int)Math.round(totalCost)*10;
        return redeemedPoints; 
    } 
    
    @Override
    public int buy(double totalCost, int points){

        discount = points/100;
        
        if(discount < totalCost){
            points = 0;
        }
        else {
            points -= totalCost*100; 
        } 
        return points;
    }
    
    @Override
    public double getDiscount(double totalCost) {
        return ((int)Math.round(totalCost) - discount);
    }
}
