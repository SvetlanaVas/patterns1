import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    public DataGenerator() {
    }

    public static String getPhoneNumber() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.phoneNumber().phoneNumber();
    }

    public static UserNameInfo generateByCard() {
        Faker faker = new Faker(new Locale("ru"));
        return new UserNameInfo(
                faker.name().lastName(),

                faker.name().firstName()
        );
    }

    public static String getFutureDate(int plusDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        LocalDate currentDate = LocalDate.now();
        LocalDate controlDate = currentDate.plusDays(plusDate);
        String formattedControlDate = controlDate.format(formatter);
        return formattedControlDate;
    }

    public static String getCity() {
        String[] cities = {"Москва", "Казань", "Новосибирск", "Краснодар", "Екатеринбург"};
        Random random = new Random();
        int index = random.nextInt(cities.length);
        return (cities[index]);
    }
}

