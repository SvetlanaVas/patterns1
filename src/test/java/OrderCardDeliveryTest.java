import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
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


public class OrderCardDeliveryTest {
    SelenideElement form = $("form");
    SelenideElement cityForm = form.$("[data-test-id=city] input");
    SelenideElement cityClick = $(".menu");
    SelenideElement dateForm = form.$("[data-test-id=date] input");
    SelenideElement nameForm = form.$("[data-test-id=name] input");
    SelenideElement phoneForm = form.$("[data-test-id=phone] input");
    SelenideElement agreementForm = form.$("[data-test-id=agreement]");
    SelenideElement button = $$("button").find(exactText("Запланировать"));
    SelenideElement notificationSuccess = $("[data-test-id='success-notification']");
    SelenideElement replanNotification = $("[data-test-id= 'replan-notification']");
    SelenideElement replanButton = $(byText("Перепланировать"));

    /*private String getFutureDate(int plusDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        LocalDate currentDate = LocalDate.now();
        LocalDate controlDate = currentDate.plusDays(plusDate);
        String formattedControlDate = controlDate.format(formatter);
        return formattedControlDate;
    }

    private String getRandomCity() {
        String[] cities = {"Ульяновск", "Казань", "Самара", "Калуга", "Екатеринбург"};
        Random random = new Random();
        int index = random.nextInt(cities.length);
        return (cities[index]);
    }*/
    private Faker faker;

    @BeforeEach
    void setUpAll() {
        faker = new Faker(new Locale("ru"));
    }

    @BeforeEach
    void openHost() {
        open("http://localhost:9999");
    }
    @Test
    void shouldChangeDeliveryDate() {
        String randomCity = DataGenerator.getCity();
        UserNameInfo userFullName = DataGenerator.generateByCard();
        String futureDay = DataGenerator.getFutureDate(3);
        String phoneNumber = faker.phoneNumber().phoneNumber();

        cityForm.setValue(randomCity);
        cityClick.waitUntil(exist, 5000).click();
        dateForm.doubleClick().sendKeys(Keys.BACK_SPACE);
        dateForm.setValue(futureDay);
        nameForm.setValue(String.valueOf(userFullName));
        phoneForm.setValue(phoneNumber);
        agreementForm.click();
        button.click();
        notificationSuccess.waitUntil(visible, 15000);
        dateForm.doubleClick().sendKeys(Keys.BACK_SPACE);
        String newFutureDay = DataGenerator.getFutureDate(5);
        dateForm.setValue(newFutureDay);
        button.click();
        replanNotification.waitUntil(visible, 15000);
        replanButton.click();
        replanNotification.waitUntil(exist, 15000);
    }
}

