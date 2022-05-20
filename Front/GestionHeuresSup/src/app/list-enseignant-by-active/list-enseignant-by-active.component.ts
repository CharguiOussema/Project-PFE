import { Component, OnInit } from '@angular/core';
import {EnseignantService} from '../Services/enseignant.service';
import {Enseignant} from '../Models/enseignant';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {DetailEnseignantComponent} from '../detail-enseignant/detail-enseignant.component';
import {DetailValidationEnseignantsComponent} from '../detail-validation-enseignants/detail-validation-enseignants.component';
import {Mail} from '../Models/mail';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-list-enseignant-by-active',
  templateUrl: './list-enseignant-by-active.component.html',
  styleUrls: ['./list-enseignant-by-active.component.css']
})
export class ListEnseignantByActiveComponent implements OnInit {
  listEnseignant: Enseignant[] ;
  mail: Mail = new Mail();
  searchEnseignant: Enseignant[] = [];
  enseignant : Enseignant = new Enseignant();
  constructor(private enseignantService: EnseignantService,
              public dialog: MatDialog,
              private snackBar:MatSnackBar) { }

  ngOnInit(): void {
    this.findByActive();
  }
  search(query: string){
    this.listEnseignant = (query) ? this.searchEnseignant.filter(enseignant => enseignant .prenomNom.toLowerCase().includes(query.toLowerCase())
      || enseignant.identifiantUnique.toString().includes(query)
      || enseignant.email?.toLowerCase().includes(query.toLowerCase())
      || enseignant.grade?.titre.toLowerCase().includes(query.toLowerCase())
      || enseignant.fonction?.titre.toLowerCase().includes(query.toLowerCase())
      || enseignant.departement?.titre.toLowerCase().includes(query.toLowerCase())
    ): this.searchEnseignant;
  }
  findByActive(){
    this.enseignantService.findByActive("n").subscribe(data =>{
      this.listEnseignant = data as Enseignant[];
      this.searchEnseignant = data as Enseignant[];
    })
  }
  detail(e:Enseignant){
    console.log("id => "+ e.id);
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;
    dialogConfig.height = "110%";
    dialogConfig.data = e;
    this.dialog.open(DetailValidationEnseignantsComponent, dialogConfig);
  }
  deleteEnseignant(id: number){
      this.enseignantService.deleteEnseignant(id).subscribe(data =>{
      },error => console.log(error))
  }

  valider(e: Enseignant) {


    e.active = "y";
    this.mail.email = e.email;
    e.password = this.formPassword(e.prenomNom, e.lieuNaissance, e.cin)
    this.mail.subject = "Demande d'inscription compte iset Béja";
    this.mail.content = "Votre inscription a été effectué avec succès \n " +
      "Adresse : " +e.email+
      "\n " +
      "Mote de passe : " + this.formPassword(e.prenomNom, e.lieuNaissance, e.cin) +"\n" +
      "Bienvenue " + e.prenomNom + "\n" +
      "http://localhost:4200/login";
    this.enseignantService.sendMail(this.mail).subscribe(res =>{

    },error => {

      console.log(error)
    })

    this.enseignantService.addPassword(e).subscribe(data =>{
      alert("fejkersdfs")
      this.snackBar.open("Le compte de "+e.prenomNom+" valider avec succès ","",{
        duration:3000,
        verticalPosition:'top',
        panelClass: ['bb']
      });
    },error => {
      this.snackBar.open(" Echec de validation ","",{
        duration:3000,
        verticalPosition:'top',
        panelClass: ['aa']
      });
      console.log(error)
    })


      this.enseignantService.updateEnseignant(e).subscribe(data =>{
       },error => {

        console.log(error)
      })
  }

  bloquer(e: Enseignant) {


    this.mail.email = e.email;
    this.mail.content = "Votre inscription a été réfusée "
    this.mail.subject = "Demande d'inscription compte iset Béja";
    this.enseignantService.sendMail(this.mail).subscribe(res =>{
      this.snackBar.open("Le compte de "+e.prenomNom+"  blocker avec succès ","",{
        duration:3000,
        verticalPosition:'top',
        panelClass: ['bb']
      });
    },error => {
      this.snackBar.open(" Echec de validation ","",{
        duration:3000,
        verticalPosition:'top',
        panelClass: ['aa']
      });
      console.log(error)
    })
    this.enseignantService.deleteEnseignant(e.id).subscribe(data =>{


    },error => console.log(error))
  }
  formPassword(prenom?: string,lieuNaissance?: string, cin?: number ) : string{

      return   prenom.substr(0,2) + "#"+ cin.toString().substr(0,2) + ""+lieuNaissance.substr(0,2);
  }
}
