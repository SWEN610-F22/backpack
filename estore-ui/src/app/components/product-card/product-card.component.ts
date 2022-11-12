import { Component, OnInit, Input } from '@angular/core';
import { CartItem } from 'src/app/models/CartItem';
import { Product } from 'src/app/models/Product';
import { CartService } from 'src/app/services/cart.service';
import { UserService } from 'src/app/services/user.service';
import { UserStore } from 'src/app/services/user.store';

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
    imageUrl:"",
    quantity: 0
  }
  public userId:number|undefined;
  public isLoggedIn:boolean = false;
  public isAdminLoggedIn:boolean = false;

  constructor(private userService:UserService, private cartService:CartService, private userStore: UserStore) { }

  ngOnInit(): void {
    this.userStore.getUserId().subscribe(userId => this.userId = userId);
    this.userStore.isLoggedIn().subscribe(isLoggedIn => this.isLoggedIn = isLoggedIn)
    this.userStore.isAdminLoggedIn().subscribe(isAdminLoggedIn => this.isAdminLoggedIn = isAdminLoggedIn)
  }

  addToCart(){

    let userId = this.userId;
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

}
