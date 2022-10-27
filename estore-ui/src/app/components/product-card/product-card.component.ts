import { Component, OnInit, Input } from '@angular/core';
import { CartItem } from 'src/app/models/CartItem';
import { Product } from 'src/app/models/Product';
import { CartService } from 'src/app/services/cart.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.scss']
})
export class ProductCardComponent implements OnInit {

  @Input() product:Product={
    name:"",
    description:"",
    price:0,
    manufacturer:"",
    imageUrl:""
  }

  constructor(private userService:UserService, private cartService:CartService) { }

  ngOnInit(): void {
  }

  addToCart(){

    let userId = this.userService.getUser()!.id;
    let productId =  this.product.id;

    if(userId && productId){

      const cartItem:CartItem = {
        userId: userId,
        productId: productId,
        quantity: 1
      };
      this.cartService.addToCart(cartItem).subscribe((cartItem)=> {
        console.log(cartItem)
      });
  
    }
    
    
  }

  isLoggedIn(){
    return this.userService.isLoggedIn();
  }


}
