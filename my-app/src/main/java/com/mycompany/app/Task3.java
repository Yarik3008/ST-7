package com.mycompany.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.json.JSONObject;
import org.json.JSONArray;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task3 {
    public static void run() {
        System.setProperty("webdriver.chrome.driver", "chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&current=cloud_cover&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms";
            driver.get(url);
            
            WebElement body = driver.findElement(By.tagName("body"));
            String jsonResponse = body.getText();
            
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject hourly = jsonObject.getJSONObject("hourly");
            
            JSONArray times = hourly.getJSONArray("time");
            JSONArray temperatures = hourly.getJSONArray("temperature_2m");
            JSONArray rains = hourly.getJSONArray("rain");
            
            System.out.println("------------------------------------------------------------");
            System.out.printf("%-20s | %-10s | %-10s%n", "Дата/Время", "Температура", "Осадки");
            System.out.println("------------------------------------------------------------");
            
            DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_DATE_TIME;
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy/HH:mm");
            
            for (int i = 0; i < times.length(); i++) {
                String timeStr = times.getString(i);
                LocalDateTime time = LocalDateTime.parse(timeStr, inputFormatter);
                double temp = temperatures.getDouble(i);
                double rain = rains.getDouble(i);
                
                System.out.printf("%-20s | %-10.1f°C | %-10.1f мм%n",
                    time.format(outputFormatter),
                    temp,
                    rain);
            }
            System.out.println("------------------------------------------------------------");
            
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}
