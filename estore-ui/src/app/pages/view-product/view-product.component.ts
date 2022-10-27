import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/Product';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';
import { UserService } from 'src/app/services/user.service';
import { CartService } from 'src/app/services/cart.service';
import { CartItem } from 'src/app/models/CartItem';

@Component({
  selector: 'app-view-product',
  templateUrl: './view-product.component.html',
  styleUrls: ['./view-product.component.scss']
})
export class ViewProductComponent implements OnInit {
  product!:Product;

  constructor( private route: ActivatedRoute, private productService:ProductService, private userService:UserService, private cartService: CartService) { }

  ngOnInit(): void {
    this.getProduct()
  }

  getProduct(){
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.productService.getProductById(id).subscribe((product)=> this.product = product)
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