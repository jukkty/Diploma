package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class CardInfo {
    private final SelenideElement cardNumber = $("input[type=text][placeholder='0000 0000 0000 0000']");
    private final SelenideElement cardMonth = $("input[type=text][placeholder='08']");
    private final SelenideElement cardYear = $("input[type=text][placeholder='22']");
    private final SelenideElement cardOwner = $("#root > div > form > fieldset > div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    private final SelenideElement cardCVC = $("input[type=text][placeholder='999']");
    private final SelenideElement buttonNext = $(byText("Продолжить"));
    private final SelenideElement notificationError = $(".notification_status_error");
    private final SelenideElement notificationSuccess = $(".notification_status_ok");

    public void InputInfo(DataHelper.PaymentInfo paymentInfo) {
        cardNumber.setValue(paymentInfo.getCardNumber());
        cardMonth.setValue(paymentInfo.getMonth());
        cardYear.setValue(paymentInfo.getYear());
        cardOwner.setValue(paymentInfo.getOwner());
        cardCVC.setValue(paymentInfo.getCvc());
        buttonNext.click();

    }

    public void EverythingIsFine() {
        notificationSuccess.waitUntil(Condition.visible, 15000);
    }

    public void EverythingIsRuined() {
        notificationError.waitUntil(Condition.visible, 15000);

    }
}
