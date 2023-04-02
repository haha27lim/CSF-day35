import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {firstValueFrom} from "rxjs";
import {Order, PlaceOrderResponse} from "./models";

// Defining URL
const URL = "http://localhost:8080/order"

@Injectable()
export class OrderService {

	// Constructor that injects HttpClient
	constructor(private http: HttpClient) { }

	// placeOrder function that sends the order data to the server and returns a Promise with the response
	placeOrder(order: Order): Promise<PlaceOrderResponse> {
		return firstValueFrom(
			// Sending POST request to the URL with order data
			this.http.post<PlaceOrderResponse>(URL, order)
		)
	}
}
