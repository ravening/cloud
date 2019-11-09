export interface ICsrp {
  id?: number;
  domainName?: string;
  cpu?: number;
  ram?: number;
  storage?: number;
  traffic?: number;
}

export class Csrp implements ICsrp {
  constructor(
    public id?: number,
    public domainName?: string,
    public cpu?: number,
    public ram?: number,
    public storage?: number,
    public traffic?: number
  ) {}
}
