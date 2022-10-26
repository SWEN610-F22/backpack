import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from './user.model';
import{HttpClient, HttpHeaders} from '@angular/common/http'

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiURL = 'http://localhost:8080/users'
  
  constructor(private httpClient:HttpClient) {

   }

   createUser(user:User): Observable<User>{
    return this.httpClient.post<User>(this.apiURL,user,httpOptions);
   }
}
