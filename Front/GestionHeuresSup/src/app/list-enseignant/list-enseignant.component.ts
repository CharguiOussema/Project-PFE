import { Component, OnInit } from '@angular/core';
import {Enseignant} from '../Models/enseignant';
import {EnseignantService} from '../Services/enseignant.service';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {DetailEnseignantComponent} from '../detail-enseignant/detail-enseignant.component';
import {TokenService} from '../Services/token.service';
import {UpdateEnseignant2Component} from '../update-enseignant2/update-enseignant2.component';

@Component({
  selector: 'app-list-enseignant',
  templateUrl: './list-enseignant.component.html',
  styleUrls: ['./list-enseignant.component.css']
})
export class ListEnseignantComponent implements OnInit {
  listeEnseignant: Enseignant[];
  searchEnseignant: Enseignant[] = [];
  constructor(private enseignantService: EnseignantService,
              public dialog: MatDialog,
              public tokenService: TokenService) { }

  ngOnInit(): void {
    this.findAllEnseignant();
  }
  search(query: string){
    this.listeEnseignant = (query) ? this.searchEnseignant.filter(enseignant => enseignant.prenomNom.toLowerCase().includes(query.toLowerCase())
      || enseignant.identifiantUnique.toString().includes(query)
      || enseignant.email?.toLowerCase().includes(query.toLowerCase())
      || enseignant.grade?.titre.toLowerCase().includes(query.toLowerCase())
      || enseignant.fonction?.titre.toLowerCase().includes(query.toLowerCase())
      || enseignant.departement?.titre.toLowerCase().includes(query.toLowerCase())

    ): this.searchEnseignant;
  }

  findAllEnseignant(){
    this.enseignantService.findAllEnseignant().subscribe(data =>{
      this.listeEnseignant = data as Enseignant [];
      this.searchEnseignant = data as Enseignant [];
    })
  }
  detail(e:Enseignant){
   // console.log("id => "+ e.id);
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;
    dialogConfig.height = "110%";
    dialogConfig.data = e;
    if(this.tokenService.getFonction()==="ressource humaine"){
      dialogConfig.width = "80%"
      this.dialog.open(UpdateEnseignant2Component, dialogConfig);
    }else {
      this.dialog.open(DetailEnseignantComponent, dialogConfig);
    }
    }
}
