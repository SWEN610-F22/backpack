import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/models/Product';

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
  onDelete(product: Product) {
    this.productService.deleteProduct(product).subscribe(() => (
      this.products = this.products.filter((p) => p.id !== product.id)));

  }
  onSave(product: Product): void {
    this.savedProduct = product;
    this.productService.updateProduct(this.savedProduct).subscribe((updatedProducts) => {
      this.products = updatedProducts;
    });
    this.dissapear = false;
     
  }

 
}