import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ParcoursService {

  path: string;
  constructor(private http: HttpClient) {
    this.path ="http://localhost:8081/Parcours";
  }
  findAllParcours(){
    return this.http.get(this.path+"/getAll");
  }
}
