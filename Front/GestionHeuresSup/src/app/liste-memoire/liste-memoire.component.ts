import { Component, OnInit } from '@angular/core';
import {Enseignant} from '../Models/enseignant';
import {Etablissement} from '../Models/etablissement';
import {EnseignantService} from '../Services/enseignant.service';
import {EtablissementService} from '../Services/etablissement.service';
import { saveAs } from "file-saver";
import {MatSnackBar} from '@angular/material/snack-bar';
const NIME_TYPE = {
  pdf: 'application/pdf',
  xls: 'application/vnd.ms-excel',
  xlsx: 'application/vnd.openxmlformats-officedocument.spreadsheetxml.sheet'
}
@Component({
  selector: 'app-liste-memoire',
  templateUrl: './liste-memoire.component.html',
  styleUrls: ['./liste-memoire.component.css']
})
export class ListeMemoireComponent implements OnInit {
  listEnseignant: Enseignant[];
  searchEnseignant: Enseignant[] = [];
  etablisement: Etablissement = new Etablissement();
  constructor(private enseignantService: EnseignantService,
              private  etablisementService: EtablissementService,
              private snackBar:MatSnackBar) { }

  ngOnInit(): void {
    this.findAllEnseignant();
    this.findEtablisement();
  }
  search(query: string){
    this.listEnseignant = (query) ? this.searchEnseignant.filter(enseignant => enseignant.prenomNom.toLowerCase().includes(query.toLowerCase())
      || enseignant.grade?.titre.toLowerCase().includes(query.toLowerCase())
      || enseignant.rib.toString().includes(query)
    ): this.searchEnseignant;
  }
  findAllEnseignant(){
    this.enseignantService.findByCodeColeur().subscribe(data =>{
      this.listEnseignant = data as Enseignant[];
      this.searchEnseignant = data as Enseignant[];


    })
  }
  findEtablisement(){
    this.etablisementService.findEtablissement().subscribe(data =>{

      this.etablisement = data as Etablissement;
    })
  }

  pourcentageAnnuelBrut(cours: number , salaireCours: number ) {
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

  donwloadFileListMemoirePDF(fileName: string) {
    this.enseignantService.deleteFile(fileName).subscribe(data =>{
      setTimeout(()=>{
        this.enseignantService.writeFilelistMemoire().subscribe(res =>{

          this.enseignantService.convertFile(fileName).subscribe(convert =>{
            console.log("converted");

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
            },1000,fileName="TableauRecap2.pdf")
          },error => console.log(error));
        })
      },1000,fileName)
    },error => console.log(error))
  }
  donwloadFileListMemoireExcel(fileName: string){
    this.enseignantService.writeFilelistMemoire().subscribe(data =>{
      setTimeout(()=>{
        const EXT = fileName.substr(fileName.lastIndexOf('.')+1);
        this.enseignantService.downloadFile({'fileName': fileName}).subscribe(data =>{
          saveAs(new Blob([data], {type: NIME_TYPE[EXT]}),fileName);
          this.snackBar.open("Téléchargement de fichier avec succés ","",{
            duration:3000,
            verticalPosition:'top',
            panelClass: ['bb']
          });
        },error=>{
          this.snackBar.open(" Echec de téléchargement de fichier  ","",{
            duration:3000,
            verticalPosition:'top',
            panelClass: ['aa']
          });
        })
      },1000,fileName)
    },error => console.log(error))
  }
/*
// TEST long file Excel And File
  downloadExcel(fileName: string){
    const EXT = fileName.substr(fileName.lastIndexOf('.')+1);
    this.enseignantService.downloadFile({'fileName': fileName}).subscribe(data =>{
      saveAs(new Blob([data], {type: NIME_TYPE[EXT]}),fileName);
    })
  }
  downloadPDF(fileName: string){
    this.enseignantService.convertFile(fileName).subscribe(convert =>{
      console.log("converted");

      setTimeout(()=>{
        const EXT = fileName.substr(fileName.lastIndexOf('.')+1);
        this.enseignantService.downloadFile({'fileName': fileName}).subscribe(data =>{
          saveAs(new Blob([data], {type: NIME_TYPE[EXT]}),fileName);
        })
      },1000,fileName="oussema.pdf")
    },error => console.log(error));
  }*/
}
