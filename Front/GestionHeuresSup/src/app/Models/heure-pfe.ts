import {ChargeHoraire} from './charge-horaire';

export class HeurePFE extends ChargeHoraire{
  nombre: number;
  coefficient: number;
  constructor(nombre?: number, coefficient?: number){
    super();
    this.coefficient = 2
  }
}
