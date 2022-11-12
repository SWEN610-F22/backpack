import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
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
    imageUrl:"",
    quantity: 0
  }
  @Input() buttonText:string = "";
  @Output() onClickButton: EventEmitter<number> = new EventEmitter();

  constructor(private userService:UserService, private cartService:CartService) { }

  ngOnInit(): void {
  }

  onClick(productId:undefined|number){
    this.onClickButton.emit(productId);
  }


  isLoggedIn(){
    return this.userService.isLoggedIn();
  }

  isAdminLoggedIn():boolean{
    return this.userService.isAdminLoggedIn();
  }

}
