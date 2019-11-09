import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderCsrp } from 'app/shared/model/orderService/order-csrp.model';

@Component({
  selector: 'jhi-order-csrp-detail',
  templateUrl: './order-csrp-detail.component.html'
})
export class OrderCsrpDetailComponent implements OnInit {
  orderCsrp: IOrderCsrp;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orderCsrp }) => {
      this.orderCsrp = orderCsrp;
    });
  }

  previousState() {
    window.history.back();
  }
}
