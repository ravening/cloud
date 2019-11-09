export interface IPrivateCloud {
  id?: number;
  domainName?: string;
  accountid?: number;
  accountName?: string;
  cpu?: number;
  ram?: number;
  storage?: number;
  traffic?: number;
}

export class PrivateCloud implements IPrivateCloud {
  constructor(
    public id?: number,
    public domainName?: string,
    public accountid?: number,
    public accountName?: string,
    public cpu?: number,
    public ram?: number,
    public storage?: number,
    public traffic?: number
  ) {}
}
