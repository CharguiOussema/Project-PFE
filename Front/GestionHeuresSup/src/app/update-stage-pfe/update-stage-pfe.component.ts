import {Component, Inject, OnInit} from '@angular/core';
import {StagePFEService} from '../Services/stage-pfe.service';
import {EnseignantService} from '../Services/enseignant.service';
import {EtudiantService} from '../Services/etudiant.service';
import {ParcoursService} from '../Services/parcours.service';
import {StagePFE} from '../Models/stage-pfe';
import {EtudiantPFE} from '../Models/etudiant-pfe';
import {Parcours} from '../Models/parcours';
import {Enseignant} from '../Models/enseignant';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from '@angular/router';

@Component({
  selector: 'app-update-stage-pfe',
  templateUrl: './update-stage-pfe.component.html',
  styleUrls: ['./update-stage-pfe.component.css']
})
export class UpdateStagePFEComponent implements OnInit {

  stagepfe: StagePFE = new StagePFE();
  stagepfeUpdate: StagePFE = new StagePFE();
  listEtudiant1: EtudiantPFE[];
  listEtudiant2: EtudiantPFE[];
  listParcours1: Parcours[];
  listParcours2: Parcours[];
  listEnseignant: Enseignant[];
  e1: EtudiantPFE = new EtudiantPFE();
  e2: EtudiantPFE = new EtudiantPFE();
  idEnseignant: number;

  updateForm = new FormGroup({
    parcours1: new FormControl(),
    etudiant1: new FormControl(),
    cin1: new FormControl(),
    parcours2: new FormControl(),
    etudiant2: new FormControl(),
    cin2: new FormControl(),
    enseignant: new FormControl(),
    sujet: new FormControl('', Validators.required),
    societe: new FormControl('', Validators.required)
  })
  constructor(private stageService : StagePFEService,
              private enseignantService: EnseignantService,
              private etudiantservice: EtudiantService,
              private parcoursService: ParcoursService,
              private snackBar:MatSnackBar,
              private router : Router,
              public dialogRef: MatDialogRef<UpdateStagePFEComponent>,
              @Inject(MAT_DIALOG_DATA) public data:number) {
    this.idEnseignant = data ;
  }

  ngOnInit(): void {
    this.findStagePFEById(this.idEnseignant);
    this.findAllParcours();
    this.findAllEnseignant();
  }

  findStagePFEById(id: number){
    this.stageService.findStagePFEById(id).subscribe(data=>{
      this.stagepfe = data as StagePFE;
      this.stagepfeUpdate =data as StagePFE;
      //patch value => form controllers
      this.updateForm.patchValue({
        id: this.stagepfe.id,
        parcours1: this.stagepfe.etudiantPFE1?.parcours.titre,
        etudiant1: this.stagepfe.etudiantPFE1?.cin,
        cin1: this.stagepfe.etudiantPFE1?.cin,
        parcours2: this.stagepfe.etudiantPFE2?.parcours.titre,
        etudiant2: this.stagepfe.etudiantPFE2?.cin,
        cin2: this.stagepfe.etudiantPFE2?.cin,
        enseignant: this.stagepfe.enseignant?.id,
        sujet: this.stagepfe.sujet,
        societe: this.stagepfe.societe
      })
    })
  }
  findEtudiant1ByParcours(titre: string){
    this.etudiantservice.findByParcoursTitre(titre).subscribe(data =>{
      this.listEtudiant1 = data as EtudiantPFE[];
    },error => console.log(error))
  }
  findEtudiant2ByParcours(titre: string){
    this.etudiantservice.findByParcoursTitre(titre).subscribe(data =>{
      this.listEtudiant2 = data as EtudiantPFE[];
    },error => console.log(error))
  }
  findAllParcours(){
    this.parcoursService.findAllParcours().subscribe(data=>{
      this.listParcours1 = data as Parcours[];
      this.listParcours2 = data as Parcours[];
    })
  }
  findAllEnseignant(){
    this.enseignantService.findAllEnseignant().subscribe(data =>{
      this.listEnseignant = data as Enseignant[];
    })
  }

  get parcours1(){
    return this.updateForm.get('parcours1');
  }
  get etudiant1(){
    return this.updateForm.get('etudiant1');
  }
  get cin1(){
    return this.updateForm.get('cin1');
  }
  get parcours2(){
    return this.updateForm.get('parcours2');
  }
  get etudiant2(){
    return this.updateForm.get('etudiant2');
  }
  get cin2(){
    return this.updateForm.get('cin2');
  }
  get enseignant(){
    return this.updateForm.get('enseignant');
  }
  get sujet(){
    return this.updateForm.get('sujet');
  }
  get societe(){
    return this.updateForm.get('societe');
  }
  selectChangeParcours1(value: any) {
    this.findEtudiant1ByParcours(value);
  }
  selectChangeParcours2(value: any){
    this.findEtudiant2ByParcours(value);
 }
  updateStage(){

    if (this.updateForm.valid) {
      if (!(this.etudiant1.value > 0)) {
        this.stagepfeUpdate.etudiantPFE1 = null;
      }
      if (!(this.etudiant2.value > 0)) {
        this.stagepfeUpdate.etudiantPFE2 = null;
      }
      this.stagepfeUpdate.societe = this.societe?.value;
      this.stagepfeUpdate.sujet = this.sujet?.value;

      this.enseignantService.findEnseignantById(this.enseignant.value).subscribe(e => {
        this.stagepfeUpdate.enseignant = e as Enseignant;

        this.stageService.updateStagePFE(this.stagepfeUpdate).subscribe(data => {
          if (this.etudiant1.value > 0) {
            this.affecterEtudiant1();
          }
          if (this.etudiant2.value > 0) {
            this.affecterEtudiant2();
          }
          this.snackBar.open("Réclamation envoyer avec succés ", "", {
            duration: 3000,
            verticalPosition: 'top',
            panelClass: ['bb']
          });
          this.close();
          this.reloadComponent();


        }, error => {
          this.snackBar.open(" Echec de modifier un stage PFE  ", "", {
            duration: 3000,
            verticalPosition: 'top',
            panelClass: ['aa']
          });
          console.log(error)
        })

      })
    }else {
      this.validateAllFormFields(this.updateForm);
    }
  }
  affecterEtudiant1(){
    this.etudiantservice.findEtudiantByCin(this.etudiant1.value).subscribe(data =>{
        this.e1 = data as EtudiantPFE;
      this.stageService.affecterEtudiant1(this.e1,this.stagepfeUpdate.id).subscribe(res=>{
      },error => console.log(error))
    },error => console.log(error))
  }
  affecterEtudiant2(){
    this.etudiantservice.findEtudiantByCin(this.etudiant2.value).subscribe(data =>{
      this.e2 = data as EtudiantPFE;
      this.stageService.affecterEtudiant2(this.e2,this.stagepfeUpdate.id).subscribe(res=>{
      },error => console.log(error))
    },error => console.log(error))
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
