import { Component, OnInit } from '@angular/core';
import {StagePFE} from '../Models/stage-pfe';
import {StagePFEService} from '../Services/stage-pfe.service';
import {EnseignantService} from '../Services/enseignant.service';
import {TokenService} from '../Services/token.service';
import {Enseignant} from '../Models/enseignant';

@Component({
  selector: 'app-list-stage-by-departement',
  templateUrl: './list-stage-by-departement.component.html',
  styleUrls: ['./list-stage-by-departement.component.css']
})
export class ListStageByDepartementComponent implements OnInit {
  stagesPFE: StagePFE[];
  enseignant: Enseignant = new Enseignant();
  constructor(private stageService: StagePFEService,
              private enseignantService: EnseignantService,
              private tokenService: TokenService) { }

  ngOnInit(): void {
   this.findByDepartementTitre();
    alert(this.tokenService.getFonction());
    //this.getById();
  }
  getById(){
    this.enseignantService.findEnseignantById(parseInt(this.tokenService.getId())).subscribe(
      data=>{
        this.enseignant = data as Enseignant;
        console.log(this.enseignant);
        alert("+++")
      },error => {alert(parseInt(this.tokenService.getId()));
      console.log(error)
      })
  }
  findByDepartementTitre(){
    this.enseignantService.findEnseignantById(parseInt(this.tokenService.getId())).subscribe(
      data=>{
        this.enseignant = data as Enseignant;
        this.stageService.findByDepartementTitre(this.enseignant.departement?.titre).subscribe(
          res=>{
            this.stagesPFE = res as StagePFE[];
          }
        )
      }
    )
  }
}
