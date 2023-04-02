import { Component, OnInit, Output } from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Subject} from 'rxjs';
import {Order} from '../models';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

	// Declare an event emitter to emit new orders
	@Output()
	onNewOrder = new Subject<Order>()

	// Declare variables to store the line items and form data
	lineItems!: FormArray;
	form!: FormGroup

	// Define a getter to return the order form data
	get value(): Order {
		return this.form.value as Order
	}

	// Define a constructor which injects a FormBuilder instance
	constructor(private fb: FormBuilder) { }

	// Define the ngOnInit lifecycle hook
	ngOnInit(): void {
		// Create the order form and assign it to the component's form property
		this.form = this.createOrderForm()
	}

	// Define a method to add a new line item to the form
	addLineItem() {
		this.lineItems.push(this.createLineItem())
	}
	
	// Define a method to remove a line item from the form
	removeLineItem(idx: number) {
		this.lineItems.removeAt(idx)
	}

	// Define a method to clear the form
	clear() {
		this.form = this.createOrderForm()
	}

	// Define a method to place the order
	placeOrder() {
		// Get the order data from the form and emit it through the event emitter
		const order = this.form.value as Order
		this.onNewOrder.next(order)
	}

	// Define a method to check if the form is invalid
	formInvalid(): boolean {
		return this.form.invalid || this.lineItems.controls.length <= 0
	}

	// Define a private method to create the order form
	private createOrderForm(): FormGroup {
		// Create a form array to hold line items
		this.lineItems = this.fb.array([])
		// Create the order form group with name, email, and line items fields
		return this.fb.group({
			name: this.fb.control<string>('', [ Validators.required ]),
			email: this.fb.control<string>('', [ Validators.required, Validators.email ]),
			lineItems: this.lineItems
		})
	}

	// Define a private method to create a line item form group
	private createLineItem(): FormGroup {
		// Create a form group with item and quantity fields
		return this.fb.group({
			item: this.fb.control<string>('', [ Validators.required ]),
			quantity: this.fb.control<number>(1, [ Validators.required, Validators.min(1) ])
		})
	}

}
