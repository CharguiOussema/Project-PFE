import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FonctionService {
  path: string;
  constructor(private http: HttpClient) {
    this.path ="http://localhost:8081/Fonction";
  }

  findAllFonction(){
    return this.http.get(this.path+"/getAll");
  }
}
