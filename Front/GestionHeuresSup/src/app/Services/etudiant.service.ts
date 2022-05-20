import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EtudiantService {

  path: string;
  constructor(private http: HttpClient) {
    this.path ="http://localhost:8081/Etudiant";
  }
  findAllEtudiant(){
    return this.http.get(this.path+"/getAll");
  }
  findEtudiantByCin(cin: number){
    return this.http.get(this.path+"/findEtudiantByCin/"+cin);
  }
  findByParcoursTitre(titre: string){
    return this.http.get(this.path+"/findByParcoursTitre/"+titre);
  }
  }

