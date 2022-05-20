/* tslint:disable */
import { Component, OnInit } from '@angular/core';
import {TokenService} from '../Services/token.service';
import {EnseignantService} from '../Services/enseignant.service';
import {Enseignant} from '../Models/enseignant';
import {error} from '@angular/compiler/src/util';
import {Router} from '@angular/router';
import {EtablissementService} from '../Services/etablissement.service';
import {Etablissement} from '../Models/etablissement';
import {ReclamationService} from '../Services/reclamation.service';
import {Reclamation} from '../Models/reclamation';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  user: Enseignant = new Enseignant();
  listReclamation: Reclamation[];
  sizeReclamation = 0;
  constructor(public tokenService: TokenService,
              public enseignantServices: EnseignantService,
              private route: Router,
              private reclamationService: ReclamationService) { }

  ngOnInit(): void {
    this.findEnseignantById(parseInt(this.tokenService.getId()));
    this.findAllReclamation();
  }

  findEnseignantById(id: number){
    this.enseignantServices.findEnseignantById(id).subscribe(data => {
      this.user = data as Enseignant;
    }, error => console.log(error));
  }
  logout(){
    this.tokenService.remove();
    this.route.navigate(['/login']);
  }
  findAllReclamation(){
    if (this.tokenService.getFonction() === 'ressource humaine'){
      this.reclamationService.findByDeuxType('Statut PFE', 'Information personnelle').subscribe(data => {
        this.listReclamation = data as Reclamation[];
        this.sizeReclamation = this.listReclamation.length;

      });

    }else if (this.tokenService.getFonction() === 'Directeur de departement'){
      this.enseignantServices.findEnseignantById(parseInt(this.tokenService.getId())).subscribe(res => {


        this.reclamationService.findByDepartementAndDeuxType(res.departement.titre, 'Charge horaire', 'Sujet PFE').subscribe(data => {
          this.listReclamation = data as Reclamation[];
          this.sizeReclamation = this.listReclamation.length;

        });
      });
    }
  }

}
