import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICsrp } from 'app/shared/model/csrp.model';
import { AccountService } from 'app/core/auth/account.service';
import { CsrpService } from './csrp.service';

@Component({
  selector: 'jhi-csrp',
  templateUrl: './csrp.component.html'
})
export class CsrpComponent implements OnInit, OnDestroy {
  csrps: ICsrp[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(protected csrpService: CsrpService, protected eventManager: JhiEventManager, protected accountService: AccountService) {}

  loadAll() {
    this.csrpService
      .query()
      .pipe(
        filter((res: HttpResponse<ICsrp[]>) => res.ok),
        map((res: HttpResponse<ICsrp[]>) => res.body)
      )
      .subscribe((res: ICsrp[]) => {
        this.csrps = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCsrps();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICsrp) {
    return item.id;
  }

  registerChangeInCsrps() {
    this.eventSubscriber = this.eventManager.subscribe('csrpListModification', response => this.loadAll());
  }
}
