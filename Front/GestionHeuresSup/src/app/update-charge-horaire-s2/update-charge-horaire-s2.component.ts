import {Component, Inject, OnInit} from '@angular/core';
import {Enseignant} from '../Models/enseignant';
import {ChargeHoraireEffectif} from '../Models/charge-horaire-effectif';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {EnseignantService} from '../Services/enseignant.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-update-charge-horaire-s2',
  templateUrl: './update-charge-horaire-s2.component.html',
  styleUrls: ['./update-charge-horaire-s2.component.css']
})
export class UpdateChargeHoraireS2Component implements OnInit {
  enseignant: Enseignant = new Enseignant();
  chargeHoraireS2: ChargeHoraireEffectif = new ChargeHoraireEffectif();
  idEnseignant: number;
  chargeSemster2Form = new FormGroup({
    td: new FormControl('',Validators.required),
    tp: new FormControl('',Validators.required),
    cours: new FormControl('',Validators.required)
  })
  constructor(private enseignantService: EnseignantService,
              private router : Router,
              private snackBar:MatSnackBar,
              public dialogRef: MatDialogRef<UpdateChargeHoraireS2Component>,
              @Inject(MAT_DIALOG_DATA) public data: number) {
    this.idEnseignant = data ;
  }

  ngOnInit(): void {
    this.findEnseignantById(this.idEnseignant);
  }
  findEnseignantById(id:number){
    this.enseignantService.findEnseignantById(id).subscribe(data =>{
      this.enseignant = data as Enseignant
      this.chargeSemster2Form.patchValue({
        td: this.enseignant.chargeHoraireS2?.td,
        tp: this.enseignant.chargeHoraireS2?.tp,
        cours: this.enseignant.chargeHoraireS2?.cours
      })
    })
  }
  close(){
    this.dialogRef.close()
  }
  get cours(){
    return this.chargeSemster2Form.get('cours');
  }
  get td(){
    return this.chargeSemster2Form.get('td');
  }
  get tp(){
    return this.chargeSemster2Form.get('tp');
  }
  updateChargeSemster2(){
    if (this.chargeSemster2Form.valid) {
      if (this.enseignant.chargeHoraireS2?.id != null) {
        this.chargeHoraireS2.id = this.enseignant.chargeHoraireS2.id;
      }
      this.chargeHoraireS2.td = this.td.value;
      this.chargeHoraireS2.tp = this.tp.value;
      this.chargeHoraireS2.cours = this.cours.value;
      this.enseignantService.addHoraireSemster2(this.idEnseignant, this.chargeHoraireS2).subscribe(data => {
        this.snackBar.open("charge horaire semestre 2 à été ajouté avec succés ", "", {
          duration: 3000,
          verticalPosition: 'top',
          panelClass: ['bb']
        });
        this.close();
        this.reloadComponent();
      }, error => {
        this.snackBar.open(" Echec d'ajout de charge horaire seemstre 2  ", "", {
          duration: 3000,
          verticalPosition: 'top',
          panelClass: ['aa']
        });
        console.log(error)
      })
    }else{
      this.validateAllFormFields(this.chargeSemster2Form);
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
