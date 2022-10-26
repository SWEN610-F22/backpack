import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/Product';
import { CartItem } from 'src/app/models/CartItem';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {
  cart:Product[] = [];
  constructor(private cartService:CartService) { }

  ngOnInit(): void {
    this.cartService.getCart().subscribe((cart) => this.cart = cart);
  }

  decrease(cartItem: Product): void {
    let productId = cartItem.id;
    if(productId)this.cartService.decrease(productId).subscribe((cart) => this.cart = cart);
  }

  increase(cartItem: Product): void {
    let productId = cartItem.id;
    if(productId)this.cartService.increase(productId).subscribe((cart) => this.cart = cart);
  }

}
