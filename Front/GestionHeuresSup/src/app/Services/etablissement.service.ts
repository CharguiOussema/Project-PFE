import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Etablissement} from '../Models/etablissement';

@Injectable({
  providedIn: 'root'
})
export class EtablissementService {
  path: string;
  constructor(private http: HttpClient) {
    this.path ="http://localhost:8081/Etablissement";
  }

  findEtablissement(){
    return this.http.get(this.path+"/get");
  }
  addEtablissement(e: Etablissement){
    return this.http.post(this.path+"/addEtablissement",e);
  }



}
