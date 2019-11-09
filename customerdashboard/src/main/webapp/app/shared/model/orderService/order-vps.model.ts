export interface IOrderVps {
  id?: number;
  name?: string;
  vpspack?: number;
}

export class OrderVps implements IOrderVps {
  constructor(public name?: string, public vpspack?: number) {}
}
