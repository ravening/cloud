import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IVps } from 'app/shared/model/vps.model';
import { AccountService } from 'app/core/auth/account.service';
import { VpsService } from './vps.service';

@Component({
  selector: 'jhi-vps',
  templateUrl: './vps.component.html'
})
export class VpsComponent implements OnInit, OnDestroy {
  vps: IVps[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(protected vpsService: VpsService, protected eventManager: JhiEventManager, protected accountService: AccountService) {}

  loadAll() {
    this.vpsService
      .query()
      .pipe(
        filter((res: HttpResponse<IVps[]>) => res.ok),
        map((res: HttpResponse<IVps[]>) => res.body)
      )
      .subscribe((res: IVps[]) => {
        this.vps = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInVps();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IVps) {
    return item.id;
  }

  registerChangeInVps() {
    this.eventSubscriber = this.eventManager.subscribe('vpsListModification', response => this.loadAll());
  }
}
