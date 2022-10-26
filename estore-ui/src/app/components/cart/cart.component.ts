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
  totalPrice:number = 0;
  constructor(private cartService:CartService) { }

  ngOnInit(): void {
    this.cartService.getCart().subscribe((cart) => this.cart = cart);
  }

  updateTotalPrice(): number{
    let totalPrice = 0;
    for(let i=0; i<this.cart.length; i++){
      let thisRowTotal = (this.cart[i].price) * (this.cart[i].quantity!)
      this.cart[i].totalPrice = Number(thisRowTotal.toFixed(2));
      totalPrice += thisRowTotal;
    }
    return Number(totalPrice.toFixed(2));
  }

  decrease(cartItem: Product): void {
    let productId = cartItem.id;
    if(productId)this.cartService.decrease(productId).subscribe((cart) => this.cart = cart);
  }

  increase(cartItem: Product): void {
    let productId = cartItem.id;
    if(productId)this.cartService.increase(productId).subscribe((cart) => this.cart = cart);
  }

  clear(cartItem: Product): void {
    let productId = cartItem.id;
    if(productId)this.cartService.clearItem(productId).subscribe((cart) => this.cart = cart);
  }

}
