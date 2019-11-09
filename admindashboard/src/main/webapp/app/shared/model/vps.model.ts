export interface IVps {
  id?: number;
  name?: string;
  cpu?: number;
  ram?: number;
  disk?: number;
  traffic?: number;
  template?: string;
}

export class Vps implements IVps {
  constructor(
    public id?: number,
    public name?: string,
    public cpu?: number,
    public ram?: number,
    public disk?: number,
    public traffic?: number,
    public template?: string
  ) {}
}
