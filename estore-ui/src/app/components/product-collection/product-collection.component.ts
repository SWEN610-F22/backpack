import { Component, OnInit } from '@angular/core';
import { CartItem } from 'src/app/models/CartItem';
import { Product } from 'src/app/models/Product';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-product-collection',
  templateUrl: './product-collection.component.html',
  styleUrls: ['./product-collection.component.scss']
})
export class ProductCollectionComponent implements OnInit {
  products: Product[] = [];

  constructor(private productService: ProductService, private userService:UserService, private cartService: CartService) { }

  ngOnInit(): void {
    this.productService.getProducts().subscribe((products) => this.products = products)
  }

  searchProducts(searchWith: string) {
    console.log(searchWith)

    this.productService.searchProducts(searchWith).subscribe((products) => {
      console.log(products);
      this.products = products
    });
  }


  addToCart(productId:number) {
    let userId = this.userService.getUser()!.id;
    if (userId && productId) {

      const cartItem: CartItem = {
        userId: userId,
        productId: productId,
        quantity: 1
      };
      this.cartService.addToCart(cartItem).subscribe((cartItem) => {
        console.log(cartItem)
      });

    }
  }

}
