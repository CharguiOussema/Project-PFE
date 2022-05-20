import { Component, OnInit } from '@angular/core';
import {EnseignantService} from '../Services/enseignant.service';
import {Enseignant} from '../Models/enseignant';
import {Etablissement} from '../Models/etablissement';
import {EtablissementService} from '../Services/etablissement.service';
import { saveAs } from "file-saver";
import {MatSnackBar} from '@angular/material/snack-bar';
const NIME_TYPE = {
  pdf: 'application/pdf',
  xls: 'application/vnd.ms-excel',
  xlsx: 'application/vnd.openxmlformats-officedocument.spreadsheetxml.sheet'
}

@Component({
  selector: 'app-recap-enseignants',
  templateUrl: './recap-enseignants.component.html',
  styleUrls: ['./recap-enseignants.component.css']
})
export class RecapEnseignantsComponent implements OnInit {
  enseignants : Enseignant[];
  etablissement: Etablissement = new Etablissement();
  totalMontantPayableTab1: number ;
  totalMontantPayableCompteBancaireTab1: number;
  totalMontantPayableComptePostalTab1: number;
  totalImpotsRevenuTab1 : number;
  totalImpotsRevenuBancaireTab1 : number;
  totalImpotsRevenuPostalTab1 : number;
  totalMontantNetTab1 : number;
  totalMontantNetBancaireTab1 : number;
  totalMontantNetPostalTab1 : number;


  constructor(private enseignantService: EnseignantService,
              private etablissementService: EtablissementService,
              private snackBar:MatSnackBar) { }

  ngOnInit(): void {
    this.findEnseignantByCodecolor();
    this.findEablissement();

  }
  findEablissement(){
    this.etablissementService.findEtablissement().subscribe(data =>{
      this.etablissement = data as Etablissement;
    })
  }
  findEnseignantByCodecolor(){
    this.enseignantService.findByCodeColeur().subscribe(data =>{
      this.enseignants= data as Enseignant[];
      setTimeout(()=>{
        this.calculTab1(this.enseignants);
      },500,this.enseignants)

    })
  }

  compte(rib: number) : string{
   var ch = rib.toString();
    if(ch.substr(0,2) ==="17"){
      return "Compte postal"
    }else{
      return "Compte bancaire"
    }
  }

  totalMontantPayable(e: Enseignant): number {
   var absence =parseFloat((e.absence/3).toFixed(3));
    var cousPourcentageAnnuelBrut = parseFloat( (e.heuresSupp.cours * e.grade.salaireCours).toFixed(3));
    var tdPourcentageAnnuelBrut = parseFloat( (e.heuresSupp.td * e.grade.salaireTd).toFixed(3));
    var tpPourcentageAnnuelBrut = parseFloat( (e.heuresSupp.tp * e.grade.salaireTp).toFixed(3));
    var coursTauxHoraire = parseFloat((e.grade.salaireCours / this.etablissement.nombreSomaine).toFixed(3));
    var tdTauxHoraire = parseFloat((e.grade.salaireTd / this.etablissement.nombreSomaine).toFixed(3));
    var tpTauxHoraire = parseFloat((e.grade.salaireTp / this.etablissement.nombreSomaine).toFixed(3));
    var coursMontantReduction = parseFloat((coursTauxHoraire * absence).toFixed(3));
    var tdMontantReduction = parseFloat((tdTauxHoraire * absence).toFixed(3));
    var tpMontantReduction = parseFloat((tpTauxHoraire * absence).toFixed(3));
    var coursMontantPayable = parseFloat(( cousPourcentageAnnuelBrut - coursMontantReduction ).toFixed(3));
    var tdMontantPayable = parseFloat(( tdPourcentageAnnuelBrut - tdMontantReduction).toFixed(3));
    var tpMontantPayable = parseFloat(( tpPourcentageAnnuelBrut - tpMontantReduction).toFixed(3));
    return  parseFloat((coursMontantPayable + tdMontantPayable + tpMontantPayable).toFixed(3));
  }


  impotsRevenu(totalMontantPayable: number): number {
    return parseFloat( (totalMontantPayable * 0.15).toFixed(3)) ;
  }

  totalMontantNet(number: number, number2: number) {
    return parseFloat((number - number2).toFixed(3));
  }
  calculTab1(enseignants : Enseignant[]){
    var MontantPayableTab1=0;
    var MontantPayableCompteBancaireTab1 = 0;
    var MontantPayableComptePostalTab1 = 0;
    enseignants.filter(e =>{
      MontantPayableTab1 = MontantPayableTab1 +  this.totalMontantPayable(e);

      if(this.compte(e.rib) === "Compte postal"){
        MontantPayableComptePostalTab1 = MontantPayableComptePostalTab1 + this.totalMontantPayable(e);
      }else{
        MontantPayableCompteBancaireTab1 = MontantPayableCompteBancaireTab1 + this.totalMontantPayable(e);
      }
    })
    this.totalMontantPayableTab1 = parseFloat(MontantPayableTab1.toFixed(3));
    this.totalMontantPayableCompteBancaireTab1 = parseFloat(MontantPayableCompteBancaireTab1.toFixed(3));
    this.totalMontantPayableComptePostalTab1 = parseFloat(MontantPayableComptePostalTab1.toFixed(3));
    this.totalImpotsRevenuTab1 = this.impotsRevenu(this.totalMontantPayableTab1);
    this.totalImpotsRevenuBancaireTab1 = this.impotsRevenu(this.totalMontantPayableCompteBancaireTab1);
    this.totalImpotsRevenuPostalTab1 = this.impotsRevenu(this.totalMontantPayableComptePostalTab1);
    this.totalMontantNetTab1= this.totalMontantNet(this.totalMontantPayableTab1 , this.totalImpotsRevenuTab1);
    this.totalMontantNetPostalTab1 = this.totalMontantNet(this.totalMontantPayableComptePostalTab1 , this.totalImpotsRevenuPostalTab1);
    this.totalMontantNetBancaireTab1 = this.totalMontantNet(this.totalMontantPayableCompteBancaireTab1 , this.totalImpotsRevenuBancaireTab1);
  }
  donwloadFileRecapPDF(fileName: string){
    this.enseignantService.deleteFile(fileName).subscribe(data =>{
        setTimeout(()=>{
          this.enseignantService.writeFileRecap().subscribe(res =>{

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
            },1000,fileName="Recap2.pdf")
            },error => console.log(error));
          })
        },1000,fileName)
    },error => console.log(error))
  }
  donwloadFileRecapExcel(fileName: string){
    this.enseignantService.writeFileRecap().subscribe(data =>{
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
  }

}
