import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderVps } from 'app/shared/model/orderService/order-vps.model';

@Component({
  selector: 'jhi-order-vps-detail',
  templateUrl: './order-vps-detail.component.html'
})
export class OrderVpsDetailComponent implements OnInit {
  orderVps: IOrderVps;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orderVps }) => {
      this.orderVps = orderVps;
    });
  }

  previousState() {
    window.history.back();
  }
}
