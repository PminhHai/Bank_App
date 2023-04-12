package vn.funix.fx21044.java.test;

import org.junit.Before;
import org.junit.Test;
import vn.funix.fx21044.java.exception.CustomerIdNotValidException;
import vn.funix.fx21044.java.model.Account;
import vn.funix.fx21044.java.model.Customer;
import vn.funix.fx21044.java.model.DigitalBank;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DigitalBankTest {
    ArrayList<Customer> customers;
    ArrayList<Account> accounts;
    @Before
    public void setUp() throws Exception {
        customers = new ArrayList<>();
        accounts = new ArrayList<>();
        customers.add(new Customer("happy","111111111111"));
        accounts.add((new Account("111111111111","123456",9000000)));
    }

    @Test(expected = CustomerIdNotValidException.class)
    public void getCustomerById() {
        assertNotNull(DigitalBank.getCustomerByID(customers,"111111111111"));
        DigitalBank.getCustomerByID(customers,"000000");
    }

    @Test
    public void isAccountNumberExisted() {
        assertEquals(false,DigitalBank.isAccountNumberExisted(accounts,"141651"));
        assertEquals(true, DigitalBank.isAccountNumberExisted(accounts,"123456"));
    }
    @Test(expected = CustomerIdNotValidException.class)
    public void isCustomerIdExisted() {
        DigitalBank.isCustomerIDExisted(customers,"141651");
        assertEquals(true,DigitalBank.isCustomerIDExisted(customers,"111111111111"));
    }
    @Test
    public void getAccountByAccountNumber() {
        assertNotNull(DigitalBank.getAccountByAccountNumber(accounts,"123456"));
        assertNull(DigitalBank.getAccountByAccountNumber(accounts,"000000"));
    }
}
