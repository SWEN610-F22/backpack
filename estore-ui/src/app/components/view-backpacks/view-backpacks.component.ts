import { Component, OnInit } from '@angular/core';
import { BackPack } from 'src/app/models/backpack.model';
import { BackpackService } from 'src/app/services/backpack.service';
import { UserStore } from 'src/app/state/user.store';




@Component({
  selector: 'app-view-backpacks',
  templateUrl: './view-backpacks.component.html',
  styleUrls: ['./view-backpacks.component.scss']
})
export class ViewBackpacksComponent implements OnInit {
  
  backpacks:BackPack[] = [];
  public isLoggedIn:boolean = false;
  constructor(private backpackService:BackpackService, private userStore:UserStore) { }

  ngOnInit(): void {
    this.backpackService.getBackpacks().subscribe((backpacks) => this.backpacks = backpacks);
    this.userStore.isLoggedIn().subscribe(isLoggedIn => this.isLoggedIn = isLoggedIn);
  }

  updateImages(): void{
    for(let i=0; i<this.backpacks.length; i++){
      if(this.backpacks[i].activity=="fishing" || this.backpacks[i].activity=="Fishing"){
        this.backpacks[i].imageURL="https://images.unsplash.com/photo-1638919800429-e3a11ea32321?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80";
      }
      else if(this.backpacks[i].activity=="hiking" || this.backpacks[i].activity=="Hiking"){
        this.backpacks[i].imageURL="https://images.unsplash.com/photo-1551632811-561732d1e306?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8aGlraW5nfGVufDB8fDB8fA%3D%3D&w=1000&q=80";
      }
      else if(this.backpacks[i].activity=="camping" || this.backpacks[i].activity=="Camping"){
        this.backpacks[i].imageURL="https://images.unsplash.com/photo-1508873696983-2dfd5898f08b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80";
      }
      
    }
  }

  onDelete(backpack: BackPack) {
    this.backpackService.deleteBackpack(backpack).subscribe(() => (
      this.backpacks = this.backpacks.filter((b) => b.id !== backpack.id)));

}
}
