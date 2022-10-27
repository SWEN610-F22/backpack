import { Injectable } from '@angular/core';
import { Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Product } from '../models/Product';
const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
}
@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8080/products'

  constructor(private httpClient:HttpClient) { }

  getProducts(): Observable<Product[]>{
    const products =  this.httpClient.get<Product[]>(this.apiUrl);
    return products;
  }
  updateProduct(product: Product): Observable<Product[]>{
    return this.httpClient.put<Product[]>(this.apiUrl, product,httpOptions);
  
  
  }
}
