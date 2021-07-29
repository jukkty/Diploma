package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.database.DatabaseInfo;
import ru.netology.pages.ChoosingPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open("http:localhost:8080");
    }

    @AfterAll
    static void cleanDB() {
        DatabaseInfo.cleanDB();
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("should book for credit cards with approved status")
    public void CreditBookingApproved() {
        val choosingPage = new ChoosingPage();
        val paymentInfo = DataHelper.getCorrectInfo();
        val cardInfo = choosingPage.Credit();
        cardInfo.InputInfo(paymentInfo);
        cardInfo.NoMistakes();
        cardInfo.EverythingIsFine();

    }

    @Test
    @DisplayName("should book for debit cards with approved status")
    public void DebitBookingApproved() {
        val choosingPage = new ChoosingPage();
        val paymentInfo = DataHelper.getCorrectInfo();
        val cardInfo = choosingPage.Debit();
        cardInfo.InputInfo(paymentInfo);
        cardInfo.NoMistakes();
        cardInfo.EverythingIsFine();
    }

    @Test
    @DisplayName("BUG.should not book for debit with declined status") // Баг = завести ишью
    public void DebitBookingDeclined() {
        val choosingPage = new ChoosingPage();
        val paymentInfo = DataHelper.getCorrectInfoButDeclined();
        val cardInfo = choosingPage.Debit();
        cardInfo.InputInfo(paymentInfo);
        cardInfo.EverythingIsRuined();
    }

    @Test
    @DisplayName("BUG.should not book for credit with declined status") // Баг = завести ишью
    public void CreditBookingDeclined() {
        val choosingPage = new ChoosingPage();
        val paymentInfo = DataHelper.getCorrectInfoButDeclined();
        val cardInfo = choosingPage.Credit();
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
    @DisplayName("should show mistake for wrong cvc code by debit card")
    public void CheckForMistakeWithWrongCvcByDebit() {
        val choosingPage = new ChoosingPage();
        val paymentInfo = DataHelper.getWrongCvcCode();
        val cardInfo = choosingPage.Debit();
        cardInfo.InputInfo(paymentInfo);
        cardInfo.SomethingWrong();
    }

    @Test
    @DisplayName("should show mistake for wrong cvc code by credit card")
    public void CheckForMistakeWithWrongCvcByCredit() {
        val choosingPage = new ChoosingPage();
        val paymentInfo = DataHelper.getWrongCvcCode();
        val cardInfo = choosingPage.Credit();
        cardInfo.InputInfo(paymentInfo);
        cardInfo.SomethingWrong();
    }

    @Test
    @DisplayName("should show mistake for wrong card number")
    public void CheckForMistakeWithWrongCardNumber() {
        val choosingPage = new ChoosingPage();
        val paymentInfo = DataHelper.getWrongCardNumber();
        val cardInfo = choosingPage.Debit();
        cardInfo.InputInfo(paymentInfo);
        cardInfo.SomethingWrong();
    }

    @Test
    @DisplayName("should show mistake for expired card")
    public void CheckForMistakeIfCardExpired() {
        val choosingPage = new ChoosingPage();
        val paymentInfo = DataHelper.getExpiredCard();
        val cardInfo = choosingPage.Debit();
        cardInfo.InputInfo(paymentInfo);
        cardInfo.ExpiredCard();
    }

    @Test
    @DisplayName("should show mistake for wrong dates")
    public void CheckForMistakeIfWrongDates() {
        val choosingPage = new ChoosingPage();
        val paymentInfo = DataHelper.getWrongMonthAndYear();
        val cardInfo = choosingPage.Debit();
        cardInfo.InputInfo(paymentInfo);
        cardInfo.WrongDates();
    }

    @Test
    @DisplayName("should show mistake for empty data input")
    public void CheckForMistakeIfEmptyData() {
        val choosingPage = new ChoosingPage();
        val paymentInfo = DataHelper.getEmptyData();
        val cardInfo = choosingPage.Debit();
        cardInfo.InputInfo(paymentInfo);
        cardInfo.EmptyInput();
    }

    @Test
    @DisplayName("BUG.should show mistake for input digits instead of name") // Баг = завести ишью
    public void CheckForMistakeIfDigitsInsteadOfName() {
        val choosingPage = new ChoosingPage();
        val paymentInfo = DataHelper.getDigitsInsteadOfName();
        val cardInfo = choosingPage.Debit();
        cardInfo.InputInfo(paymentInfo);
        cardInfo.SomethingWrong();
    }

    @Test
    @DisplayName("BUG.should be okay to send empty data first and correct data after") // Баг = завести ишью
    public void CheckForReenterData() {
        val choosingPage = new ChoosingPage();
        val correctInfo = DataHelper.getCorrectInfo();
        val incorrectInfo = DataHelper.getEmptyData();
        val cardInfo = choosingPage.Debit();
        cardInfo.InputInfo(incorrectInfo);
        cardInfo.ClearEverything();
        cardInfo.InputInfo(correctInfo);
        cardInfo.EverythingIsFine();
        cardInfo.NoMistakes();
    }
    ///////////////////////////DATABASE TESTING////////////////////////////////////////////////////////

    @Test
    @DisplayName("should check approved status and amount for debit card")
    public void CheckApprovedStatusAndAmountByDebit() throws SQLException {
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
        System.out.println(paymentId);
    }

    @Test
    @DisplayName("should check approved status for credit card")
    public void CheckStatusByCredit() throws SQLException {
        val choosingPage = new ChoosingPage();
        val paymentInfo = DataHelper.getCorrectInfo();
        val cardInfo = choosingPage.Credit();
        cardInfo.InputInfo(paymentInfo);
        cardInfo.EverythingIsFine();
        val paymentId = DatabaseInfo.getPaymentId();
        val status = DatabaseInfo.getStatusFromCredit(paymentId);
        assertEquals("APPROVED", status);
    }

    @Test
    @DisplayName("should check declined status and amount for debit card")
    public void CheckDeclinedStatusAndAmountByDebit() throws SQLException {
        val choosingPage = new ChoosingPage();
        val paymentInfo = DataHelper.getCorrectInfoButDeclined();
        val cardInfo = choosingPage.Debit();
        cardInfo.InputInfo(paymentInfo);
        cardInfo.EverythingIsFine();
        val paymentId = DatabaseInfo.getPaymentId();
        val status = DatabaseInfo.getStatusFromDebit(paymentId);
        val paymentAmount = DatabaseInfo.getAmount(paymentId);
        assertEquals("DECLINED", status);
        assertEquals("4500000", paymentAmount);
    }

    @Test
    @DisplayName("should check declined status for credit card")
    public void CheckDeclinedStatusByCredit() throws SQLException {
        val choosingPage = new ChoosingPage();
        val paymentInfo = DataHelper.getCorrectInfoButDeclined();
        val cardInfo = choosingPage.Credit();
        cardInfo.InputInfo(paymentInfo);
        cardInfo.EverythingIsFine();
        val paymentId = DatabaseInfo.getPaymentId();
        val status = DatabaseInfo.getStatusFromCredit(paymentId);
        assertEquals("DECLINED", status);
    }
}

