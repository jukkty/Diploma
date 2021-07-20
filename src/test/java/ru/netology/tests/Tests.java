package ru.netology.tests;

import lombok.Data;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.database.DatabaseInfo;
import ru.netology.pages.ChoosingPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {

    @BeforeEach
    void setUp() {
        open("http:localhost:8080");
    }

    @AfterAll
    static void cleanDB(){
        DatabaseInfo.cleanDB();
    }


    @Test
    @DisplayName("should book for credit cards with approved status")
    public void CreditBookingApproved() {
        val choosingPage = new ChoosingPage();
        val paymentInfo = DataHelper.getCorrectInfo();
        val cardInfo = choosingPage.Credit();
        cardInfo.InputInfo(paymentInfo);
        cardInfo.EverythingIsFine();
    }

    @Test
    @DisplayName("should book for debit cards with approved status")
    public void DebitBookingApproved() {
        val choosingPage = new ChoosingPage();
        val paymentInfo = DataHelper.getCorrectInfo();
        val cardInfo = choosingPage.Debit();
        cardInfo.InputInfo(paymentInfo);
        cardInfo.EverythingIsFine();
    }

    @Test
    @DisplayName("should not book for debit with declined status") // Баг = завести ишью
    public void DebitBookingDeclined() {
        val choosingPage = new ChoosingPage();
        val paymentInfo = DataHelper.getCorrectInfoButDeclined();
        val cardInfo = choosingPage.Debit();
        cardInfo.InputInfo(paymentInfo);
        cardInfo.EverythingIsRuined();
    }

    @Test
    @DisplayName("should not book with wrong payment info")
    public void WrongPaymentInfoWillNotWork() {
        val choosingPage = new ChoosingPage();
        val paymentInfo = DataHelper.getWrongInfo();
        val cardInfo = choosingPage.Debit();
        cardInfo.InputInfo(paymentInfo);
        cardInfo.EverythingIsRuined();
    }

    @Test
    @DisplayName("should check status and amount from DB if it is debit card")
    public void CheckStatusAndAmountFromDBbyDebit() throws SQLException {
        val choosingPage = new ChoosingPage();
        val paymentInfo = DataHelper.getCorrectInfo();
        val cardInfo = choosingPage.Debit();
        cardInfo.InputInfo(paymentInfo);
        cardInfo.EverythingIsFine();
        val paymentId = DatabaseInfo.getPaymentId();
        val status = DatabaseInfo.getStatusFromDebit(paymentId);
        val paymentAmount = DatabaseInfo.getAmount(paymentId);
        assertEquals("APPROVED", status);
        assertEquals("4500000", paymentAmount);
    }

    @Test
    @DisplayName("should check status if it is credit card")
    public void CheckStatusFromDBbyCredit() throws SQLException {
        val choosingPage = new ChoosingPage();
        val paymentInfo = DataHelper.getCorrectInfo();
        val cardInfo = choosingPage.Credit();
        cardInfo.InputInfo(paymentInfo);
        cardInfo.EverythingIsFine();
        val paymentId = DatabaseInfo.getPaymentId();
        val status = DatabaseInfo.getStatusFromCredit(paymentId);
        assertEquals("APPROVED", status);
    }
}
