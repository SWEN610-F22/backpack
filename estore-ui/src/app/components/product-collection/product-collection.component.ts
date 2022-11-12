import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/Product';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-collection',
  templateUrl: './product-collection.component.html',
  styleUrls: ['./product-collection.component.scss']
})
export class ProductCollectionComponent implements OnInit {
  products:Product[] = [];

  constructor(private productService:ProductService) { }

  ngOnInit(): void {
    this.productService.getProducts().subscribe((products) => this.products = products)
  }

  searchProducts(searchWith: string){
    console.log(searchWith)
    
    this.productService.searchProducts(searchWith).subscribe((products) => {
      console.log(products);
      this.products = products
    });
  }

}
