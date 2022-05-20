import { Injectable } from '@angular/core';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {Enseignant} from '../Models/enseignant';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  path: string="http://localhost:8081/";
  constructor(private http: HttpClient) { }

  register(e: Enseignant){
    return this.http.post(this.path+"Enseignant/add",e);
  }
}
