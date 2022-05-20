import { Component, OnInit } from '@angular/core';
import {StagePFEService} from '../Services/stage-pfe.service';
import {EnseignantService} from '../Services/enseignant.service';
import { saveAs } from "file-saver";
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
const NIME_TYPE ={
  xls: 'application/vnd.ms-excel',
  xlsx: 'application/vnd.openxmlformats-officedocument.spreadsheetxml.sheet'
}
@Component({
  selector: 'app-import-file-stage',
  templateUrl: './import-file-stage.component.html',
  styleUrls: ['./import-file-stage.component.css']
})
export class ImportFileStageComponent implements OnInit {
  formData: FormData = new FormData();
  readytoupload = false;
  constructor(private stageService: StagePFEService,
              private enseignantService: EnseignantService,
              private snackBar:MatSnackBar) { }

  ngOnInit(): void {
  }
  fileChange(event) {
    const fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      const file: File = fileList[0];
      this.formData.append('file', file);
      this.readytoupload = true;
    }
  }
  readFileStage(){
    if(this.readytoupload){
      this.stageService.readFileStage(this.formData).subscribe(data =>{
        this.snackBar.open("Chargement de fichier avec succés ","",{
          duration:3000,
          verticalPosition:'top',
          panelClass: ['bb']
        });
      },error => {
        this.snackBar.open(" Echec de charger de fichier  ","",{
          duration:3000,
          verticalPosition:'top',
          panelClass: ['aa']
        });
        console.log(error)
      });
    }

  }
  downloadFile(){
    const fileName = "Encadrement.xlsx";
    const EXT = fileName.substr(fileName.lastIndexOf('.')+1);
    this.enseignantService.downloadFile({'fileName': fileName}).subscribe(data =>{
      saveAs(new Blob([data], {type: NIME_TYPE[EXT]}),fileName);
      this.snackBar.open("Téléchargement de fichier avec succés ","",{
        duration:3000,
        verticalPosition:'top',
        panelClass: ['bb']
      });
    },error => {
      this.snackBar.open(" Echec de téléchargement de fichier  ","",{
        duration:3000,
        verticalPosition:'top',
        panelClass: ['aa']
      });
      console.log(error);
    })

  }


}
