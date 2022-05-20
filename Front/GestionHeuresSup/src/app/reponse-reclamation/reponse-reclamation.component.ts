/* tslint:disable */
import { Component, OnInit} from '@angular/core';
import {EnseignantService} from '../Services/enseignant.service';
import {ReclamationService} from '../Services/reclamation.service';
import {Reclamation} from '../Models/reclamation';
import {ActivatedRoute, Router} from '@angular/router';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Mail} from '../Models/mail';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-reponse-reclamation',
  templateUrl: './reponse-reclamation.component.html',
  styleUrls: ['./reponse-reclamation.component.css']
})
export class ReponseReclamationComponent implements OnInit {

  reclamation : Reclamation = new Reclamation();
  mail: Mail = new Mail();
  reclamationForm = new FormGroup({
    reponse : new FormControl('',Validators.required),
    description: new FormControl(null)
  })
  constructor(private enseignantService: EnseignantService,
              private reclamationService: ReclamationService,
              private route : ActivatedRoute,
              private router :Router,
              private snackBar:MatSnackBar) {
    this.router.routeReuseStrategy.shouldReuseRoute = ()=> false;
  }


  ngOnInit(): void {
    this.findReclamationById(this.route.snapshot.params.id);

  }

  findReclamationById(id: number){
    this.reclamationService.findById(id).subscribe(data =>{
      this.reclamation = data as Reclamation;
      console.log(this.reclamation.id)
    })
  }


  envoyer() {
    if (this.reclamationForm.valid){
    this.mail.email = this.reclamation.enseignant?.email;
    this.mail.subject = "Réponse de votre réclamation";
    this.mail.content = "Réclamation : \n   " +
      "Type de réclamation : "+ this.reclamation.typeReclamation?.type + "\n   " +
      "Description : "+ this.reclamation.description+"\n\n" +
      "Réponse : \n   " + this.reponse?.value;
    this.enseignantService.sendMail(this.mail).subscribe(data =>{
      this.snackBar.open("Répondre de réclamation avec succès ","",{
        duration:3000,
        verticalPosition:'top',
        panelClass: ['bb']
      });
    },error => {console.log(error)
      this.snackBar.open(" Échec d'envoi la réponse  ","",{
        duration:3000,
        verticalPosition:'top',
        panelClass: ['aa']
      });

    })
    this.reclamationService.deleteReclamation(this.reclamation.id).subscribe(data =>{
      console.log("reclamation supprimer")
      this.router.navigate(['/listeEnseignant']);
    },error => console.log(error))

    }else{
      this.validateAllFormFields(this.reclamationForm);
    }
  }

  get reponse(){
    return this.reclamationForm.get('reponse');
  }
  validateAllFormFields(formGroup: FormGroup)
  {Object.keys(formGroup.controls).forEach(field =>
  {const control = formGroup.get(field);
    if (control instanceof FormControl)
    {control.markAsTouched({ onlySelf: true }); }
    else if (control instanceof FormGroup)
    {this.validateAllFormFields(control); }});
  }

}
