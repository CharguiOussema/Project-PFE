/* tslint:disable */
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }
  set(data: any){
    localStorage.setItem('token', data.token);
    localStorage.setItem('id', data.id);
    localStorage.setItem('fonction', data.fonction);
  }
  handel(data) {
    this.set(data);
  }
  getToken(){
    return localStorage.getItem('token');
  }
  getId(){
    return localStorage.getItem('id');
  }
  getFonction(){
    return localStorage.getItem('fonction');
  }
  remove(){
   /* localStorage.removeItem('token');
    localStorage.removeItem('id');
    localStorage.removeItem('fonction');*/
    localStorage.clear();
  }

  decode(payload){
    return JSON.parse(atob(payload));
  }

  payload(token){
    const payload = token.split('.')[1];
    console.log('payload : ', payload);
    return this.decode(payload);
  }

  isValid() {
    const token =this.getToken();
    const id = this.getId();
    if(token){
      const payload = this.payload(token);
      if(payload){
        return id == payload.id;
      }
    }
    return false;
  }

  getInfo(){

    const token = this.getToken();
    if(token){
      const payload = this.payload(token);
      return payload ? payload : null;
    }

    return null;
  }

  loggedIn(){
    return this.isValid();
  }
}
