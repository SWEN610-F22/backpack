import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../../models/user.model';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnInit {

  user: User = new User;

  constructor(private userService: UserService, private router:Router) { 
    if (userService.isLoggedIn()) {
      router.navigate(['']);
    }
  }

  ngOnInit(): void {
    this.resetForm();
  }

  resetForm(form?:NgForm){
    if(form!=null)
        form.reset();
        this.user={
          username:""
        }
  }

doesNotExist = false;

submitData(username: String){

    // console.log("user: "+username);
              

            // localStorage.setItem(this.user.username,JSON.stringify(this.user));
            //  let data: any = localStorage.getItem(this.user.username);
            //         console.log(JSON.parse(data));

                // this.userService.createUser(this.user).subscribe((user)=>{
                //   console.log(user);
                //   if(user.username == ""){
                            
                //             this.router.navigate(['']);
                //   }
                //   else{
                //     this.doesNotExist = true;
                    
                //   }
                // });   
                
                
                

                this.userService.getUsersMatchingName(this.user.username).subscribe((users)=>{
                  users.forEach(user=>{
                    console.log(user);
                    if(user.username == this.user.username){
                      this.userService.setUser(user);
                      this.router.navigate(['']);
                      
                    }
                    
                  })

                  this.doesNotExist = true;
                 


                })
    
                
}

}