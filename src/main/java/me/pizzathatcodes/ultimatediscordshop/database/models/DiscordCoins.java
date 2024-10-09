package me.pizzathatcodes.ultimatediscordshop.database.models;

public class DiscordCoins {

    String userID;
    int coinAmount;

    public DiscordCoins(String userID, int coinAmount) {
        this.userID = userID;
        this.coinAmount = coinAmount;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getCoinAmount() {
        return coinAmount;
    }

    public void setCoinAmount(int coinAmount) {
        this.coinAmount = coinAmount;
    }

    public void addCoins(int amount) {
        coinAmount += amount;
    }

    public void removeCoins(int amount) {
        coinAmount -= amount;
    }



}
