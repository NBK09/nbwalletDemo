package com.NBWallet.layers.api.data;

import com.NBWallet.layers.api.models.Customer;
import com.github.javafaker.Faker;

import java.util.Locale;

public class UserFactory {
    private static final Faker faker = new Faker(new Locale("en"));

    public static Customer generateCustomer() {
        Customer customer = new Customer();
        customer.setFirstName(faker.name().firstName());
        customer.setLastName(faker.name().lastName());
        customer.setEmail(faker.internet().emailAddress());
        customer.setPhoneNumber("+996" + faker.number().digits(9));
        customer.setPassword(generateStrongPassword());
        return customer;
    }

    private static String generateStrongPassword() {
        // Пример: "Abc_123@"
        String upper = faker.letterify("?").toUpperCase();
        String lower = faker.letterify("????");
        String digit = faker.numerify("##");
        return upper + lower + "_" + digit + "@";
    }
}
