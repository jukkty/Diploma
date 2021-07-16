package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class ChoosingPage {
    private final SelenideElement buyDebit = $(byText("Купить"));
    private final SelenideElement buyCredit = $(byText("Купить в кредит"));

    public CardInfo Debit(){
        buyDebit.click();
        return new CardInfo();
    }

    public CardInfo Credit(){
        buyCredit.click();
        return new CardInfo();
    }
}
