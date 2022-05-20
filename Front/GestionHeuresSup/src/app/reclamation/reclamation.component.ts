import { Component, OnInit } from '@angular/core';
import {ReclamationService} from '../Services/reclamation.service';
import {TypeReclamationService} from '../Services/type-reclamation.service';
import {TypeReclamation} from '../Models/type-reclamation';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Reclamation} from '../Models/reclamation';
import {EnseignantService} from '../Services/enseignant.service';
import {TokenService} from '../Services/token.service';
import {Enseignant} from '../Models/enseignant';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-reclamation',
  templateUrl: './reclamation.component.html',
  styleUrls: ['./reclamation.component.css']
})
export class ReclamationComponent implements OnInit {
  reclamation: Reclamation = new Reclamation();
  typeReclamation: TypeReclamation[];
  reclamationForm = new FormGroup( {
    type: new FormControl('',Validators.required),
    description: new FormControl('',Validators.required)
})
  constructor(private reclamationService: ReclamationService,
              private typeReclamationService: TypeReclamationService,
              private enseiggnantService: EnseignantService,
              private tokenService: TokenService,
              private router : Router,
              private snackBar:MatSnackBar) { }

  ngOnInit(): void {
    this.findAllTypeReclamation();
  }

  findAllTypeReclamation(){
    this.typeReclamationService.findAll().subscribe(data =>{
      this.typeReclamation = data as TypeReclamation[];
      console.log(this.typeReclamation)
    })
  }


  envoyer() {
    if (this.reclamationForm.valid){
    this.enseiggnantService.findEnseignantById(parseInt(this.tokenService.getId())).subscribe(res =>{
      this.reclamation.enseignant = res as Enseignant;
      this.reclamation.description = this.description.value;
      this.reclamationService.addReclamation(this.reclamation).subscribe(data =>{
        this.reloadComponent();
        this.snackBar.open("Réclamation envoyer avec succés ","",{
          duration:3000,
          verticalPosition:'top',
          panelClass: ['bb']
        });
      },error => {console.log(error);
        this.snackBar.open(" Echec de d'envoi le réclamation  ","",{
          duration:3000,
          verticalPosition:'top',
          panelClass: ['aa']
        });})
    },error => console.log(error))

    }else{
      this.validateAllFormFields(this.reclamationForm);
    }
  }
  get description(){
    return this.reclamationForm.get('description');
  }
  get type(){
    return this.reclamationForm.get('type')
  }
  selectTypeReclamation(value: any) {
      this.reclamation.typeReclamation = value;
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
