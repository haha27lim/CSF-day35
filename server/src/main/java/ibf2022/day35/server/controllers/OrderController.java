package ibf2022.day35.server.controllers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ibf2022.day35.server.models.Order;
import ibf2022.day35.server.services.OrderService;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@Controller
@RequestMapping(path="/order", produces=MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins="*") // Enables Cross-Origin Resource Sharing (CORS) for all origins
public class OrderController {

	// Creating a logger instance
	private Logger logger = Logger.getLogger(OrderController.class.getName());

	@Autowired
	private OrderService orderSvc;

	
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
	// Method to handle POST requests for creating new orders with JSON request payload
	public ResponseEntity<String> postOrder(@RequestBody String payload) {

		// Logging the new order payload
		logger.info("New order: %s".formatted(payload));

		// Parsing the request payload to an Order object
		Order order = Order.toOrder(payload);

		// Creating the new order with the help of OrderService
		String orderId = orderSvc.createOrder(order);
		// Creating a JSON response object with orderId
		JsonObject resp = Json.createObjectBuilder()
			.add("orderId", orderId)
			.build();

		// Returning the response as JSON string with 200 OK status
		return ResponseEntity.ok(resp.toString());
	}
}
