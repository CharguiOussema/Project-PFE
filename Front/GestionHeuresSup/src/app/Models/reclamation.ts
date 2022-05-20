import {Enseignant} from './enseignant';
import {TypeReclamation} from './type-reclamation';

export class Reclamation {
  id: number;
  description: string;
  enseignant: Enseignant;
  typeReclamation: TypeReclamation;
}
