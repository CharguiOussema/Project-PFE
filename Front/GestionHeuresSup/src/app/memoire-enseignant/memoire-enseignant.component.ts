import { Component, OnInit } from '@angular/core';
import {EnseignantService} from '../Services/enseignant.service';
import {File} from '@angular/compiler-cli/src/ngtsc/file_system/testing/src/mock_file_system';
import {Enseignant} from '../Models/enseignant';
import {FormControl, FormGroup} from '@angular/forms';
import {EtablissementService} from '../Services/etablissement.service';
import {Etablissement} from '../Models/etablissement';
import { saveAs } from "file-saver";
import {TokenService} from '../Services/token.service';
import {MatSnackBar} from '@angular/material/snack-bar';
const NIME_TYPE = {
  pdf: 'application/pdf',
  xls: 'application/vnd.ms-excel',
  xlsx: 'application/vnd.openxmlformats-officedocument.spreadsheetxml.sheet'
}
@Component({
  selector: 'app-memoire-enseignant',
  templateUrl: './memoire-enseignant.component.html',
  styleUrls: ['./memoire-enseignant.component.css']
})
export class MemoireEnseignantComponent implements OnInit {
  listEnseignant: Enseignant[];
  en : Enseignant = new Enseignant();
  etablisement: Etablissement= new Etablissement();
  memoireForm = new FormGroup({
    enseignant: new FormControl()
  });



  constructor(private enseignantService: EnseignantService,
              private etablisementService: EtablissementService,
              public tokenService: TokenService,
              private snackBar:MatSnackBar) { }

  ngOnInit(): void {
    this.findAllEnseignant();
    this.findEtablisement();
    this.getEnseignant(parseInt(this.tokenService.getId()));

  }
  findAllEnseignant(){
    this.enseignantService.findByCodeColeur().subscribe(data =>{
      this.listEnseignant = data as Enseignant[];


    })
  }

  get enseignant(){

    return this.memoireForm.get('enseignant');
  }
  findEtablisement(){
    this.etablisementService.findEtablissement().subscribe(data =>{

      this.etablisement = data as Etablissement;
    })
  }

  selectEnseignant(e: any) {
this.en = e as Enseignant;
console.log(this.en)
  }
  getEnseignant(id: number){
    if(this.tokenService.getFonction()==="Enseignant"){
    this.enseignantService.findEnseignantById(id).subscribe(data =>{
    if(data.codeCouleur === "green" || data.codeCouleur === "white") {
      this.en = data as Enseignant;
    }
    })}
  }
  pourcentageAnnuelBrut(cours: number , salaireCours: number): number {
    let res = cours * salaireCours;
    return parseFloat(res.toFixed(3));
  }


  totalpourcentageAnnuelBrut(s: number, s2: number, s3: number): number {
    let res = s + s2 + s3;
    return parseFloat(res.toFixed(3));
  }

  absences(absence: number) : number{
    return parseFloat((absence/3).toFixed(3));
  }


  tauxHoraire(salaireTp: number , nombreSomaine: number) : number {
    return parseFloat((salaireTp/nombreSomaine).toFixed(3));
  }

  montantReduction(number: number, number2: number): number {
    return parseFloat((number * number2).toFixed(3));
  }

  totalMontantReduction(number: number, number2: number, number3: number): number {
    return parseFloat((number + number2 + number3).toFixed(3));
  }

  montantPayable(number: number, number2: number): number {
    return parseFloat((number - number2).toFixed(3));
  }

  totalMontantPayable(number: number, number2: number, number3: number): number {
    return parseFloat((number + number2 + number3).toFixed(3));
  }

  impotsRevenu(number: number): number{

    return parseFloat((number * 0.15).toFixed(3));
  }

  totalImpotsRevenu(number: number, number2: number, number3: number): number {
    return parseFloat((number + number2 + number3).toFixed(3));
  }

  montantNet(number: number, number2: number): number {
    return parseFloat((number - number2).toFixed(3));
  }

  totalMontantNet(number: number, number2: number, number3: number): number {
    return parseFloat((number + number2 + number3).toFixed(3));
  }

  downloadFileMemoire (fileName: string,e : Enseignant){
    this.enseignantService.deleteFile(fileName).subscribe( data =>{
      setTimeout(()=>{
        this.enseignantService.writeFileMemoire(e).subscribe(res=>{
          this.enseignantService.convertFile(fileName).subscribe(test=>{
            console.log("converted")

          setTimeout(()=>{
            const EXT = fileName.substr(fileName.lastIndexOf('.')+1);
            this.enseignantService.downloadFile({'fileName': fileName}).subscribe(data =>{
              saveAs(new Blob([data], {type: NIME_TYPE[EXT]}),fileName);
              this.snackBar.open("Téléchargement de fichier avec succés ","",{
                duration:3000,
                verticalPosition:'top',
                panelClass: ['bb']
              });
            },error => {
              this.snackBar.open(" Echec de téléchargement de fichier  ","",{
                duration:3000,
                verticalPosition:'top',
                panelClass: ['aa']
              });
              }
            )
          },1000,fileName="Memoire2.pdf")
          },error => console.log("no converted becauce",error));
        },error => console.log(error))
      },1000,fileName,e)
    },error => console.log(error))
  }

  downloadFileMemoireExcel(fileName: string, e: Enseignant) {
    this.enseignantService.deleteFile(fileName).subscribe( data =>{
      setTimeout(()=>{
        this.enseignantService.writeFileMemoire(e).subscribe(res=>{
            setTimeout(()=>{
              const EXT = fileName.substr(fileName.lastIndexOf('.')+1);
              this.enseignantService.downloadFile({'fileName': fileName}).subscribe(data =>{
                saveAs(new Blob([data], {type: NIME_TYPE[EXT]}),fileName);
                this.snackBar.open("Téléchargement de fichier avec succés ","",{
                  duration:3000,
                  verticalPosition:'top',
                  panelClass: ['bb']
                });
              },error => {
                this.snackBar.open(" Echec de téléchargement de fichier  ","",{
                  duration:3000,
                  verticalPosition:'top',
                  panelClass: ['aa']
                });
              })
            },1000,fileName)
        },error => console.log(error))
      },1000,fileName,e)
    },error => console.log(error))
  }
}
