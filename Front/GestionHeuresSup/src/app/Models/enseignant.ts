import {Fonction} from './fonction';
import {Grade} from './grade';
import {Departement} from './departement';
import {Etablissement} from './etablissement';
import {StagePFE} from './stage-pfe';
import {HeurePFE} from './heure-pfe';
import {ChargeHoraireEffectif} from './charge-horaire-effectif';
import {Reclamation} from './reclamation';

export class Enseignant {
  id: number;
  prenomNom: string;
  prenomNomArabe: string;
  rib: number;
  cin: number;
  identifiantUnique: number;
  numTel: number;
  email: string;
  dateNaissance: Date;
  lieuNaissance: string;
  lieuNaissanceArabe: string;
  absence: number;
  active: string;
  password: string;
  codeCouleur: string;
  fonction: Fonction;
  grade: Grade;
  departement: Departement;
  liststagepfe: StagePFE[];
  chargeHorairePFE?: HeurePFE;
  chargeHoraireS1?: ChargeHoraireEffectif;
  chargeHoraireS2?: ChargeHoraireEffectif;
  heuresSupp? : ChargeHoraireEffectif;
  reclamations: Reclamation[];
}
