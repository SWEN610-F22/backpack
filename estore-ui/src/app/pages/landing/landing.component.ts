import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss']
})
export class LandingComponent implements OnInit {

  constructor(private userService:UserService) { }

  ngOnInit(): void {
  }

  isLoggedIn():boolean{
    return this.userService.isLoggedIn();
  }

}
