import {Observable} from 'rxjs';
import {StagePFE} from './stage-pfe';

export class Statut {
  id: number;
  titre: string;
  stagespfe: Observable<StagePFE[]>;
}
