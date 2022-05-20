import { Component, OnInit } from '@angular/core';
import {EtablissementService} from '../Services/etablissement.service';
import {Etablissement} from '../Models/etablissement';
import {FormControl, FormGroup} from '@angular/forms';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-update-etablissement',
  templateUrl: './update-etablissement.component.html',
  styleUrls: ['./update-etablissement.component.css']
})
export class UpdateEtablissementComponent implements OnInit {
  etablissement : Etablissement = new Etablissement();
  etablissementForm = new FormGroup({
    nom: new FormControl(),
    nombreProjetPFE: new FormControl(),
    pourcentageEncadrement: new FormControl(),
    nombreEnseignant : new FormControl(),
    anneeUniversitaire: new FormControl(),
    periode: new FormControl(),
    nombreSomaine: new FormControl(),
    periodeArabe: new FormControl()

  })
  constructor(private etablissementService: EtablissementService,
              private snackBar:MatSnackBar,
              private router : Router) { }

  ngOnInit(): void {
    this.findEtablissement();
  }

  findEtablissement(){
    this.etablissementService.findEtablissement().subscribe(data =>{
        this.etablissement = data as Etablissement;
        this.etablissementForm.patchValue({
          nom : this.etablissement?.nom,
          nombreProjetPFE: this.etablissement?.nombreProjetPFE,
          pourcentageEncadrement: (this.etablissement?.nombreProjetPFE/this.etablissement?.nombreEnseignant).toFixed(9),
          nombreEnseignant: this.etablissement?.nombreEnseignant,
          anneeUniversitaire: this.etablissement?.anneeUniversitaire,
          periode: this.etablissement?.periode,
          nombreSomaine: this.etablissement?.nombreSomaine,
          periodeArabe: this.etablissement?.periodeArabe

        })
    })
  }
  updateEtablissement(){
    this.etablissement.nom = this.nom.value;
    this.etablissement.nombreProjetPFE = this.nombreProjetPFE.value;
    this.etablissement.nombreEnseignant = this.nombreEnseignant.value;
    this.etablissement.anneeUniversitaire = this.anneeUniversitaire.value;
    this.etablissement.periode = this.periode.value;
    this.etablissement.nombreSomaine = this.nombreSomaine.value;
    this.etablissement.periodeArabe = this.periodeArabe.value;
    this.etablissementService.addEtablissement(this.etablissement).subscribe(data =>{
      this.etablissement = data as Etablissement;
      this.reloadComponent();
      this.snackBar.open(" Modification avec succès ","",{
        duration:3000,
        verticalPosition:'top',
        panelClass: ['bb']
      });
    }, error => {
      this.snackBar.open(" Echec de modification ","",{
        duration:3000,
        verticalPosition:'top',
        panelClass: ['aa']
      });
    })
  }

  get nom(){
    return this.etablissementForm.get('nom');
  }
  get nombreProjetPFE(){
    return this.etablissementForm.get('nombreProjetPFE');
  }
  get pourcentageEncadrement(){
    return this.etablissementForm.get('pourcentageEncadrement');
  }
  get nombreEnseignant(){
    return this.etablissementForm.get('nombreEnseignant');
  }
  get anneeUniversitaire(){
    return this.etablissementForm.get('anneeUniversitaire');
  }
  get nombreSomaine(){
    return this.etablissementForm.get('nombreSomaine');
  }
  get periode(){
    return this.etablissementForm.get('periode');
  }
  get periodeArabe(){
    return this.etablissementForm.get('periodeArabe');
  }
  reloadComponent() {
    let currentUrl = this.router.url;
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate([currentUrl]);
  }

}
