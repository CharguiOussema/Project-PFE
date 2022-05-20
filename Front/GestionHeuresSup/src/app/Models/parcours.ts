import {Observable} from 'rxjs';
import {EtudiantPFE} from './etudiant-pfe';

export class Parcours {
  id: number;
  titre: string;
  etudiantsPFE?: Observable<EtudiantPFE[]>;
}
