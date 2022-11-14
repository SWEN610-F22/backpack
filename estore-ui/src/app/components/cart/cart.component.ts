import { Component, OnInit } from '@angular/core';
import { CartItem } from 'src/app/models/CartItem';
import { Product } from 'src/app/models/Product';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';
import { UserStore } from 'src/app/state/user.store';


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {
  cart:Product[] = [];
  inventory:Product[] = [];
  totalPrice:number = 0;
  userId:number=0;
  constructor(private cartService:CartService, private userStore: UserStore, private productService: ProductService) { }


  ngOnInit(): void {
    this.userStore.getUserId().subscribe((userId) => {
      this.userId = userId != undefined ? userId : 0;
      this.cartService.getCart(this.userId).subscribe((cart) => {
        console.log(cart);
        this.cart = cart});
    })
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

  increase(product: Product): void {
    const productId = (product?.id !=undefined) ? product.id : 0;
    if(!(this.isProductInInventory(productId!))){
      alert(product.name+" is currently out of stock. Please delete from cart before proceeding.");
      return;
    }
    let quantityInInventory = this.getInventoryQuantity(productId!);
    if(product.quantity>quantityInInventory){
      alert(product.name+" amount exceeds stock. Please decrease amount in cart to "+quantityInInventory+" or lower.");
      return;
    }
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
