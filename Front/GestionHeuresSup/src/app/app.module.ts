import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';
import { DashboardComponent } from './dashboard/dashboard.component';
import {SignInComponent} from './sign-in/sign-in.component';
import {RegisterComponent} from './register/register.component';
import {UpdateStagePFEComponent} from './update-stage-pfe/update-stage-pfe.component';
import {UpdateEnseignantComponent} from './update-enseignant/update-enseignant.component';
import {ListStagePFEComponent} from './list-stage-pfe/list-stage-pfe.component';
import {ListStageByEnseignantComponent} from './list-stage-by-enseignant/list-stage-by-enseignant.component';
import {ListStageByDepartementComponent} from './list-stage-by-departement/list-stage-by-departement.component';
import {AddStagePFEComponent} from './add-stage-pfe/add-stage-pfe.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import {authInterceptorProviders} from './Services/Helpers/auth-interceptor';
import { ImportFileStageComponent } from './import-file-stage/import-file-stage.component';
import { ImportFileEnseignantComponent } from './import-file-enseignant/import-file-enseignant.component';
import { ListChargeHoraireComponent } from './list-charge-horaire/list-charge-horaire.component';
import { UpdateChargeHoraireS1Component } from './update-charge-horaire-s1/update-charge-horaire-s1.component';
import { UpdateChargeHoraireS2Component } from './update-charge-horaire-s2/update-charge-horaire-s2.component';
import { UpdateChargePFEComponent } from './update-charge-pfe/update-charge-pfe.component';
import { ListEnseignantComponent } from './list-enseignant/list-enseignant.component';
import { DetailEnseignantComponent } from './detail-enseignant/detail-enseignant.component';
import { AddEnseignantComponent } from './add-enseignant/add-enseignant.component';
import { UpdateEtablissementComponent } from './update-etablissement/update-etablissement.component';
import { MemoireEnseignantComponent } from './memoire-enseignant/memoire-enseignant.component';
import { ListeMemoireComponent } from './liste-memoire/liste-memoire.component';
import { RecapEnseignantsComponent } from './recap-enseignants/recap-enseignants.component';
import { DossierEnseignantComponent } from './dossier-enseignant/dossier-enseignant.component';
import { ListEnseignantByActiveComponent } from './list-enseignant-by-active/list-enseignant-by-active.component';
import { DetailValidationEnseignantsComponent } from './detail-validation-enseignants/detail-validation-enseignants.component';
import { ReclamationComponent } from './reclamation/reclamation.component';
import { ReponseReclamationComponent } from './reponse-reclamation/reponse-reclamation.component';
import { UpdateEnseignant2Component } from './update-enseignant2/update-enseignant2.component';
import {MatSnackBarModule} from '@angular/material/snack-bar';



@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    SignInComponent,
    RegisterComponent,
    UpdateStagePFEComponent,
    UpdateEnseignantComponent,
    ListStagePFEComponent,
    ListStageByEnseignantComponent,
    ListStageByDepartementComponent,
    AddStagePFEComponent,
    ImportFileStageComponent,
    ImportFileEnseignantComponent,
    ListChargeHoraireComponent,
    UpdateChargeHoraireS1Component,
    UpdateChargeHoraireS2Component,
    UpdateChargePFEComponent,
    ListEnseignantComponent,
    DetailEnseignantComponent,
    AddEnseignantComponent,
    UpdateEtablissementComponent,
    MemoireEnseignantComponent,
    ListeMemoireComponent,
    RecapEnseignantsComponent,
    DossierEnseignantComponent,
    ListEnseignantByActiveComponent,
    DetailValidationEnseignantsComponent,
    ReclamationComponent,
    ReponseReclamationComponent,
    UpdateEnseignant2Component
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MatButtonModule,
        ReactiveFormsModule,
        HttpClientModule,
        MatDialogModule,
        MatSnackBarModule


    ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent],
  entryComponents:[UpdateStagePFEComponent]
})
export class AppModule { }
