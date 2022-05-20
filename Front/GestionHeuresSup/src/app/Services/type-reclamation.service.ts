import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TypeReclamationService {
  path: string;
  constructor(private http: HttpClient) {
    this.path ="http://localhost:8081/TypeReclamation";
  }

  findAll(){
    return this.http.get(this.path+"/findAll")
  }

}
