import {Component, Inject, OnInit} from '@angular/core';
import {EnseignantService} from '../Services/enseignant.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Enseignant} from '../Models/enseignant';
import {ChargeHoraireEffectif} from '../Models/charge-horaire-effectif';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-update-charge-horaire-s1',
  templateUrl: './update-charge-horaire-s1.component.html',
  styleUrls: ['./update-charge-horaire-s1.component.css']
})
export class UpdateChargeHoraireS1Component implements OnInit {
  enseignant: Enseignant = new Enseignant();
  chargeHoraireS1: ChargeHoraireEffectif = new ChargeHoraireEffectif();
  idEnseignant: number;
  chargeSemster1Form = new FormGroup({
    td: new FormControl('',Validators.required),
    tp: new FormControl('',Validators.required),
    cours: new FormControl('',Validators.required)
  })
  constructor(private enseignantService: EnseignantService,
              private router : Router,
              private snackBar:MatSnackBar,
              public dialogRef: MatDialogRef<UpdateChargeHoraireS1Component>,
              @Inject(MAT_DIALOG_DATA) public data: number) {
    this.idEnseignant = data ;
}

  ngOnInit(): void {
    this.findEnseignantById(this.idEnseignant);
  }
  findEnseignantById(id:number){
    this.enseignantService.findEnseignantById(id).subscribe(data =>{
      this.enseignant = data as Enseignant
      this.chargeSemster1Form.patchValue({
        td: this.enseignant.chargeHoraireS1?.td,
        tp: this.enseignant.chargeHoraireS1?.tp,
        cours: this.enseignant.chargeHoraireS1?.cours
      })
    })
  }

  close(){
    this.dialogRef.close()
  }
  get cours(){
    return this.chargeSemster1Form.get('cours');
  }
  get td(){
    return this.chargeSemster1Form.get('td');
  }
  get tp(){
    return this.chargeSemster1Form.get('tp');
  }
  updateChargeSemster1(){
    if (this.chargeSemster1Form.valid) {
      if (this.enseignant.chargeHoraireS1?.id != null) {
        this.chargeHoraireS1.id = this.enseignant.chargeHoraireS1.id;
      }
      this.chargeHoraireS1.td = this.td.value;
      this.chargeHoraireS1.tp = this.tp.value;
      this.chargeHoraireS1.cours = this.cours.value;
      this.enseignantService.addHoraireSemster1(this.idEnseignant, this.chargeHoraireS1).subscribe(data => {
        this.snackBar.open("charge horaire semestre 1 à été ajouté avec succés ", "", {
          duration: 3000,
          verticalPosition: 'top',
          panelClass: ['bb']
        });
        this.close();
        this.reloadComponent();
      }, error => {
        this.snackBar.open(" Echec d'ajout de charge horaire seemstre 1  ", "", {
          duration: 3000,
          verticalPosition: 'top',
          panelClass: ['aa']
        });
        console.log(error)
      })
    }else{
      this.validateAllFormFields(this.chargeSemster1Form);
    }

  }
  reloadComponent() {
    let currentUrl = this.router.url;
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate([currentUrl]);
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
