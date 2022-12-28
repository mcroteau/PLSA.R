package shape.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Charge {
    Long id;
    String stripeId;
    BigDecimal amount;
    Business business;
    String donationDate;
    String email;
    String amountZero;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStripeId() {
        return stripeId;
    }

    public void setStripeId(String stripeId) {
        this.stripeId = stripeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getAmountZero(){
        return this.amountZero;
    }

    public void setAmountZero(BigDecimal a){
        this.amountZero = NumberFormat.getCurrencyInstance(Locale.US).format(a);
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Business getOrganization() {
        return business;
    }

    public void setOrganization(Business business) {
        this.business = business;
    }

    public String getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(String donationDate) {
        this.donationDate = donationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
