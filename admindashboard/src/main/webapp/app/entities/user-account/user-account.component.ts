import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IUserAccount } from 'app/shared/model/user-account.model';
import { AccountService } from 'app/core/auth/account.service';
import { UserAccountService } from './user-account.service';

@Component({
  selector: 'jhi-user-account',
  templateUrl: './user-account.component.html'
})
export class UserAccountComponent implements OnInit, OnDestroy {
  userAccounts: IUserAccount[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected userAccountService: UserAccountService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.userAccountService
      .query()
      .pipe(
        filter((res: HttpResponse<IUserAccount[]>) => res.ok),
        map((res: HttpResponse<IUserAccount[]>) => res.body)
      )
      .subscribe((res: IUserAccount[]) => {
        this.userAccounts = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInUserAccounts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUserAccount) {
    return item.id;
  }

  registerChangeInUserAccounts() {
    this.eventSubscriber = this.eventManager.subscribe('userAccountListModification', response => this.loadAll());
  }
}
