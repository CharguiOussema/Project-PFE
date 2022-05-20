import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Enseignant} from '../Models/enseignant';
import {FonctionService} from '../Services/fonction.service';
import {GradeService} from '../Services/grade.service';
import {DepartementService} from '../Services/departement.service';
import {EnseignantService} from '../Services/enseignant.service';

@Component({
  selector: 'app-detail-validation-enseignants',
  templateUrl: './detail-validation-enseignants.component.html',
  styleUrls: ['./detail-validation-enseignants.component.css']
})
export class DetailValidationEnseignantsComponent implements OnInit {
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

  constructor(public dialogRef: MatDialogRef<DetailValidationEnseignantsComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Enseignant,
              private fonctionService: FonctionService,
              private gradeService: GradeService,
              private departementService: DepartementService,
              private enseignantService: EnseignantService) {
    this.enseignant = data ;
  }

  ngOnInit(): void {

  }

}
