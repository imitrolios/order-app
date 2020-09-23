import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, pipe,  } from 'rxjs';
import { map } from 'rxjs/operators';
import { Orders } from '../model/Orders';
import {AuthService} from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class OrdersClientService {

  private url = 'http://localhost:9091/order/1';


  constructor(private httpClient: HttpClient, private authService:AuthService) { }

  getOrders(): Observable<Orders> {
    let headers = new HttpHeaders({ 'Authorization': this.authService.getAuthorizationHeaderValue() });
    return this.httpClient.get(this.url, { headers }).pipe(map(orders => orders as Orders));
  }
}
