/* tslint:disable */
import {Departement} from './departement';
import {Enseignant} from './enseignant';
import {EtudiantPFE} from './etudiant-pfe';
import {Statut} from './statut';

export class StagePFE {
  public id: number;
  public sujet: string;
  public societe: string;
  public departement: Departement;
  public enseignant: Enseignant;
  public etudiantPFE1: EtudiantPFE;
  public etudiantPFE2: EtudiantPFE;
  public statut: Statut;


  constructor(id?: number, sujet?: string, societe?: string, departement?: Departement, enseignant?: Enseignant, etudiantPFE1?: EtudiantPFE, etudiantPFE2?: EtudiantPFE, statut?: Statut) {
    this.id = id;
    this.sujet = sujet;
    this.societe = societe;
    this.departement = departement;
    this.enseignant = enseignant;
    this.etudiantPFE1 = etudiantPFE1;
    this.etudiantPFE2 = etudiantPFE2;
    this.statut = statut;
  }


  getId(): number {
    return this.id;
  }

  setId(value: number) {
    this.id = value;
  }

  getSujet(): string {
    return this.sujet;
  }

  setSujet(value: string) {
    this.sujet = value;
  }

  getSociete(): string {
    return this.societe;
  }

  setSociete(value: string) {
    this.societe = value;
  }

  getDepartement(): Departement {
    return this.departement;
  }

  setDepartement(value: Departement) {
    this.departement = value;
  }

  getEnseignant(): Enseignant {
    return this.enseignant;
  }

  setEnseignant(value: Enseignant) {
    this.enseignant = value;
  }

  getEtudiantPFE1(): EtudiantPFE {
    return this.etudiantPFE1;
  }

  setEtudiantPFE1(value: EtudiantPFE) {
    this.etudiantPFE1 = value;
  }

  getEtudiantPFE2(): EtudiantPFE {
    return this.etudiantPFE2;
  }

  setEtudiantPFE2(value: EtudiantPFE) {
    this.etudiantPFE2 = value;
  }

  getStatut(): Statut {
    return this.statut;
  }

  setStatut(value: Statut) {
    this.statut = value;
  }
}
