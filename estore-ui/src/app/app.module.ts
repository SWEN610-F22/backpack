import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LandingComponent } from './pages/landing/landing.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ProductCardComponent } from './components/product-card/product-card.component';
import { ProductCollectionComponent } from './components/product-collection/product-collection.component';
import { SearchBarComponent } from './components/search-bar/search-bar.component';
import { RegisterComponent } from './pages/register/register.component';
import { CartComponent } from './components/cart/cart.component';
import { CartPageComponent } from './pages/cart-page/cart-page.component';
import { FormsModule } from '@angular/forms';
import { LoginComponent } from './pages/login/login.component';

import { InventoryComponent } from './pages/inventory/inventory.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material/material.module';




@NgModule({
  declarations: [
    AppComponent,
    LandingComponent,
    NavbarComponent,
    ProductCardComponent,
    ProductCollectionComponent,
    SearchBarComponent,
    RegisterComponent,
    CartComponent,
    CartPageComponent,
    LoginComponent,
    InventoryComponent,
    RegisterComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule ,
    FormsModule,
    MaterialModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
