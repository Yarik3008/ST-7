package com.mycompany.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Task2 {
    public static void run() {
        System.setProperty("webdriver.chrome.driver", "chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://api.ipify.org/?format=json");
            
            WebElement body = driver.findElement(By.tagName("body"));
            String jsonResponse = body.getText();
            
            JSONObject jsonObject = new JSONObject(jsonResponse);
            String ipAddress = jsonObject.getString("ip");
            
            System.out.println("IP-адрес: " + ipAddress);
            
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}
