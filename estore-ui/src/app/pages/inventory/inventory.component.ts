import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/models/Product';
import {Product2} from '../../models/product.model';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.scss']
})
export class InventoryComponent implements OnInit {
  products: Product[] = [];
  selectedProduct?: Product;
  savedProduct?: Product;
  dissapear = true;
  addProduct = false;
  newProduct:Product2 = new Product2;

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
      this.productService.getProducts().subscribe((products) => {
      this.products = products;
    });
  }
  onEdit(product: Product): void {
    this.selectedProduct = product;
    this.dissapear = true;
  }
  onAdd(): void {
  
    this.addProduct = true;
  }
  onDelete(product: Product): void {
    this.productService.deleteProduct().subscribe(
      (resp) => {
        console.log(resp);
        this.productService.getProducts
      },
      (err) => {
        console.log(err)
      }
    );
  
  }
  onSave(product: Product): void {
    this.savedProduct = product;
    this.productService.updateProduct(this.savedProduct).subscribe((updatedProducts) => {
      this.products = updatedProducts;
    });
    this.dissapear = false;
     
  }

  submitData() {
    
    this.productService.createProduct(this.newProduct).subscribe((product) => {
      console.log(product);
    });
    this.addProduct = false;
  }

 
}