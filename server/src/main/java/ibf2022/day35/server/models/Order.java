package ibf2022.day35.server.models;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Order {

	private String orderId = "";
	private String name;
	private String email;
	private List<LineItem> lineItems = new LinkedList<>();

	public void setOrderId(String orderId) { this.orderId = orderId; }
	public String getOrderId() { return this.orderId; }

	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }

	public void setEmail(String email) { this.email = email; }
	public String getEmail() { return this.email; }

	public void setLineItems(List<LineItem> lineItems) { this.lineItems = lineItems; }
	public List<LineItem> getLineItems() { return this.lineItems; }
	public void addLineItem(LineItem lineItem) { this.lineItems.add(lineItem); }
	public void removeLineItem(int idx) { this.lineItems.remove(idx); }

	// Method to convert the object to a JSON object
	public JsonObject toJson() {
		// Create a JsonArrayBuilder to hold the line items
		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
		// Convert each line item in the list to a JSON object and add it to the JsonArrayBuilder
		lineItems.stream()
			.forEach(v -> arrBuilder.add(v.toJson()));
		// Create a JsonObjectBuilder with the order details and the JsonArrayBuilder of line items
		return Json.createObjectBuilder()
			.add("orderId", orderId)
			.add("name", name)
			.add("email", email)
			.add("lineItems", arrBuilder.build())
			.build();
	}

	// Method to convert a JSON string to an Order object
	public static Order toOrder(String j) {
		// Create a JsonReader to read the JSON string
		JsonReader reader = Json.createReader(new StringReader(j));
		// Call the toOrder method with the read JsonObject
		return toOrder(reader.readObject());
	}

	// Method to convert a JsonObject to an Order object
	public static Order toOrder(JsonObject j) {
		// Create a new Order object
		Order order = new Order();
		// Set the order ID if it is present in the JsonObject
		if (j.containsKey("orderId") && (!j.isNull("orderId")))
			order.setOrderId(j.getString("orderId"));
		order.setName(j.getString("name"));
		order.setEmail(j.getString("email"));
		// Convert the line items from the JsonObject's JsonArray to a list of LineItem objects and set it in the Order object
		List<LineItem> lineItems = j.getJsonArray("lineItems").stream()
			.map(i -> i.asJsonObject())
			.map(LineItem::toLineItem)
			.toList();
		order.setLineItems(lineItems);
		return order;
	}
}
