import { Component, OnInit } from '@angular/core';
import { BackPack } from '../../models/backpack.model';
import { BackpackService } from '../../services/backpack.service';
import { NgForm } from '@angular/forms';
 import { Product } from 'src/app/models/Product';
 import { ProductService } from 'src/app/services/product.service';



@Component({
  selector: 'app-create-back-pack',
  templateUrl: './create-back-pack.component.html',
  styleUrls: ['./create-back-pack.component.scss']
})


export class CreateBackPackComponent implements OnInit {
products:Product[] = [];
  backpack: BackPack = new BackPack;
pros:Array<any>=[];
count:any = 0;
  
  addToBackpack(productId:number){
    this.pros.push(productId);
    this.backpack.productId = this.pros;
  }

  constructor(private backpackService: BackpackService, private productService:ProductService) { }

  ngOnInit(): void {
    this.resetForm();
    this.productService.getProducts().subscribe((products) => this.products = products);
  }
searched=false;
  searchProducts(searchWith: string){
    console.log(searchWith);
    this.searched=true;
    
    this.productService.searchProducts(searchWith).subscribe((products) => {
      console.log(products);
      this.products = products
    });
  }


  resetForm(form?:NgForm){
    // if(form!=null)
    //     form.reset();
    //     this.backpack={
    //       name:"",
    //       description:"",
    //       location:"",
    //       activity:"",
    //       productId:[0,0,0]
    //     }
  }

  submitData(){

                this.backpackService.createBackPack(this.backpack).subscribe((backpack)=>{
                  console.log(backpack);      
                });    
  }

  
}
