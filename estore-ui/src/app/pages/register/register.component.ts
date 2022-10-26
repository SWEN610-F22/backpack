import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { User } from './user.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})

export class RegisterComponent implements OnInit {

  user: User = new User;

  constructor() { }

  ngOnInit(): void {
    this.resetForm();
  }

  resetForm(form?:NgForm){
    if(form!=null)
        form.reset();
        this.user={
          UserName:""
        }
  }

submitted=false;
submitData(username: String){
this.submitted = true;       
    console.log("user: "+username);
              

            localStorage.setItem(this.user.UserName,JSON.stringify(this.user));
             let data: any = localStorage.getItem(this.user.UserName);
                    console.log(JSON.parse(data));
     
}

}