package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;


public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class PaymentInfo {
        String cardNumber;
        String month;
        String year;
        String owner;
        String cvc;
    }

    public static PaymentInfo getCorrectInfo() {
        return new PaymentInfo("4444 4444 4444 4441", "12", "21", "Iachimov Dmitri", "127");
    }

    public static PaymentInfo getCorrectInfoButDeclined() {
        return new PaymentInfo("4444 4444 4444 4442", "12", "21", "Iachimov Dmitri", "127");
    }

    public static PaymentInfo getWrongInfo() {
        Faker faker = new Faker();
        return new PaymentInfo(faker.business().creditCardNumber(), "12", "21", faker.name().firstName(), "127");
    }
    public static PaymentInfo getWrongCvcCode(){
        return new PaymentInfo("4444 4444 4444 4441", "12", "21", "Iachimov Dmitri", "17");
    }

}


