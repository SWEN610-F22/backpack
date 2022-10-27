import { Component, OnInit, Input } from '@angular/core';
import { Product } from 'src/app/models/Product';

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

  constructor() { }

  ngOnInit(): void {
  }

}
