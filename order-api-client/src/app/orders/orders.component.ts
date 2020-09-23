import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { Orders } from '../model/Orders';
import { OrdersClientService } from '../services/orders-client.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit, OnDestroy {

  orders: Orders;
  subscription = new Subscription();
  constructor(private ordersClientService: OrdersClientService) { }

  ngOnInit() {
    this.subscription.add(this.ordersClientService.getOrders().subscribe(orders => this.orders = orders));
  }

  ngOnDestroy(){
    this.subscription.unsubscribe();
  }

}
