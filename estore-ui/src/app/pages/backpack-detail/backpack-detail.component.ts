import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BackPack } from 'src/app/models/backpack.model';
import { User } from 'src/app/models/user.model';
import { BackpackService } from 'src/app/services/backpack.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-backpack-detail',
  templateUrl: './backpack-detail.component.html',
  styleUrls: ['./backpack-detail.component.scss']
})
export class BackpackDetailComponent implements OnInit {
  backpack!:BackPack;
  user!:User;

  constructor(private route: ActivatedRoute, private backpackService:BackpackService, private userService:UserService) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.backpackService.getBackpack(id).subscribe((backpack)=>{
      this.backpack = backpack;
      this.userService.getUser(Number(backpack.userId)).subscribe((user)=>{
        this.user = user;
        console.log(user);
      })
      console.log(backpack);
    })
  }

}