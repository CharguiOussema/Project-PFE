import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Departement} from '../Models/departement';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class DepartementService {

  path: string;
  constructor(private http: HttpClient) {
    this.path ="http://localhost:8081/Departement";
  }

  findAllDepartement(){
    return this.http.get(this.path+"/getAll");
  }
  findByEnseignantsId(id: number): any{
    return this.http.get(this.path+"/findByEnseignantsId/"+id);
  }
}
