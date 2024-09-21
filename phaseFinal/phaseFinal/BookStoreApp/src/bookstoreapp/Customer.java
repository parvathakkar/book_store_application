package bookstoreapp;//Set this to your package name

import javafx.scene.control.CheckBox;
import java.io.*; 
import javafx.stage.Stage;

/**
 *
 * @author Parva
 */
public class Customer {

    private String username;
    private String password;
    private int points;
    private State status;

    public Customer (String username, String password, int points){
        this.username = username;
        this.password = password;
        this.points = points;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }

    public int getPoints(){
        return points;
    }
    public void setPoints(int points){
        this.points = points;
    }
    
    public String getStatus(int p){
        String statusString;
        if(p < 1000){
            status = new Silver();
            statusString = "Silver";
        }else{
            status = new Gold();
            statusString = "Gold";
        }
        return statusString;
    }

    public void setStatus(State status){
        this.status = status;
    }
    
    public int buy(double totalCost, int points){
        return status.buy(totalCost, points);
    }

    public int redeem(double totalCost){
        points = status.redeem(totalCost);
        return points;
    }
    
    public double getDiscount(double totalCost) {
        return status.getDiscount(totalCost);
    }


}
