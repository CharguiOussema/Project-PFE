import { Component, OnInit } from '@angular/core';
import {DepartementService} from '../Services/departement.service';
import {ParcoursService} from '../Services/parcours.service';
import {EnseignantService} from '../Services/enseignant.service';
import {Departement} from '../Models/departement';
import {StatutService} from '../Services/statut.service';
import {Statut} from '../Models/statut';
import {Enseignant} from '../Models/enseignant';
import {Parcours} from '../Models/parcours';
import {EtudiantPFE} from '../Models/etudiant-pfe';
import {EtudiantService} from '../Services/etudiant.service';
import {StagePFE} from '../Models/stage-pfe';
import {StagePFEService} from '../Services/stage-pfe.service';
import {TokenService} from '../Services/token.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-add-stage-pfe',
  templateUrl: './add-stage-pfe.component.html',
  styleUrls: ['./add-stage-pfe.component.css']
})
export class AddStagePFEComponent implements OnInit {
  listDepartement: Departement[];
  listStatut: Statut[];
  listEnseignant: Enseignant[];
  listParcours1: Parcours[];
  listParcours2: Parcours[];
  ListEtudiant1: EtudiantPFE[];
  ListEtudiant2: EtudiantPFE[];

  stagePFE: StagePFE = new StagePFE();
  etudiantPFE1: EtudiantPFE = new EtudiantPFE();
  etudiantPFE2: EtudiantPFE = new EtudiantPFE();
  enseignantinfo: Enseignant = new Enseignant();

  addStage = new FormGroup({
    etudiant1: new FormControl('', Validators.required),
    etudiant2: new FormControl('', Validators.required),
    sujet: new FormControl('', Validators.required),
    societe: new FormControl('', Validators.required),
    departement: new FormControl('', Validators.required),
    enseignant: new FormControl('', Validators.required),
    parcours2: new FormControl(),
    parcours1: new FormControl('', Validators.required)
  })
  departement: Departement = new Departement();

  constructor(private departementService: DepartementService,
              private statutService: StatutService,
              private enseignantService: EnseignantService,
              private etudiantservice: EtudiantService,
              private parcoursService: ParcoursService,
              private stagePfeService: StagePFEService,
              private tokenservice: TokenService,
              private router : Router,
              private snackBar:MatSnackBar
  ) {
  }

  ngOnInit(): void {
    this.findAllDepartement();
    this.findAllStatut();
    this.findAllEnseignant();
    this.findAllParcours();
    this.findByEnseignantsId();

  }

  findAllDepartement() {
    this.departementService.findAllDepartement().subscribe(data => {
      this.listDepartement = data as Departement[];
    })

  }

  findAllStatut() {
    this.statutService.findAllStatut().subscribe(data => {
      this.listStatut = data as Statut[];
    })
  }


  findAllEnseignant() {
    this.enseignantService.findAllEnseignant().subscribe(data => {
      this.listEnseignant = data as Enseignant[];
    })
  }

  findAllParcours() {
    this.parcoursService.findAllParcours().subscribe(data => {
      this.listParcours1 = data as Parcours[];
      this.listParcours2 = data as Parcours[];
    })
  }

  findByEnseignantsId() {
    this.departementService.findByEnseignantsId(parseInt(this.tokenservice.getId())).subscribe(
      data => {
        this.departement = data as Departement;
      }, error => console.log(error))
  }

  findEtudiant1ByParcours(titre: string) {
    this.etudiantservice.findByParcoursTitre(titre).subscribe(data => {
      this.ListEtudiant1 = data as EtudiantPFE[];
    }, error => console.log(error))
  }

  findEtudiant2ByParcours(titre: string) {
    this.etudiantservice.findByParcoursTitre(titre).subscribe(data => {
      this.ListEtudiant2 = data as EtudiantPFE[];
    }, error => console.log(error))
  }

  addstagepfe() {

    if (this.addStage.valid) {
      this.stagePFE.setSujet(this.sujet?.value);
      this.stagePFE.setSociete(this.societe?.value);
      //  this.etudiantPFE1 = this.etudiant1?.value  as EtudiantPFE;
      //  let testEtudiant1 : EtudiantPFE = this.etudiant1.value ;
      if (this.etudiant1.value >= 0) {
        this.stagePFE.setEtudiantPFE1(null);
      } else {
        this.stagePFE.setEtudiantPFE1(this.etudiant1?.value);
      }
      if (this.etudiant2.value >= 0) {
        this.stagePFE.setEtudiantPFE2(null);
      } else {
        this.stagePFE.setEtudiantPFE2(this.etudiant2?.value);
      }
      this.stagePFE.setDepartement(this.departement);
      this.stagePFE.setEnseignant(this.enseignant?.value);
      this.stagePfeService.addStagePFE(this.stagePFE).subscribe(data => {
        this.reloadComponent();
        this.snackBar.open("Ajout stage PFE avec succés ", "", {
          duration: 3000,
          verticalPosition: 'top',
          panelClass: ['bb']
        });
      }, error => {
        console.log(error)
        this.snackBar.open(" Echec d'ajout de stage PFE  ", "", {
          duration: 3000,
          verticalPosition: 'top',
          panelClass: ['aa']
        });
      })
    }else {
      this.validateAllFormFields(this.addStage);
    }
  }

  get etudiant1() {
    return this.addStage.get('etudiant1');
  }

  get etudiant2() {
    return this.addStage.get('etudiant2');
  }

  get sujet() {
    return this.addStage.get('sujet');
  }

  get societe() {
    return this.addStage.get('societe');
  }

  get enseignant() {
    return this.addStage.get('enseignant');
  }

  get parcours2() {
    return this.addStage.get('parcours2');
  }

  get parcours1() {
    return this.addStage.get('parcours1');
  }

  selectChangeParcours1(value: any) {
    this.findEtudiant1ByParcours(value);
  }

  selectChangeParcours2(value: any) {
    this.findEtudiant2ByParcours(value);
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
