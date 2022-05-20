import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Enseignant} from '../Models/enseignant';
import {HeurePFE} from '../Models/heure-pfe';
import {ChargeHoraire} from '../Models/charge-horaire';
import {Form} from '@angular/forms';
import {Observable} from 'rxjs';
import {Mail} from '../Models/mail';

@Injectable({
  providedIn: 'root'
})
export class EnseignantService {
  path: string;

  constructor(private http: HttpClient) {
    this.path ="http://localhost:8081/Enseignant";
  }
  findEnseignantById(id: number): any{
    return this.http.get<Enseignant>(this.path+"/getById/"+id);
  }
  findAllEnseignant(){
    return this.http.get(this.path+"/getAll");
  }
  updateEnseignant(enseignant: Enseignant){
    return this.http.put(this.path+"/update",enseignant);
  }
  findEnseignantByDepartement(titre: string){
    return this.http.get(this.path+"/findByDepartement/"+titre);
  }
  addHeurePFE(id: number, heurePFE: HeurePFE){
    return this.http.post(this.path+"/addHeurePFE/"+id,heurePFE);
  }
  addHoraireSemster1(id: number, chargeS1: ChargeHoraire){
    return this.http.post(this.path+"/addHoraireSemster1/"+id,chargeS1);
  }
  addHoraireSemster2(id: number, chargeS2: ChargeHoraire){
    return this.http.post(this.path+"/addHoraireSemster2/"+id,chargeS2);
  }
  readFileEnseignant(formData: FormData): Observable<any>{
    return this.http.post(this.path+'/readEnseignantExcel',formData);
  }
  calculHeureSupEnseignant(e: Enseignant){
    return this.http.post(this.path+'/calculHeureSup',e);
  }
  downloadFile(data){
    const REQUEST_PARAMS = new  HttpParams().set('fileName',data.fileName);
    return this.http.get(this.path+'/download', {
      params: REQUEST_PARAMS,
      responseType: 'arraybuffer'
    })
  }
  
  writeFileHeuresSup(){
    return this.http.get(this.path+'/writeFileHeuresSup');
  }
  writeFileMemoire(e: Enseignant)
  {
    return this.http.post(this.path+"/writeFileMemoire",e);
  }
  deleteFile(fileName: string){
    return this.http.delete(this.path+'/deleteFile/'+fileName);
  }
  convertFile(fileName: string){
    return this.http.get(this.path+"/convertFile/"+fileName);
  }

  findByCodeColeur(){
    return this.http.get(this.path+"/findByCodeColeur");
  }
  writeFileRecap(){
    return this.http.get(this.path+"/writeFileRecap");
  }
  writeFilelistMemoire(){
    return this.http.get(this.path+"/writeFilelistMemoire");
  }
  writeFileDossierEnseignant(e: Enseignant){
    return this.http.post(this.path+"/writeFileDossierEnseignant",e);
  }
  findByActive(active: string) : any{
    return this.http.get(this.path+"/findByActive/"+active);
  }
  deleteEnseignant(id: number){
    return this.http.delete(this.path+"/delete/"+id);
  }
  sendMail(mail: Mail){
    return this.http.post(this.path+"/sendMail",mail);
  }
  addPassword(e: Enseignant){
    return this.http.put(this.path+'/addPassword',e);
  }
}
