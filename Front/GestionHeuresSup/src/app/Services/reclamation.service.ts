import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Reclamation} from '../Models/reclamation';

@Injectable({
  providedIn: 'root'
})
export class ReclamationService {
  path: string;
  constructor(private http: HttpClient) {
    this.path ="http://localhost:8081/Reclamtion";
  }
  findAll(){
    return this.http.get(this.path+"findAll");
  }
  findByTypeReclamation(type :string){
    return this.http.get(this.path+"/findByTypeReclamation/"+type);
  }
  deleteReclamation(id : number){
    return this.http.delete(this.path+"/deleteReclamation/"+id);
  }
  addReclamation(r: Reclamation){
    return this.http.post(this.path+"/addReclamation",r);
  }

  findById(id: number){
    return this.http.get(this.path+"/findById/"+id);
  }
  // update
  findByDeuxType(type1: string, type2: string){
    return this.http.get(this.path+"/findByDeuxType/"+type1+"/"+type2);
  }
  findByDepartementAndDeuxType(titre: string,type1: string, type2: string ){
    return this.http.get(this.path+"/findByDepartementAndDeuxType/"+titre+"/"+type1+"/"+type2);
  }
}
