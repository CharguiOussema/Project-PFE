import {Enseignant} from './enseignant';

export class Departement {
  id: number;
  titre: string;
  listeenseignant?: Enseignant[];
}
