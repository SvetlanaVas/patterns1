import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;


public class OrderCardDeliveryTest {
    SelenideElement form = $("form");
    SelenideElement cityInput = form.$("[data-test-id=city] input");
    SelenideElement cityClick = $(".menu");
    SelenideElement dateInput = form.$("[data-test-id=date] input");
    SelenideElement nameInput = form.$("[data-test-id=name] input");
    SelenideElement phoneInput = form.$("[data-test-id=phone] input");
    SelenideElement agreementForm = form.$("[data-test-id=agreement]");
    SelenideElement button = $$("button").find(exactText("Запланировать"));
    SelenideElement notificationSuccess = $("[data-test-id='success-notification']");
    SelenideElement replanNotification = $("[data-test-id= 'replan-notification']");
    SelenideElement planButton = $(byText("Перепланировать"));
    private Faker faker;

    @BeforeAll
    static void setUpAl() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUpAll() {
        faker = new Faker(new Locale("ru"));
    }

    @BeforeEach
    void openHost() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }



    @Test
    void shouldChangeDeliveryDate() {
        String randomCity = DataGenerator.getCity();
        UserNameInfo userFullName = DataGenerator.generateByCard();
        String futureDay = DataGenerator.getFutureDate(3);
        String phoneNumber = DataGenerator.getPhoneNumber();

        cityInput.setValue(randomCity);
        cityClick.waitUntil(exist, 5000).click();
        dateInput.doubleClick().sendKeys(Keys.BACK_SPACE);
        dateInput.setValue(futureDay);
        nameInput.setValue(String.valueOf(userFullName));
        phoneInput.setValue(phoneNumber);
        agreementForm.click();
        button.click();
        notificationSuccess.waitUntil(visible, 15000);
        dateInput.doubleClick().sendKeys(Keys.BACK_SPACE);
        String newFutureDay = DataGenerator.getFutureDate(5);
        dateInput.setValue(newFutureDay);
        button.click();
        replanNotification.waitUntil(visible, 15000);
        planButton.click();
        replanNotification.waitUntil(exist, 15000);
    }
}

