package mapping;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Set;

public class Mapper {
    private static JsonObject readJSON(String elementFound) {
        Gson gson = new Gson();
        JsonElement jsonObject = null;
        FileReader reader = null;
        JsonObject jsonElement = null;
        JsonArray jsonArray = null;
        JsonObject jp = null;
        JsonObject foundElement = null;
        try {
            reader = new FileReader("./resources/ObjectRepository.json");
            jsonObject = gson.fromJson(reader, JsonElement.class);
            jsonElement = jsonObject.getAsJsonObject();
            jp = jsonElement.getAsJsonObject(elementFound);
            foundElement = jp;

        } catch (FileNotFoundException e) {
            Assert.fail(e.getMessage());
        }
        return foundElement;
    }

    public static By getElementFromJSON(String elementFound) {
        Set<Map.Entry<String, JsonElement>> entries = null;
        try {
            entries = readJSON(elementFound).entrySet();
//            System.out.println("Entries: " + entries);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Assert.fail(elementFound + " is not found in ObjectRepository file.");
        }
        By by = null;
        String value = null;
        for (Map.Entry<String, JsonElement> entry : entries) {
            if (!(entry.getKey().equals("X")) & !(entry.getKey().equals("Y"))) {
                by = generateByElement(entry.getKey().toLowerCase(), entry.getValue().getAsString());
//                System.out.println("Element Key:" + entry.getKey());
//                System.out.println("Element Value:" + entry.getValue().getAsString());
//                System.out.println("by: " + by);
            }
        }
        return by;
    }

    public static By generateByElement(String byType, String byValue) {
        switch (byType) {
            case "id":
                return By.id(byValue);
            case "cssselector":
                return By.cssSelector(byValue);
            case "xpath":
                return By.xpath(byValue);
            case "classname":
                return By.className(byValue);
            case "linktext":
                return By.linkText(byValue);
            case "name":
                return By.name(byValue);
            default:
                return null;
        }
    }
}
