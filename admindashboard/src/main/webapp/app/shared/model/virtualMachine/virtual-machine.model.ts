export interface IVirtualMachine {
  id?: number;
  name?: string;
  accountid?: number;
  accountName?: string;
  cpu?: number;
  ram?: number;
  disk?: number;
  traffic?: number;
  template?: string;
}

export class VirtualMachine implements IVirtualMachine {
  constructor(
    public id?: number,
    public name?: string,
    public accountid?: number,
    public accountName?: string,
    public cpu?: number,
    public ram?: number,
    public disk?: number,
    public traffic?: number,
    public template?: string
  ) {}
}
