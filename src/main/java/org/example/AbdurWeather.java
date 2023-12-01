package org.example;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AbdurWeather {
    private static final List<String> mycities = new ArrayList<>(); // Used List to store the Favorite cities in 'mycicties'
    private static final int max = 3;// Declaring the Maximum capacity

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // Used Scanner for user Input.
        System.out.println(" ########################################\n " +
                "Welcome to Abdur's Weather App \n (Don't let the weather ruins your plans)\n " +
                "########################################"); // My Application name . It will display only once when you start the execution
        while (true) { // While loop to Iterate the process, it avoids re-executing the process
            System.out.println("**************** Enter the Number of your choice *************");
            System.out.println("1. Find the Weather of your City\n 2. Show Favourite Cities\n 3. Add to your Favourites \n 4. Manage your Favourite Cities\n 5. Exit");
            Scanner choice = new Scanner(System.in);
            int selection = choice.nextInt();
            switch (selection) { // Used Switch case for Selection in the menu
                case 1:
                    findWeather();
                    break;
                case 2:
                    showCities(sc);
                    break;
                case 3:
                    addCities(sc);
                    break;
                case 4:
                    manageCities(sc);
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid Choice, Please select a number between 1 and 5");
            }


        }
    }

    // This function is used to find the weather of the city which you want.
    private static void findWeather() {
        try {
            System.out.print("Enter the name of the city: ");
            String city = new BufferedReader(new InputStreamReader(System.in)).readLine();

            String apiKey = "54c075e9e83a71509571f75dfe9b85ae";
            String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                // Parse the JSON response, as JSON is in readable format.
                String weatherDetails = parseWeather(response.toString());

                // Display the weather - Temparature
                System.out.println("Current Weather in " + city + ":");
                System.out.println(weatherDetails + " Celsius");
            } else {
                System.out.println("City not found. HTTP Response Code: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function used to parse the weather details from JSON response
    private static String parseWeather(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);

            if (jsonObject.has("main")) {
                JSONObject main = jsonObject.getJSONObject("main");
                if (main.has("temp")) {
                    double temperature = main.getDouble("temp");

                    return Double.toString(temperature - 273.15);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Temperature not found";
    }

    //Function to Display your Favorite Cities
    private static void showCities(Scanner scanner) {
        if (mycities.isEmpty()) {
            System.out.println("You have No favorite cities added");
        } else {
            System.out.println("Your Favorite Cities :");
            for (String city : mycities) {
                findWeatherOfFavoriteCities(city);
            }
        }
    }

    // Function to find the weather of Favorite cities
    private static void findWeatherOfFavoriteCities(String city) {
        try {

            String apiKey = "54c075e9e83a71509571f75dfe9b85ae";
            String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                // Parse the JSON response, as JSON is in readable format.
                String weatherDetails = parseWeather(response.toString());

                // Display the weather - Temparature
                System.out.println("Current Weather in " + city + ":");
                System.out.println(weatherDetails + " Celsius");
            } else {
                System.out.println("City not found. HTTP Response Code: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Function to Add your Favorite Cities
    private static void addCities(Scanner sc) {
        if (mycities.size() >= max) {
            System.out.println("You reached maximum capacity of adding the cities");
            return;
        }
        System.out.println("Enter the city of name to Add");
        String city = sc.next();

        if (!mycities.contains(city)) {
            mycities.add(city);
            System.out.println(city + " added to your favorites");
        } else {
            System.out.println(city + " is already in your favorites");
        }
    }

    //Function to Manage or Delete you Favorite cities
    private static void manageCities(Scanner sc) {
        if (mycities.isEmpty()) {
            System.out.println("No favorite cities added to manage");
        }
        System.out.println("Enter the city name to remove from the list");
        System.out.println("Your favorite cities: " + mycities);
        Scanner del = new Scanner(System.in);
        String delete = del.nextLine();
        if (mycities.contains(delete)) {
            mycities.remove(delete);
            System.out.println(delete + " Removed from your favorites");
        } else {
            System.out.println(delete + " is not in your favorites");
        }
    }
}
