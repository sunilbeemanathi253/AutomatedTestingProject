import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiTest {

    @Test
    public void testProductAPI() throws IOException {
        String apiUrl = "https://dummyjson.com/products";
        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        Assert.assertEquals(200, responseCode);
        System.out.println("API Response Code: " + responseCode);

        Scanner scanner = new Scanner(connection.getInputStream());
        StringBuilder jsonResponse = new StringBuilder();
        while (scanner.hasNext()) {
            jsonResponse.append(scanner.nextLine());
        }
        scanner.close();

        JSONObject jsonObject = new JSONObject(jsonResponse.toString());
        JSONArray products = jsonObject.getJSONArray("products");

        double totalPrice = 0;
        double groceryTotal = 0, beautyTotal = 0;
        int groceryCount = 0, beautyCount = 0;

        System.out.println("\nFiltered Products:");

        for (int i = 0; i < products.length(); i++) {
            JSONObject product = products.getJSONObject(i);
            String category = product.getString("category");
            double price = product.getDouble("price");

            if (category.equals("groceries") && price <= 5 && groceryCount < 3) {
                groceryTotal += price;
                groceryCount++;
                printProduct(product);
            } else if (category.equals("beauty") && price >= 5 && price <= 14 && beautyCount < 3) {
                beautyTotal += price;
                beautyCount++;
                printProduct(product);
            }

            if (groceryCount + beautyCount == 6) break;
        }

        totalPrice = groceryTotal + beautyTotal;
        System.out.printf("Total Price: $%.2f%n", totalPrice);
        System.out.printf("Average Price: $%.2f%n", totalPrice / 6);
        System.out.printf("Grocery Average: $%.2f%n", groceryTotal / groceryCount);
        System.out.printf("Beauty Average: $%.2f%n", beautyTotal / beautyCount);
    }

    private void printProduct(JSONObject product) {
        System.out.println("ID: " + product.getInt("id"));
        System.out.println("Title: " + product.getString("title"));
        System.out.println("Category: " + product.getString("category"));
        System.out.println("Price: $" + product.getDouble("price"));
        System.out.println("----------------------------");
    }
}
