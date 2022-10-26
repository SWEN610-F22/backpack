import { Injectable } from '@angular/core';
import { Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Product } from '../models/Product';
import { CartItem } from '../models/CartItem';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private userId: number = 2;
  private apiUrl = 'http://localhost:8080/cart/user/?userId='+String(this.userId);
  private entireCartUrl = 'http://localhost:8080/cart'

  constructor(private httpClient:HttpClient) { }

  getCart(): Observable<Product[]>{
    const cart = this.httpClient.get<Product[]>(this.apiUrl);
    return cart;
  }

  getEntireCart(): Observable<CartItem[]>{
    const entireCart = this.httpClient.get<CartItem[]>(this.entireCartUrl)
    return entireCart;
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

}
