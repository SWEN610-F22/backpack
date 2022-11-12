import { Injectable } from '@angular/core';
import { Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Product } from '../models/Product';
import { CartItem } from '../models/CartItem';
import { CartComponent } from '../components/cart/cart.component';
import { UserService } from './user.service';
import { UserStore } from '../state/user.store';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
}

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private userId: number|undefined;
  private apiUrl = 'http://localhost:8080/cart'

  constructor(private httpClient:HttpClient, private userService:UserService) {
  
   }

  getCart(userId:number): Observable<Product[]>{
    console.log(userId);
    const endpoint = 'http://localhost:8080/cart/'+String(userId);
    const cart = this.httpClient.get<Product[]>(endpoint);
    return cart;
  }


  decrease(productId:number): Observable<Product[]>{
    let urlToDecrease = "http://localhost:8080/cart/decrease?productId="+productId
    return this.httpClient.get<Product[]>(urlToDecrease);
  }

  increase(productId:number): Observable<Product[]>{
    let urlToIncrease = "http://localhost:8080/cart/increase?productId="+productId
    return this.httpClient.get<Product[]>(urlToIncrease);
  }

  clearItem(productId:number): Observable<Product[]>{
    let urlToClear = "http://localhost:8080/cart/clear?productId="+productId
    return this.httpClient.get<Product[]>(urlToClear);
  }

  addToCart(cartItem:CartItem):Observable<CartItem>{
    console.log(cartItem);
    const newCartItem = this.httpClient.post<CartItem>(this.apiUrl, cartItem, httpOptions);
    return newCartItem;
  }

}
