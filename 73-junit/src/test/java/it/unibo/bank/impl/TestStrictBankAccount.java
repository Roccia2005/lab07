package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    private static final int MONEY_TEST = 100;
    private static final int NEGATIVE_WITHDRAW = -100;
    
    public static final double TRANSACTION_FEE = 0.1;
    public static final double MANAGEMENT_FEE = 5;


    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, 0.0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(0.0, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        this.bankAccount.deposit(bankAccount.getAccountHolder().getUserID(), MONEY_TEST);
        assertEquals(1, bankAccount.getTransactionsCount());
        final double expectedBalance = MONEY_TEST - (MANAGEMENT_FEE + bankAccount.getTransactionsCount() * TRANSACTION_FEE);
        bankAccount.chargeManagementFees(mRossi.getUserID());
        assertEquals(expectedBalance, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        try{
            this.bankAccount.withdraw(bankAccount.getAccountHolder().getUserID(), NEGATIVE_WITHDRAW);
            Assertions.fail("Negative withdraw should not be possible, but it is");
        }catch(final IllegalArgumentException e){
            assertEquals(0, this.bankAccount.getBalance());
            assertEquals(0, this.bankAccount.getTransactionsCount());
            assertNotNull(e.getMessage());
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        try{
            bankAccount.withdraw(bankAccount.getAccountHolder().getUserID(), MONEY_TEST);
            Assertions.fail("Withdrawing too much money was possible, but should have thrown an exception");
        }catch(final IllegalArgumentException e){
            assertEquals(0, bankAccount.getBalance());
            assertEquals(0, bankAccount.getTransactionsCount());
            assertNotNull(e.getMessage());
        }
    }
}
