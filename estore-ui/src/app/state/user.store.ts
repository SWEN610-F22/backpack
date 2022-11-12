import { Injectable } from '@angular/core';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { User } from '../models/user.model';

@Injectable({
    providedIn: 'root'
})

export class UserStore{
    private user$ = new BehaviorSubject<User>({username: ''});

    constructor(){     
    }

    public getUser():Observable<User>{
        return this.user$;
    }

    public setUser(user: User){
        this.user$.next(user);
    }

    public clearUser(){
        this.user$.next({username: ''})
    }

    public isLoggedIn() : Observable<boolean>{
        return this.getUser().pipe(
            map((user) => user.username != '')
        )
    }

    public isAdminLoggedIn() : Observable<boolean>{
        return this.getUser().pipe(
            map((user) => !!user.isAdmin )
        )
    }

    public getUserName() : Observable<String>{
        return this.getUser().pipe(
            map((user) => user.username)
        )
    }

    public getUserId() : Observable<number| undefined>{
        return this.getUser().pipe(
            map((user) => user.id)
        )
    }
}

