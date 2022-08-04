package com.flalottery.esavalidation.component;

import com.flalottery.esavalidation.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class AppComponent {

    @Autowired
    Environment environment;

    public String formatDrawTicketNo(String ticketNo) {
        return ticketNo.substring(0, 4) + "-" + ticketNo.substring(4, 13) + "-" + ticketNo.substring(13, 19);
    }

    public boolean isValidProductCode(String ticketNo, String productCode) {
        return (!ticketNo.isEmpty() && ticketNo.length() > 20) && ticketNo.substring(15, 17).equals(productCode);
    }
public String formatCashAmount(long cashAmount) {
       return this.formatCashAmount(String.valueOf(cashAmount));
}
    public String formatCashAmount(String amount) {
        double cashAmountinCents, cashAmountinDollars = 0.0;
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        try {
            if (!amount.trim().equals("")) {
                cashAmountinCents = Double.parseDouble(amount);
                if (cashAmountinCents != 0.0) {
                    cashAmountinDollars = cashAmountinCents / 100;
                }
            }
        } catch (NumberFormatException | NullPointerException e) {
            return "$0.0";
        }
        return formatter.format(cashAmountinDollars);
    }

    public String epochToDate(long epochDate) {
        Date date = new Date(epochDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
        String s = simpleDateFormat.format(date);
        return s;
    }


/*    public static void main(String ar[]) {
        AppComponent app = new AppComponent();
        System.out.println(app.epochToDate(Long.parseLong("1667334610000")));
    }*/
}
