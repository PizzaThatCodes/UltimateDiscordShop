package me.pizzathatcodes.ultimatediscordshop.database.models;

public class RedeemableItem {

    String itemStackCode;
    String code;
    String redeemedBy;
    String boughtBy;
    int amount;
    boolean redeemed;

    /**
     * Constructor for RedeemableItem
     * @param itemStackCode ItemStack to be redeemed
     * @param code Code to redeem the item
     * @param redeemedBy User who redeemed the item
     * @param boughtBy Player who bought the item
     * @param amount Amount of items to be redeemed
     * @param redeemed Whether the item has been redeemed
     */
    public RedeemableItem(String itemStackCode, String code, String redeemedBy, String boughtBy,
                          int amount, boolean redeemed) {
        this.itemStackCode = itemStackCode;
        this.code = code;
        this.redeemedBy = redeemedBy;
        this.boughtBy = boughtBy;
        this.amount = amount;
        this.redeemed = redeemed;
    }

    /**
     * Get the ItemStack to be redeemed
     * @return itemStack
     */
    public String getItemStackCode() {
        return itemStackCode;
    }

    /**
     * Get the code to redeem the item
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Get the player who redeemed the item
     * @return redeemedBy
     */
    public String getRedeemedBy() {
        return redeemedBy;
    }

    /**
     * Get the player who bought the item
     * @return boughtBy
     */
    public String getBoughtBy() {
        return boughtBy;
    }

    /**
     * Get the amount of items to be redeemed
     * @return amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Get whether the item has been redeemed
     * @return redeemed
     */
    public boolean isRedeemed() {
        return redeemed;
    }

    /**
     * Set the ItemStack to be redeemed
     * @param itemStackCode ItemStack to be redeemed
     */
    public void setItemStackCode(String itemStackCode) {
        this.itemStackCode = itemStackCode;
    }

    /**
     * Set the code to redeem the item
     * @param code Code to redeem the item
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Set the player who redeemed the item
     * @param redeemedBy User who redeemed the item
     */
    public void setRedeemedBy(String redeemedBy) {
        this.redeemedBy = redeemedBy;
    }

    /**
     * Set the player who bought the item
     * @param boughtBy Player who bought the item
     */
    public void setBoughtBy(String boughtBy) {
        this.boughtBy = boughtBy;
    }

    /**
     * Set the amount of items to be redeemed
     * @param amount Amount of items to be redeemed
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Set whether the item has been redeemed
     * @param redeemed Whether the item has been redeemed
     */
    public void setRedeemed(boolean redeemed) {
        this.redeemed = redeemed;
    }



}
