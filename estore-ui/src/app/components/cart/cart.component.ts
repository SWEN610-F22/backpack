import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/Product';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {
  cart:Product[] = [];
  inventory:Product[] = [];
  totalPrice:number = 0;
  constructor(private cartService:CartService, private productService:ProductService) { }


  ngOnInit(): void {
    this.cartService.getCart().subscribe((cart) => this.cart = cart);
    this.productService.getProducts().subscribe((inventory) => this.inventory = inventory);
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

  isProductInInventory(productId: number): boolean{
    for(let i=0;i<this.inventory.length;i++){
      if(this.inventory[i].id==productId)return true;
    }
    return false;
  }

  getInventoryQuantity(productId: number): number{
    for(let i=0;i<this.inventory.length;i++){
      if(this.inventory[i].id==productId)return this.inventory[i].quantity;
    }
    return -1;
  }

  increase(cartItem: Product): void {
    let productId = cartItem.id;
    if(!(this.isProductInInventory(productId!))){
      alert(cartItem.name+" is currently out of stock. Please delete from cart before proceeding.");
      return;
    }
    let quantityInInventory = this.getInventoryQuantity(productId!);
    if(cartItem.quantity>quantityInInventory){
      alert(cartItem.name+" amount exceeds stock. Please decrease amount in cart to "+quantityInInventory+" or lower.");
      return;
    }
    if(productId)this.cartService.increase(productId).subscribe((cart) => this.cart = cart);
  }

  clear(cartItem: Product): void {
    let productId = cartItem.id;
    if(productId)this.cartService.clearItem(productId).subscribe((cart) => this.cart = cart);
  }

  checkout(): void{
    for(let i=0; i<this.cart.length; i++){
      if(!(this.isProductInInventory((this.cart[i].id)!))){
        alert(this.cart[i].name+" is currently out of stock. Please delete from cart before proceeding.");
        return;
      }
      let quantityInInventory = this.getInventoryQuantity(this.cart[i].id!);
      if(this.cart[i].quantity>=quantityInInventory){
        alert(this.cart[i].name+" amount exceeds stock. Please decrease amount in cart to "+quantityInInventory+" or lower.");
        return;
      }
    }
    window.open("http://localhost:4200/checkout","_self");
  }


}
