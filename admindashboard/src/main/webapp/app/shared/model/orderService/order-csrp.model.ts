export interface IOrderCsrp {
  id?: number;
  name?: string;
  csrppack?: number;
}

export class OrderCsrp implements IOrderCsrp {
  constructor(public id?: number, public name?: string, public csrppack?: number) {}
}
