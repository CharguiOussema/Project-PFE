import {Component, inject, OnInit} from '@angular/core';
import {Enseignant} from '../Models/enseignant';
import {EnseignantService} from '../Services/enseignant.service';
import {FormGroup} from '@angular/forms';
import {TokenService} from '../Services/token.service';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {UpdateChargePFEComponent} from '../update-charge-pfe/update-charge-pfe.component';
import {UpdateChargeHoraireS1Component} from '../update-charge-horaire-s1/update-charge-horaire-s1.component';
import {UpdateChargeHoraireS2Component} from '../update-charge-horaire-s2/update-charge-horaire-s2.component';
import { saveAs } from "file-saver";
import {HttpClient} from '@angular/common/http';
import {MatSnackBar} from '@angular/material/snack-bar';
const NIME_TYPE = {
  pdf: 'application/pdf',
  xls: 'application/vnd.ms-excel',
  xlsx: 'application/vnd.openxmlformats-officedocument.spreadsheetxml.sheet'
}
@Component({
  selector: 'app-list-charge-horaire',
  templateUrl: './list-charge-horaire.component.html',
  styleUrls: ['./list-charge-horaire.component.css']
})
export class ListChargeHoraireComponent implements OnInit {

  listEnseignnat: Enseignant[] = [];
  searchEnseignant: Enseignant[] = [];
  chargeForm = new FormGroup({

  })

  constructor(private enseignantService: EnseignantService,
              public tokenService: TokenService,
              public dialog: MatDialog,
              private snackBar:MatSnackBar) { }

  ngOnInit(): void {
    this.findByDepartement(parseInt(this.tokenService.getId()));
  }
  search(query: string){
    this.searchEnseignant = (query) ? this.listEnseignnat.filter(enseignant => enseignant.prenomNom.toLowerCase().includes(query.toLowerCase()) || enseignant.identifiantUnique.toString().includes(query) ) : this.listEnseignnat;
  }
  findByDepartement(id: number){
   if(this.tokenService.getFonction()=="Directeur de departement"){
    this.enseignantService.findEnseignantById(id).subscribe(data =>{
      this.enseignantService.findEnseignantByDepartement(data.departement?.titre).subscribe(res =>{
     this.searchEnseignant = this.listEnseignnat = res as Enseignant[];

      })
    })
   }else if(this.tokenService.getFonction()=="Directeur" || this.tokenService.getFonction()=="directeur visiteur") {
     this.enseignantService.findAllEnseignant().subscribe(data => {
       this.searchEnseignant = this.listEnseignnat = data as Enseignant[];
     })
   }else if(this.tokenService.getFonction() === "Enseignant"){
     this.enseignantService.findEnseignantById(parseInt(this.tokenService.getId())).subscribe(data =>{
       this.searchEnseignant.push(data as Enseignant);
       this.listEnseignnat.push(data as Enseignant);
     })
   }
  }
  updateChargePFE(id: number){
    if(this.tokenService.getFonction()=="Directeur de departement") {
      const dialogConfig = new MatDialogConfig();
      dialogConfig.disableClose = false;
      dialogConfig.autoFocus = true;
      dialogConfig.data = id;
      this.dialog.open(UpdateChargePFEComponent, dialogConfig);
    }
  }
  updateChargeS1(id : number){
    if(this.tokenService.getFonction()=="Directeur de departement") {
      const dialogConfig = new MatDialogConfig();
      dialogConfig.disableClose = false;
      dialogConfig.autoFocus = true;
      dialogConfig.data = id;
      this.dialog.open(UpdateChargeHoraireS1Component, dialogConfig);
    }
  }
  updateChargeS2(id:number){
    if(this.tokenService.getFonction()=="Directeur de departement") {
      const dialogConfig = new MatDialogConfig();
      dialogConfig.disableClose = false;
      dialogConfig.autoFocus = true;
      dialogConfig.data = id;
      this.dialog.open(UpdateChargeHoraireS2Component, dialogConfig);
    }
  }

  Nombretotal(value: number , value2: number) : number{
    return (value+value2)/2;
  }
  total(value1: number, value2 : number, value3 : number): number{
    return value1 + value2 + value3;
  }

  restPFE(due: number,nbPfe : number, total: number) {
    if(total > due){
      return nbPfe;
    }else if((total + nbPfe) > due){
      return (total + nbPfe) - due;
    }else{
      return 0;
    }


  }

  donwloadFileRecapPDF(fileName: string){
    this.enseignantService.deleteFile(fileName).subscribe(data =>{
      setTimeout(()=>{
        this.enseignantService.writeFileHeuresSup().subscribe(res =>{
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
          },1000,fileName="heuresSup2.pdf")
          },error => console.log(error));
        })
      },1000,fileName)
    },error => console.log(error))
  }


  donwloadFileRecapExcel(fileName: string){
    this.enseignantService.writeFileHeuresSup().subscribe(data =>{
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
      },2000,fileName)
    },error => console.log(error))
  }





  /*

    downloadheursup(fileName: string) {



      this.enseignantService.writeFileHeuresSup().subscribe(data =>{

        if(fileName === "heuresSup2.pdf"){

          this.enseignantService.convertFile("heuresSup2.xlsx").subscribe(data =>{

          },error => console.log("no converted"))
        }
        setTimeout(()=>{


          const EXT = fileName.substr(fileName.lastIndexOf('.')+1);
          this.enseignantService.downloadFile({'fileName': fileName}).subscribe(data =>{
            saveAs(new Blob([data], {type: NIME_TYPE[EXT]}),fileName);
          })
        },2000,fileName)
              },error => console.log(error))




    }

    deleteFile(fileName: string){

    this.enseignantService.deleteFile(fileName).subscribe(data=>{
    },error => console.log(error))
      setTimeout(()=>{
        this.downloadheursup(fileName);
      },2000,fileName)
      //this.downloadheursup(fileName);
    }
  */
}
