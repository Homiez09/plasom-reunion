package cs211.project.services;

import java.util.Random;

public class GenerateRandomID {
    protected final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final int randomInt;
    private final StringBuilder randomText;
    public GenerateRandomID() {
        Random random = new Random();
        randomText = new StringBuilder();
        randomInt = random.nextInt(1000000);

        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomText.append(randomChar);
        }
    }

    public String getRandomText() {
        return randomText.toString() + randomInt;
    }
}
