import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/Product';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss']
})
export class CheckoutComponent implements OnInit {

  cart:Product[] = [];
  inventory:Product[] = [];
  totalPrice:number = 0;
  deliveryAddress = "";
  phoneNumber = "";
  cardNumber = "";
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

  confirmCheckout(): void{
    alert("Thank you for shopping with us!");
    this.cartService.confirmCheckout().subscribe((success) => {
      if(success){
        this.cartService.getCart().subscribe((cart) => this.cart = cart);
        this.productService.getProducts().subscribe((inventory) => this.inventory = inventory);
      }
    });
    window.open("http://localhost:4200","_self");
  }

}
