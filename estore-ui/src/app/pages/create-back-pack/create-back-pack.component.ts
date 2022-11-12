import { Component, OnInit } from '@angular/core';
import { BackPack } from '../../models/backpack.model';
import { BackpackService } from '../../services/backpack.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-create-back-pack',
  templateUrl: './create-back-pack.component.html',
  styleUrls: ['./create-back-pack.component.scss']
})
export class CreateBackPackComponent implements OnInit {

  backpack: BackPack = new BackPack;

  constructor(private backpackService: BackpackService) { }

  ngOnInit(): void {
    this.resetForm();
  }

  resetForm(form?:NgForm){
    if(form!=null)
        form.reset();
        this.backpack={
          name:"",
          description:"",
          location:"",
          activity:"",
          productId:[0,0,0]
        }
  }

  submitData(){
     

                this.backpackService.createBackPack(this.backpack).subscribe((backpack)=>{
                  console.log(backpack);      
                });    
  }
}
