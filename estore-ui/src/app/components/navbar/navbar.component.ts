import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  username:string = "";

  constructor(private userService:UserService,private router:Router) { }

  ngOnInit(): void {
    if(this.userService.isLoggedIn()){
      let loggedInUsername = this.userService.getUser()?.username;
      if(loggedInUsername){
        this.username = loggedInUsername;
      }
    }
  }

  isLoggedIn(){
    return this.userService.isLoggedIn();
  }

  
  isAdminLoggedIn():boolean{
    return this.userService.isAdminLoggedIn();
  }

  logout(){
    this.userService.logout();
  }
  

}