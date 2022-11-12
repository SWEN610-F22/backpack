import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/Product';
import { CartItem } from 'src/app/models/CartItem';
import { CartService } from 'src/app/services/cart.service';
import { UserStore } from 'src/app/state/user.store';


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {
  cart:Product[] = [];
  totalPrice:number = 0;
  userId:number=0;
  constructor(private cartService:CartService, private userStore: UserStore) { }


  ngOnInit(): void {
    this.userStore.getUserId().subscribe((userId) => {
      this.userId = userId != undefined ? userId : 0;
      this.cartService.getCart(this.userId).subscribe((cart) => {
        console.log(cart);
        this.cart = cart});
    })
    
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

  decrease(product: Product): void {
    const productId = (product?.id !=undefined) ? product.id : 0;
    let cartItem: CartItem = {
      userId: this.userId,
      productId: productId,
      quantity: 0
    }
    
    if(productId)this.cartService.decrease(cartItem).subscribe(()=>{
      this.cartService.getCart(this.userId).subscribe((cart) => {
        console.log(cart);
        this.cart = cart});
    });
  }

  increase(product: Product): void {
    const productId = (product?.id !=undefined) ? product.id : 0;
    let cartItem: CartItem = {
      userId: this.userId,
      productId: productId,
      quantity: 0
    }
    if(productId)this.cartService.increase(cartItem).subscribe(()=>{
      this.cartService.getCart(this.userId).subscribe((cart) => {
        console.log(cart);
        this.cart = cart});
    });
  }

  clear(product: Product): void {
    const productId = (product?.id !=undefined) ? product.id : 0;
    let cartItem: CartItem = {
      userId: this.userId,
      productId: productId,
      quantity: 0
    }
    if(productId)this.cartService.clearItem(cartItem).subscribe(()=>{
      this.cartService.getCart(this.userId).subscribe((cart) => {
        console.log(cart);
        this.cart = cart});
    });
  }


}
