import { Component, OnInit } from '@angular/core';
import { BackPack } from 'src/app/models/backpack.model';
import { BackpackService } from 'src/app/services/backpack.service';

@Component({
  selector: 'app-view-backpacks',
  templateUrl: './view-backpacks.component.html',
  styleUrls: ['./view-backpacks.component.scss']
})
export class ViewBackpacksComponent implements OnInit {
  backpacks:BackPack[] = [];
  constructor(private backpackService:BackpackService) { }

  ngOnInit(): void {
    this.backpackService.getBackpacks().subscribe((backpacks) => this.backpacks = backpacks);
  }

  searchBackpacks(searchString: string) {
    this.backpackService.searchBackpacks(searchString).subscribe((backpacks) => {
      console.log(backpacks);
      this.backpacks = backpacks
    });

  }
  updateImages(): void{
    for(let i=0; i<this.backpacks.length; i++){
      if(this.backpacks[i].activity=="fishing"){
        this.backpacks[i].imageURL="https://freshwatervacationrentals.com/wp-content/uploads/2020/06/fishing.jpg";
      }
      else if(this.backpacks[i].activity=="hiking"){
        this.backpacks[i].imageURL="https://images.unsplash.com/photo-1551632811-561732d1e306?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8aGlraW5nfGVufDB8fDB8fA%3D%3D&w=1000&q=80";
      }
    }
  }

}
