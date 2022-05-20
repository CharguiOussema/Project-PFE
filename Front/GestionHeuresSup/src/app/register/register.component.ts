import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {RegisterService} from '../Services/register.service';
import {DepartementService} from '../Services/departement.service';
import {Departement} from '../Models/departement';
import {FonctionService} from '../Services/fonction.service';
import {GradeService} from '../Services/grade.service';
import {Fonction} from '../Models/fonction';
import {Grade} from '../Models/grade';
import {Enseignant} from '../Models/enseignant';
import {TokenService} from '../Services/token.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  listDepartement: Departement[];
  listFonction: Fonction[];
  listFonctionTemp: Fonction[];
  listGrade: Grade[];
  enseignant: Enseignant = new Enseignant();
  registerForm = new FormGroup({
    prenomNom: new FormControl('', Validators.required),
    prenomNomArabe: new FormControl('', Validators.required),
    cin: new FormControl('', [Validators.required ,Validators.pattern('[0-9]{8}')]),
    identifiantUnique: new FormControl('', Validators.required),
    numTel: new FormControl('', [Validators.required ,Validators.pattern('[0-9]{8}')]),
    email: new FormControl('',[Validators.email, Validators.required]),
    dateNaissance: new FormControl('', Validators.required),
    lieuNaissance: new FormControl('', Validators.required),
    lieuNaissanceArabe: new FormControl('', Validators.required),
    rib: new FormControl('', [Validators.required ,Validators.pattern('[0-9]{19}')]),
    grade: new FormControl('', Validators.required),
    fonction: new FormControl('', Validators.required),
    departement: new FormControl('', Validators.required)
  });

  get prenomNom() {
    return this.registerForm.get('prenomNom');
  }

  get prenomNomArabe() {
    return this.registerForm.get('prenomNomArabe');
  }

  get cin() {
    return this.registerForm.get('cin');
  }

  get identifiantUnique() {
    return this.registerForm.get('identifiantUnique');
  }

  get numTel() {
    return this.registerForm.get('numTel');
  }

  get email() {
    return this.registerForm.get('email');
  }

  get dateNaissance() {
    return this.registerForm.get('dateNaissance');
  }

  get lieuNaissance() {
    return this.registerForm.get('lieuNaissance');
  }

  get lieuNaissanceArabe() {
    return this.registerForm.get('lieuNaissanceArabe');
  }

  get rib() {
    return this.registerForm.get('rib');
  }

  get grade() {
    return this.registerForm.get('grade');
  }

  get fonction() {
    return this.registerForm.get('fonction');
  }

  get departement() {
    return this.registerForm.get('departement');
  }

  constructor(private service: RegisterService,
              private departementService: DepartementService,
              private fonctionService: FonctionService,
              private gradeService: GradeService,
              public tokenService: TokenService,
              private snackBar: MatSnackBar,
              private route: Router) {
  }

  ngOnInit(): void {
    this.findAllDepartement();
    this.findAllGrade();
    this.findAllFonction();
  }

  register() {
    if (this.registerForm.valid) {


    this.enseignant = this.registerForm.value;
    if (this.tokenService.getFonction() === 'Directeur' || this.tokenService.getFonction() === 'ressource humaine') {
      this.enseignant.active = 'y';
    } else {
      this.enseignant.active = 'n';
    }
    this.service.register(this.enseignant).subscribe(data => {
        this.snackBar.open('Inscription avec succès    ', '', {
          duration: 3000,
          verticalPosition: 'top',
          panelClass: ['bb']
        });
        this.route.navigate(['/login']);
      }, error => {
        this.snackBar.open('Echec d\'inscription', '', {
          duration: 3000,
          verticalPosition: 'top',
          panelClass: ['bb']
        });
      }
    );
    } else {
      this.validateAllFormFields(this.registerForm);


    }
  }

  findAllDepartement() {
    this.departementService.findAllDepartement().subscribe(data => {
      this.listDepartement = data as Departement[];
    }, error => console.log(error));
  }

  findAllGrade() {
    this.gradeService.findAllGrade().subscribe(data => {
      this.listGrade = data as Grade[];
    }, error => console.log(error));
  }

  findAllFonction() {
    this.fonctionService.findAllFonction().subscribe(data => {
      this.listFonction = data as Fonction[];
      this.listFonction.splice(3, 1);
      console.log(this.listFonction);
    }, error => console.log(error));
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
