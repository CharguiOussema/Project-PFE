/* tslint:disable */
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {DashboardComponent} from './dashboard/dashboard.component';
import {AddStagePFEComponent} from './add-stage-pfe/add-stage-pfe.component';
import {SignInComponent} from './sign-in/sign-in.component';
import {RegisterComponent} from './register/register.component';
import {UpdateEnseignantComponent} from './update-enseignant/update-enseignant.component';
import {UpdateStagePFEComponent} from './update-stage-pfe/update-stage-pfe.component';
import {ListStageByEnseignantComponent} from './list-stage-by-enseignant/list-stage-by-enseignant.component';
import {ListStageByDepartementComponent} from './list-stage-by-departement/list-stage-by-departement.component';
import {ListStagePFEComponent} from './list-stage-pfe/list-stage-pfe.component';
import {ImportFileStageComponent} from './import-file-stage/import-file-stage.component';
import {ImportFileEnseignantComponent} from './import-file-enseignant/import-file-enseignant.component';
import {ListChargeHoraireComponent} from './list-charge-horaire/list-charge-horaire.component';
import {UpdateChargeHoraireS1Component} from './update-charge-horaire-s1/update-charge-horaire-s1.component';
import {UpdateChargeHoraireS2Component} from './update-charge-horaire-s2/update-charge-horaire-s2.component';
import {UpdateChargePFEComponent} from './update-charge-pfe/update-charge-pfe.component';
import {ListEnseignantComponent} from './list-enseignant/list-enseignant.component';
import {DetailEnseignantComponent} from './detail-enseignant/detail-enseignant.component';
import {AddEnseignantComponent} from './add-enseignant/add-enseignant.component';
import {UpdateEtablissementComponent} from './update-etablissement/update-etablissement.component';
import {MemoireEnseignantComponent} from './memoire-enseignant/memoire-enseignant.component';
import {ListeMemoireComponent} from './liste-memoire/liste-memoire.component';
import {RecapEnseignantsComponent} from './recap-enseignants/recap-enseignants.component';
import {DossierEnseignantComponent} from './dossier-enseignant/dossier-enseignant.component';
import {ListEnseignantByActiveComponent} from './list-enseignant-by-active/list-enseignant-by-active.component';
import {DetailValidationEnseignantsComponent} from './detail-validation-enseignants/detail-validation-enseignants.component';
import {ReclamationComponent} from './reclamation/reclamation.component';
import {ReponseReclamationComponent} from './reponse-reclamation/reponse-reclamation.component';
import {UpdateEnseignant2Component} from './update-enseignant2/update-enseignant2.component';


const routes: Routes = [
  {path: '', component: DashboardComponent, children : [

      {path: 'addstagePFE', component: AddStagePFEComponent},
      {path: 'updateStagePFE', component: UpdateStagePFEComponent},
      {path: 'listeStageByEnseignant', component: ListStageByEnseignantComponent},
      {path: 'ListeStageByDepartement', component: ListStageByDepartementComponent},
      {path: 'listeStagePFE', component: ListStagePFEComponent},
      {path: 'updateEnseignant', component: UpdateEnseignantComponent},
      {path: 'importFileStage', component: ImportFileStageComponent},
      {path: 'importFileEnseignant', component: ImportFileEnseignantComponent},
      {path: 'listeChargeHoraire', component: ListChargeHoraireComponent},
      {path: 'UpdateChargeHoraireS1', component: UpdateChargeHoraireS1Component},
      {path: 'UpdateChargeHoraireS2', component: UpdateChargeHoraireS2Component},
      {path: 'UpdateChargeHorairePFE', component: UpdateChargePFEComponent},
      {path: 'listeEnseignant', component: ListEnseignantComponent},
      {path: 'detailEnseignant', component: DetailEnseignantComponent},
      {path: 'addEnseignant', component: AddEnseignantComponent},
      {path: 'updateEtablissement', component: UpdateEtablissementComponent},
      {path: 'memoireEnseignant', component: MemoireEnseignantComponent},
      {path: 'listeMemoire', component: ListeMemoireComponent},
      {path: 'recapEnseignant', component: RecapEnseignantsComponent},
      {path: 'dossierEnseignant', component: DossierEnseignantComponent},
      {path: 'validerEnseignant', component: ListEnseignantByActiveComponent},
      {path: 'detailValidationEnseignants', component: DetailValidationEnseignantsComponent},
      {path: 'reclamation', component: ReclamationComponent},
      {path: 'reponseReclamation/:id', component: ReponseReclamationComponent},
      {path: 'UpdateEnseignant2',component: UpdateEnseignant2Component}


  ]},
  {path: 'login', component: SignInComponent},
  {path: 'register', component: RegisterComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
