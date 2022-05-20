import { Component, OnInit } from '@angular/core';
import {Enseignant} from '../Models/enseignant';
import {EnseignantService} from '../Services/enseignant.service';
import {FormControl, FormGroup} from '@angular/forms';
import {EtablissementService} from '../Services/etablissement.service';
import {Etablissement} from '../Models/etablissement';
import { saveAs } from "file-saver";
import {MatSnackBar} from '@angular/material/snack-bar';
const NIME_TYPE = {
  pdf: 'application/pdf',
  xls: 'application/vnd.ms-excel',
  xlsx: 'application/vnd.openxmlformats-officedocument.spreadsheetxml.sheet'
}
@Component({
  selector: 'app-dossier-enseignant',
  templateUrl: './dossier-enseignant.component.html',
  styleUrls: ['./dossier-enseignant.component.css']
})
export class DossierEnseignantComponent implements OnInit {
  listEnseignant: Enseignant[];
  en : Enseignant = new Enseignant();
  etablisement: Etablissement= new Etablissement();
  DossierForm= new FormGroup({
    enseignant: new FormControl()
  });
  constructor(private enseignantService: EnseignantService,
              private etablisementService: EtablissementService,
              private snackBar:MatSnackBar) { }

  ngOnInit(): void {
    this.findAllEnseignant();
    this.findEtablisement();
  }
  findAllEnseignant(){
    this.enseignantService.findByCodeColeur().subscribe(data =>{
      this.listEnseignant = data as Enseignant[];


    })
  }
  selectEnseignant(e: any) {
    this.en = e as Enseignant;
    console.log(this.en)
  }
  findEtablisement(){
    this.etablisementService.findEtablissement().subscribe(data =>{

      this.etablisement = data as Etablissement;
    })
  }
  get enseignant(){

    return this.DossierForm.get('enseignant');
  }

  total(cours: number, td: number , tp: number ) {
    return parseFloat((cours + td + tp).toFixed(3));
  }

  totalHeureEffectue(cours: number, cours2: number) {
      return parseFloat(((cours + cours2)/2).toFixed(3));
  }


  totalHeureEffectueAvecPFE(td: number, td2: number , nombre: number) {
    return parseFloat(((td + td2 + (nombre * 2))/2).toFixed(3));
  }

  downloadFileDossierEnseignant(fileName: string,e : Enseignant) {
    this.enseignantService.deleteFile(fileName).subscribe( data =>{
      setTimeout(()=>{
        this.enseignantService.writeFileDossierEnseignant(e).subscribe(res=>{
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
              }, error => {
                this.snackBar.open(" Echec de téléchargement de fichier  ","",{
                  duration:3000,
                  verticalPosition:'top',
                  panelClass: ['aa']
                });
              })
            },1000,fileName="DossierEnseignant2.pdf")
          },error => console.log("no converted becauce",error));
        },error => console.log(error))
      },1000,fileName,e)
    },error => console.log(error))
  }



  downloadFileDossierEnseignantExcel(fileName: string, e: Enseignant) {
    this.enseignantService.deleteFile(fileName).subscribe( data =>{
      setTimeout(()=>{
        this.enseignantService.writeFileDossierEnseignant(e).subscribe(res=>{
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
  /*download(fileName: string,e : Enseignant){
    this.enseignantService.writeFileMemoire(e).subscribe(res=>{console.log("test dossier enseignant created ")
    },error => console.log(error))
  }*/
}
