package models;

public class DonatorTransaction {
    private String txn_id;
    private String role_id;
    private String price;
    private boolean recurring;
    private String guild_id;
    private String currency;
    private String buyer_id;
    private String status;

    public DonatorTransaction(String txn_id, String role_id, String price, boolean recurring, String guild_id,
                              String currency, String buyer_id, String status) {
        this.txn_id = txn_id;
        this.role_id = role_id;
        this.price = price;
        this.recurring = recurring;
        this.guild_id = guild_id;
        this.currency = currency;
        this.buyer_id = buyer_id;
        this.status = status;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public String getGuild_id() {
        return guild_id;
    }

    public void setGuild_id(String guild_id) {
        this.guild_id = guild_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DonatorTransaction{" +
                "txn_id='" + txn_id + '\'' +
                ", role_id='" + role_id + '\'' +
                ", price='" + price + '\'' +
                ", recurring=" + recurring +
                ", guild_id='" + guild_id + '\'' +
                ", currency='" + currency + '\'' +
                ", buyer_id='" + buyer_id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
