import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { UserStore } from 'src/app/services/user.store';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/user.model';
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  public username!:String;
  public isLoggedIn:boolean = false;
  public isAdminLoggedIn:boolean = false;

  constructor(private userService:UserService,private router:Router, private userStore: UserStore) { }

  ngOnInit(): void {
    this.userStore.getUserName().subscribe((username) => this.username = username)
    this.userStore.isLoggedIn().subscribe(isLoggedIn => this.isLoggedIn = isLoggedIn)
    this.userStore.isAdminLoggedIn().subscribe(isAdminLoggedIn => this.isAdminLoggedIn = isAdminLoggedIn)
  }


  logout(){
    this.userStore.clearUser();
  }
  

}