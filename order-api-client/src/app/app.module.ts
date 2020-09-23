import { BrowserModule } from '@angular/platform-browser';
import { APP_INITIALIZER, NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './/app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { OrdersComponent } from './orders/orders.component';
import { OrderComponent } from './order/order.component';
import { CommonModule } from '@angular/common';
import { AuthCallbackComponent } from './auth-callback/auth-callback.component';
import { CallApiComponent } from './call-api/call-api.component';
import { ProtectedComponent } from './protected/protected.component';


@NgModule({
  declarations: [
    AppComponent,
    OrdersComponent,
    OrderComponent,
    AuthCallbackComponent,
    CallApiComponent,
    ProtectedComponent,
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [
  
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
