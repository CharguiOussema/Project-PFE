import { Component, OnInit } from '@angular/core';
import {StagePFE} from '../Models/stage-pfe';
import {StagePFEService} from '../Services/stage-pfe.service';
import {TokenService} from '../Services/token.service';

@Component({
  selector: 'app-list-stage-by-enseignant',
  templateUrl: './list-stage-by-enseignant.component.html',
  styleUrls: ['./list-stage-by-enseignant.component.css']
})
export class ListStageByEnseignantComponent implements OnInit {
  stagesPFE: StagePFE[];
  constructor(private service: StagePFEService,
              private tokenService: TokenService) { }

  ngOnInit(): void {
    this.findByEnseignantId();
  }
  findByEnseignantId(): any{
    console.log(this.tokenService.getId());
    this.service.findByEnseignantId(parseInt(this.tokenService.getId())).subscribe(data=>{
      this.stagesPFE =data as StagePFE[];
    })
  }
}
