package vn.funix.fx21044.java.test;

import org.junit.Test;
import vn.funix.fx21044.java.model.DigitalCustomer;
import vn.funix.fx21044.java.model.LoanAccounts;
import vn.funix.fx21044.java.model.SavingAccounts;

import static org.junit.Assert.assertEquals;

public class DigitalCustomerTest {
    DigitalCustomer digitalCustomer;

    @org.junit.Before
    public void setUp() throws Exception {
        digitalCustomer = new DigitalCustomer("HappyMaker", "111111111111");
        digitalCustomer.addAccount(new LoanAccounts("111111111111","111111"));
        digitalCustomer.addAccount(new SavingAccounts("111111111111","222222", 900000));

    }

    @Test
    public void getBalance() {
        assertEquals(100900000, digitalCustomer.getBalance(), 0.001);

    }


    @Test
    public void getPremium() {
        assertEquals("Premium",digitalCustomer.isPremium());
    }
}
