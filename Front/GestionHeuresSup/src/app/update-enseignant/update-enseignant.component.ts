import { Component, OnInit } from '@angular/core';
import {EnseignantService} from '../Services/enseignant.service';
import {TokenService} from '../Services/token.service';
import {Enseignant} from '../Models/enseignant';
import {DepartementService} from '../Services/departement.service';
import {FonctionService} from '../Services/fonction.service';
import {GradeService} from '../Services/grade.service';
import {Departement} from '../Models/departement';
import {Fonction} from '../Models/fonction';
import {Grade} from '../Models/grade';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from '@angular/router';

@Component({
  selector: 'app-update-enseignant',
  templateUrl: './update-enseignant.component.html',
  styleUrls: ['./update-enseignant.component.css']
})
export class UpdateEnseignantComponent implements OnInit {
  enseignant: Enseignant = new Enseignant();
  enseignantUpdate: Enseignant = new Enseignant();
  listDepartement :Departement[];
  listFonction: Fonction[];
  listGrade: Grade[];
  updateForm = new FormGroup({
    id: new FormControl(null),
    prenomNom: new FormControl('',Validators.required),
    prenomNomArabe: new FormControl('',Validators.required),
    cin: new FormControl(null),
    identifiantUnique: new FormControl(null),
    numTel: new FormControl('',[Validators.required ,Validators.pattern('[0-9]{8}')]),
    email: new FormControl('',[Validators.email, Validators.required]),
    dateNaissance: new FormControl('',Validators.required),
    lieuNaissance: new FormControl('',Validators.required),
    lieuNaissanceArabe: new FormControl('',Validators.required),
    rib: new FormControl(null)
  });

  get prenomNom() {
    return this.updateForm.get('prenomNom');
  }

  get prenomNomArabe() {
    return this.updateForm.get('prenomNomArabe');
  }

  get cin() {
    return this.updateForm.get('cin');
  }

  get identifiantUnique() {
    return this.updateForm.get('identifiantUnique');
  }

  get numTel() {
    return this.updateForm.get('numTel');
  }

  get email() {
    return this.updateForm.get('email');
  }

  get dateNaissance() {
    return this.updateForm.get('dateNaissance');
  }

  get lieuNaissance() {
    return this.updateForm.get('lieuNaissance');
  }

  get lieuNaissanceArabe() {
    return this.updateForm.get('lieuNaissanceArabe');
  }

  get rib() {
    return this.updateForm.get('rib');
  }


  constructor(private ensiegnantService: EnseignantService,
              private tokenService: TokenService,
              private snackBar:MatSnackBar,
              private router : Router) { }

  ngOnInit(): void {
    this.findEnseignantById(parseInt(this.tokenService.getId()));

  }

  updateEnseignant(){
    if (this.updateForm.valid) {
      this.enseignant.prenomNomArabe = this.prenomNomArabe.value;
      this.enseignant.prenomNom = this.prenomNom.value;
      this.enseignant.numTel = this.numTel.value;
      this.enseignant.email = this.email.value;
      this.enseignant.dateNaissance = this.dateNaissance.value;
      this.enseignant.lieuNaissance = this.lieuNaissance.value;
      this.enseignant.lieuNaissanceArabe = this.lieuNaissanceArabe.value;
      this.enseignant.active = this.enseignantUpdate.active;
      this.ensiegnantService.updateEnseignant(this.enseignant).subscribe(data => {
        this.reloadComponent();
        console.log(data);
        this.snackBar.open(" Modification avec succès ", "", {
          duration: 3000,
          verticalPosition: 'top',
          panelClass: ['bb']
        });
      }, error => {
        this.snackBar.open(" Echec de modification ", "", {
          duration: 3000,
          verticalPosition: 'top',
          panelClass: ['aa']
        });
        console.log(error)

      })
    }
    else {
      this.validateAllFormFields(this.updateForm);


    }

  }
  findEnseignantById(id: number){
      this.ensiegnantService.findEnseignantById(id).subscribe(data =>{
        this.enseignant = data as Enseignant;
        this.enseignantUpdate = data ;
        this.updateForm.patchValue({
          id : this.enseignant.id,
          prenomNom: this.enseignant.prenomNom,
          prenomNomArabe: this.enseignant.prenomNomArabe,
          cin: this.enseignant.cin,
          identifiantUnique: this.enseignant.identifiantUnique,
          numTel: this.enseignant.numTel,
          email: this.enseignant.email,
          dateNaissance: this.enseignant.dateNaissance,
          lieuNaissance: this.enseignant.lieuNaissance,
          lieuNaissanceArabe: this.enseignant.lieuNaissanceArabe,
          rib: this.enseignant.rib
        })
      },error => console.log(error));
  }
  validateAllFormFields(formGroup: FormGroup)
  {Object.keys(formGroup.controls).forEach(field =>
  {const control = formGroup.get(field);
    if (control instanceof FormControl)
    {control.markAsTouched({ onlySelf: true }); }
    else if (control instanceof FormGroup)
    {this.validateAllFormFields(control); }});
  }
/*  get prenomNomArabe(){
    return this.updateForm.get('prenomNomArabe');
  }
  get prenomNom(){
    return this.updateForm.get('prenomNom');
  }
  get numTel(){
    return this.updateForm.get('numTel');
  }
  get email(){
    return this.updateForm.get('email');
  }
  get dateNaissance(){
    return this.updateForm.get('dateNaissance');
  }
  get lieuNaissance(){
    return this.updateForm.get('lieuNaissance');
  }
  get lieuNaissanceArabe(){
    return this.updateForm.get('lieuNaissanceArabe');
  }*/

  reloadComponent() {
    let currentUrl = this.router.url;
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate([currentUrl]);
  }
}
