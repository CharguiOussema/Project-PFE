import {Component, Inject, OnInit} from '@angular/core';
import {Enseignant} from '../Models/enseignant';
import {EnseignantService} from '../Services/enseignant.service';
import {TokenService} from '../Services/token.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ChargeHoraire} from '../Models/charge-horaire';
import {HeurePFE} from '../Models/heure-pfe';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-update-charge-pfe',
  templateUrl: './update-charge-pfe.component.html',
  styleUrls: ['./update-charge-pfe.component.css']
})
export class UpdateChargePFEComponent implements OnInit {
  enseignant: Enseignant = new Enseignant();
  chargeHorairePFE: HeurePFE = new HeurePFE();
  idEnseignant: number;
  chargePFEForm = new FormGroup({
    nombre: new FormControl('',Validators.required),
    encadrement: new FormControl(null)
  })
  constructor(private enseignantService: EnseignantService,
              private router : Router,
              private snackBar:MatSnackBar,
              public dialogRef: MatDialogRef<UpdateChargePFEComponent>,
              @Inject(MAT_DIALOG_DATA) public data: number) {
    this.idEnseignant = data ;
  }

  ngOnInit(): void {
    this.findEnseignantById(this.idEnseignant);
  }

  findEnseignantById(id: number){
    this.enseignantService.findEnseignantById(id).subscribe(data =>{
      this.enseignant = data as Enseignant

      this.chargePFEForm.patchValue({
        nombre: this.enseignant.chargeHorairePFE?.nombre,
        encadrement: this.enseignant.chargeHorairePFE?.coefficient
      })
    })
  }

  updateChargePFE(){
    if (this.chargePFEForm.valid) {
      if (this.enseignant.chargeHorairePFE?.id != null) {
        this.chargeHorairePFE.id = this.enseignant.chargeHorairePFE.id;
      }

      this.chargeHorairePFE.nombre = this.nombre.value;
      // this.chargeHorairePFE.coefficient = this.encadrement.value;
      this.enseignantService.addHeurePFE(this.idEnseignant, this.chargeHorairePFE).subscribe(data => {
        this.snackBar.open("charge d'encadrment PFE à été ajouté avec succés ", "", {
          duration: 3000,
          verticalPosition: 'top',
          panelClass: ['bb']
        });
        this.close();
        this.reloadComponent();
      }, error => {
        this.snackBar.open(" Echec d'ajout de charge d'encadrement PFE ", "", {
          duration: 3000,
          verticalPosition: 'top',
          panelClass: ['aa']
        });
        console.log(error)
      });
    }else{
      this.validateAllFormFields(this.chargePFEForm);
    }
  }
  get nombre(){
    return this.chargePFEForm.get('nombre');
  }
  get encadrement(){
    return this.chargePFEForm.get('encadrement');
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
  validateAllFormFields(formGroup: FormGroup)
  {Object.keys(formGroup.controls).forEach(field =>
  {const control = formGroup.get(field);
    if (control instanceof FormControl)
    {control.markAsTouched({ onlySelf: true }); }
    else if (control instanceof FormGroup)
    {this.validateAllFormFields(control); }});
  }
}
