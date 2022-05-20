import {Component, Inject, OnInit} from '@angular/core';
import {Enseignant} from '../Models/enseignant';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {FormControl, FormGroup} from '@angular/forms';
import {TokenService} from '../Services/token.service';
import {Fonction} from '../Models/fonction';
import {FonctionService} from '../Services/fonction.service';
import {Grade} from '../Models/grade';
import {GradeService} from '../Services/grade.service';
import {Departement} from '../Models/departement';
import {DepartementService} from '../Services/departement.service';
import {EnseignantService} from '../Services/enseignant.service';

@Component({
  selector: 'app-detail-enseignant',
  templateUrl: './detail-enseignant.component.html',
  styleUrls: ['./detail-enseignant.component.css']
})
export class DetailEnseignantComponent implements OnInit {
  enseignant: Enseignant = new Enseignant();
  registerForm = new FormGroup({
    prenomNom: new FormControl(),
    prenomNomArabe: new FormControl(),
    cin: new FormControl(),
    identifiantUnique: new FormControl(),
    numTel: new FormControl(),
    email: new FormControl(),
    dateNaissance: new FormControl(),
    lieuNaissance: new FormControl(),
    lieuNaissanceArabe: new FormControl(),
    rib: new FormControl(),
    grade: new FormControl(),
    fonction: new FormControl(),
    departement: new FormControl(),

  });
  constructor(public dialogRef: MatDialogRef<DetailEnseignantComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Enseignant,
              public tokenService: TokenService) {
    this.enseignant = data ;
  }

  ngOnInit(): void {

  }




  get fonction(){
    return this.registerForm.get('fonction');
  }
  get grade(){
    return this.registerForm.get('grade');
  }
  get departement(){
    return this.registerForm.get('departement');
  }
  get prenomNom(){
    return this.registerForm.get('prenomNom');
  }
  get prenomNomArabe(){
    return this.registerForm.get('prenomNomArabe');
  }
  get cin(){
    return this.registerForm.get('cin');
  }
  get identifiantUnique(){
    return this.registerForm.get('identifiantUnique');
  }
  get numTel(){
    return this.registerForm.get('numTel');
  }
  get email(){
    return this.registerForm.get('email');
  }
  get dateNaissance(){
    return this.registerForm.get('dateNaissance');
  }
  get lieuNaissance(){
    return this.registerForm.get('lieuNaissance');
  }
  get lieuNaissanceArabe(){
    return this.registerForm.get('lieuNaissanceArabe');
  }
  get rib(){
    return this.registerForm.get('rib');
  }


}
