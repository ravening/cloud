export interface IOrderVps {
  id?: number;
  name?: string;
  vpspack?: number;
}

export class OrderVps implements IOrderVps {
  constructor(public id?: number, public name?: string, public vpspack?: number) {}
}
