import { Component, OnInit } from '@angular/core';
import {Observable} from 'rxjs';
import {StagePFE} from '../Models/stage-pfe';
import {StagePFEService} from '../Services/stage-pfe.service';
import {ActivatedRoute, Router} from '@angular/router';
import {TokenService} from '../Services/token.service';
import {StatutService} from '../Services/statut.service';
import {Statut} from '../Models/statut';
import {FormControl, FormGroup} from '@angular/forms';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {UpdateStagePFEComponent} from '../update-stage-pfe/update-stage-pfe.component';
import {EnseignantService} from '../Services/enseignant.service';
import {Enseignant} from '../Models/enseignant';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-list-stage-pfe',
  templateUrl: './list-stage-pfe.component.html',
  styleUrls: ['./list-stage-pfe.component.css']
})
export class ListStagePFEComponent implements OnInit {
  stagesPFE: StagePFE[];
  stagePFESearch : StagePFE[] = [];
  listStatut: Statut[];
  statutInfo: Statut = new Statut();
  stageInfo: StagePFE ;
  enseignant: Enseignant= new Enseignant();
  stagepfe = new FormGroup({
    statut: new FormControl(),
    stage: new FormControl()
  })


  constructor(private service: StagePFEService,
              private enseignantService: EnseignantService,
              private route: ActivatedRoute,
              private router: Router,
              public tokenservice: TokenService,
              public statutservice: StatutService,
              public dialog: MatDialog,
              private snackBar:MatSnackBar) { }

  ngOnInit(): void {
    if(this.tokenservice.getFonction()==="Directeur de departement"){
      this.findStageByDepartement(parseInt(this.tokenservice.getId()));
    }else {
      this.findAllStagePFE();
    }
    this.findAllStatut();



  }
  search(query: string){
    this.stagesPFE = (query) ? this.stagePFESearch.filter(stage => stage.enseignant?.prenomNom.toLowerCase().includes(query.toLowerCase())
      || stage.etudiantPFE1?.prenomNom.toLowerCase().includes(query.toLowerCase())
      || stage.etudiantPFE2?.prenomNom.toLowerCase().includes(query.toLowerCase())
      || stage.statut?.titre.toLowerCase().includes(query.toLowerCase())
      || stage.societe?.toLowerCase().includes(query.toLowerCase())
      || stage.departement?.titre.toLowerCase().includes(query.toLowerCase())
      || stage.etudiantPFE1?.parcours?.titre.toLowerCase().includes(query.toLowerCase())
      || stage.etudiantPFE2?.parcours?.titre.toLowerCase().includes(query.toLowerCase())
    ): this.stagePFESearch;
  }

  findStageByDepartement(id: number): any{
    this.enseignantService.findEnseignantById(id).subscribe(res =>{
    this.service.findByDepartementTitre(res.departement.titre).subscribe(data =>{
      this.stagesPFE = data as StagePFE[];
      this.stagePFESearch = data as StagePFE[];
    })
    })
  }
  findAllStagePFE(): any{
    this.service.findAllStagePFE().subscribe(data=>{
      this.stagesPFE = data as StagePFE[];
      this.stagePFESearch = data as StagePFE[];
    });
  }
  findAllStatut(){
    this.statutservice.findAllStatut().subscribe(data=>{
      this.listStatut = data as Statut[];


    })
  }
  updateStatut(s){
    this.stageInfo = s;
    this.stageInfo.statut = this.statut.value ;
    this.service.updateStagePFE(this.stageInfo).subscribe(data=>{
      this.reloadComponent();
      this.snackBar.open("Statut modifier avec succés ","",{
        duration:3000,
        verticalPosition:'top',
        panelClass: ['bb']
      });
    },error => {
      this.snackBar.open(" Echec de modification de statut  ","",{
        duration:3000,
        verticalPosition:'top',
        panelClass: ['aa']
      });
      console.log(error)
    })

  }
  get statut(){
    return this.stagepfe.get('statut');
  }
  get stage(){
    return this.stagepfe.get('stage');
  }


  UpdateStage(id: number) {
      if(this.tokenservice.getFonction()==="Directeur de departement"){
      const dialogConfig = new MatDialogConfig();
      dialogConfig.disableClose = false;
      dialogConfig.autoFocus =true;
      dialogConfig.width = "80%";
      dialogConfig.height = "130%";
      dialogConfig.data = id;
      this.dialog.open(UpdateStagePFEComponent,dialogConfig);

      }}
  reloadComponent() {
    let currentUrl = this.router.url;
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate([currentUrl]);
  }
}
