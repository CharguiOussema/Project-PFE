/* tslint:disable */
import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  path: string="http://localhost:8081/";
  constructor(private http: HttpClient) { }

  login(data:{email: string, password: string}){
    return this.http.post(this.path+"login",data);
  }
}
