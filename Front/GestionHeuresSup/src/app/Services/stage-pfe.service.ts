import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, pipe} from 'rxjs';
import {StagePFE} from '../Models/stage-pfe';
import {EtudiantPFE} from '../Models/etudiant-pfe';

@Injectable({
  providedIn: 'root'
})
export class StagePFEService {
  path: string;
  constructor(private http: HttpClient) {
    this.path ="http://localhost:8081/StagePFE";
  }
  findAllStagePFE(){
    return this.http.get(this.path+"/getAll");
  }
  findByEnseignantId(id: number){
    return this.http.get(this.path+"/findByEnseignantId/"+id);
  }
  findByDepartementTitre(titre: string){
    return this.http.get(this.path+"/findByDepartementTitre/"+titre);
  }
  addStagePFE(stagePfe: StagePFE){
    return this.http.post(this.path+"/add",stagePfe);
  }
  updateStagePFE(stagePfe: StagePFE){
    return this.http.put(this.path+"/update",stagePfe);
  }
  findStagePFEById(id: number){
    return this.http.get(this.path+'/findById/'+id);
  }

  affecterEtudiant1(e: EtudiantPFE, id:number){
    return this.http.put(this.path+"/affecterEtudiant1/"+id,e);
  }
  affecterEtudiant2(e: EtudiantPFE, id:number){
    return this.http.put(this.path+"/affecterEtudiant2/"+id,e);
  }
  readFileStage(formData: FormData): Observable<any>{
    return this.http.post(this.path+'/readStagePFEByExcel',formData);
  }


}
