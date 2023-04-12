package vn.funix.fx21044.java.test;

import vn.funix.fx21044.java.model.LoanAccounts;

import static org.junit.Assert.*;

public class LoanAccountsTest {
    LoanAccounts loanAccount;

    @org.junit.Before
    public void setUp() throws Exception {
        loanAccount = new LoanAccounts("111111111111","111111");
        System.out.println(100000000-99940000);
    }

    @org.junit.Test
    public void isPremium() {
        assertTrue(loanAccount.isPremium());
        loanAccount.setBalance(200000);
        assertFalse(loanAccount.isPremium());

    }

    @org.junit.Test
    public void withdraw() {
        assertTrue(loanAccount.withdraw(50000000));
        assertFalse(loanAccount.withdraw(50000000));
    }

    @org.junit.Test
    public void transactionFee() {
        assertEquals(1000, loanAccount.transactionFee(100000),0.001);
        loanAccount.setBalance(200000);
        assertEquals(5000, loanAccount.transactionFee(100000),0.001);
    }

    @org.junit.Test
    public void isAccepted() {
        //kiem tra duoi 50000
        assertTrue(loanAccount.isAccepted(40000));
        //kiem tra qua han muc 100000000
        assertFalse(loanAccount.isAccepted(100000000));
        //kiem tra han muc con lai ko qua 50000(tinh ca transactionFee)
        assertFalse(loanAccount.isAccepted(99960000));
        assertTrue(loanAccount.isAccepted(95000000));
    }
}
