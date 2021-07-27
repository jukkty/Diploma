package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.Keys.*;

public class CardInfo {
    private final SelenideElement cardNumber = $("input[type=text][placeholder='0000 0000 0000 0000']");
    private final SelenideElement cardMonth = $("input[type=text][placeholder='08']");
    private final SelenideElement cardYear = $("input[type=text][placeholder='22']");
    private final SelenideElement cardOwner = $("fieldset div:nth-child(3) > span > span:nth-child(1) input");
    private final SelenideElement cardCVC = $("input[type=text][placeholder='999']");
    private final SelenideElement buttonNext = $(byText("Продолжить"));
    private final SelenideElement notificationError = $(".notification_status_error");
    private final SelenideElement notificationSuccess = $(".notification_status_ok");
    private final SelenideElement notificationSomethingWrong = $(byText("Неверный формат"));
    private final SelenideElement notificationAboutExpiredCard = $(byText("Истёк срок действия карты"));
    private final SelenideElement notificationAboutWrongDates = $(byText("Неверно указан срок действия карты"));
    private final SelenideElement notificationAboutEmptyInput = $(byText("Поле обязательно для заполнения"));

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
    public void SomethingWrong(){
        notificationSomethingWrong.shouldBe(Condition.visible);
    }
    public void ExpiredCard(){
        notificationAboutExpiredCard.shouldBe(Condition.visible);
    }
    public void WrongDates(){
        notificationAboutWrongDates.shouldBe(Condition.visible);
    }
    public void EmptyInput(){
        notificationSomethingWrong.shouldBe(Condition.visible);
        notificationAboutEmptyInput.shouldBe(Condition.visible);
    }
    public void NoMistakes(){
        notificationSomethingWrong.should(Condition.hidden);
        notificationAboutEmptyInput.should(Condition.hidden);
        notificationAboutExpiredCard.should(Condition.hidden);
        notificationAboutWrongDates.should(Condition.hidden);
    }
    public void ClearEverything(){
        cardNumber.sendKeys(chord(CONTROL,"a"),BACK_SPACE);
        cardMonth.sendKeys(chord(CONTROL,"a"),BACK_SPACE);
        cardYear.sendKeys(chord(CONTROL,"a"),BACK_SPACE);
        cardOwner.sendKeys(chord(CONTROL,"a"),BACK_SPACE);
        cardCVC.sendKeys(chord(CONTROL,"a"),BACK_SPACE);
    }
}
