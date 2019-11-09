export interface IUserAccount {
  id?: number;
  name?: string;
  domainName?: string;
  email?: string;
}

export class UserAccount implements IUserAccount {
  constructor(public id?: number, public name?: string, public domainName?: string, public email?: string) {}
}
