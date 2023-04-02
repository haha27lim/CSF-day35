import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import {OrderComponent} from './components/order.component';
import {Order} from './models';
import {OrderService} from './order.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, AfterViewInit {

	@ViewChild(OrderComponent) // ViewChild decorator to access the OrderComponent
	orderComp!: OrderComponent // Defining orderComp as an instance of OrderComponent

	constructor(private orderSvc: OrderService) { }

	ngOnInit(): void {
		
	}

	ngAfterViewInit(): void {
	}

	// newOrder function that receives an order and sends it to the OrderService
	newOrder(order: Order) {
		// Log statement with the order details
		console.info('>>> new order: ', order)
		// Call placeOrder function of OrderService with order parameter
		this.orderSvc.placeOrder(order)
			.then(result => {
				// Alert message with the order id returned by the server
				alert(`Placed order. Order id is ${result.orderId}`)
				// Clear the order form in OrderComponent
				this.orderComp.clear()
			})
			.catch(error => {
				// Alert message with the error returned by the server
				alert(`ERROR! ${JSON.stringify(error)}`)
			})
	}
}
