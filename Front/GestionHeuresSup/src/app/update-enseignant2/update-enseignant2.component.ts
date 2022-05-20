import {Component, Inject, OnInit} from '@angular/core';
import {Enseignant} from '../Models/enseignant';
import {Fonction} from '../Models/fonction';
import {Grade} from '../Models/grade';
import {Departement} from '../Models/departement';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {TokenService} from '../Services/token.service';
import {FonctionService} from '../Services/fonction.service';
import {GradeService} from '../Services/grade.service';
import {DepartementService} from '../Services/departement.service';
import {EnseignantService} from '../Services/enseignant.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from '@angular/router';

@Component({
  selector: 'app-update-enseignant2',
  templateUrl: './update-enseignant2.component.html',
  styleUrls: ['./update-enseignant2.component.css']
})
export class UpdateEnseignant2Component implements OnInit {
  enseignant: Enseignant = new Enseignant();
  en: Enseignant = new Enseignant();
  listFonction: Fonction[];
  listGrade: Grade[];
  listDepartement :Departement[];

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
    grade: new FormControl(),
    fonction: new FormControl(),
    departement: new FormControl()

  });
  constructor(public dialogRef: MatDialogRef<UpdateEnseignant2Component>,
              @Inject(MAT_DIALOG_DATA) public data: Enseignant,
              public tokenService: TokenService,
              private fonctionService: FonctionService,
              private gradeService: GradeService,
              private departementService: DepartementService,
              private enseignantService: EnseignantService,
              private snackBar:MatSnackBar,
              private router : Router) {
    this.enseignant = data ;
    this.en = data ;
  }

  ngOnInit(): void {
    this.findAllFonction();
    this.findAllGrade();
    this.findAllDepartement();
    this.findEnseignant();
  }
  findEnseignant(){
    this.registerForm.patchValue({
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
  }
  findAllFonction() {
    this.fonctionService.findAllFonction().subscribe(data =>{
      this.listFonction = data as Fonction[];
    },error => console.log(error))
  }
  findAllGrade(){
    this.gradeService.findAllGrade().subscribe(data =>{
      this.listGrade =data as Grade[];
    },error => console.log(error))
  }
  findAllDepartement(){
    this.departementService.findAllDepartement().subscribe(data =>{
      this.listDepartement = data as Departement[];
    },error => console.log(error))
  }
  updateEnseignant() {
    if(this.registerForm.valid){
    this.enseignant.prenomNom = this.prenomNom.value;
    this.enseignant.prenomNomArabe = this.prenomNomArabe.value;
    this.enseignant.rib = this.rib.value;
    this.enseignant.cin = this.cin.value;
    this.enseignant.identifiantUnique= this.identifiantUnique.value;
    this.enseignant.numTel =this.numTel.value;
    this.enseignant.email = this.email.value;
    this.enseignant.dateNaissance = this.dateNaissance.value;
    this.enseignant.lieuNaissance = this.lieuNaissance.value;
    this.enseignant.lieuNaissanceArabe = this.lieuNaissanceArabe.value;
    this.enseignant.grade = this.en.grade;
    this.enseignant.fonction = this.en.fonction;
    this.enseignant.departement =this.departement.value;
    this.enseignantService.updateEnseignant(this.enseignant).subscribe(data =>{
      this.dialogRef.close();
      this.reloadComponent();
      this.snackBar.open(" Modification  avec succès ","",{
        duration:3000,
        verticalPosition:'top',
        panelClass: ['bb']
      });

    },error => {
      this.snackBar.open(" Echec de modification d'un enseignant ","",{
        duration:3000,
        verticalPosition:'top',
        panelClass: ['aa']
      });
      console.log(error)
    });
  } else {
      this.validateAllFormFields(this.registerForm);

    }
  }
  selectGrade(g: any){
    this.en.grade = g as Grade;
  }
  selectFonction(f: any){
    this.en.fonction = f as Fonction;
  }
  selectDepartement(d: any){
    this.en.departement = d as Departement;
    console.log("test")
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

  close(){
    this.dialogRef.close()
  }
  reloadComponent() {
    let currentUrl = this.router.url;
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate([currentUrl]);
  }
  validateAllFormFields(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({onlySelf: true});
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    });
  }
}
