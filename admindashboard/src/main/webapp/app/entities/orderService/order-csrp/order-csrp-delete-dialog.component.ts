import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderCsrp } from 'app/shared/model/orderService/order-csrp.model';
import { OrderCsrpService } from './order-csrp.service';

@Component({
  selector: 'jhi-order-csrp-delete-dialog',
  templateUrl: './order-csrp-delete-dialog.component.html'
})
export class OrderCsrpDeleteDialogComponent {
  orderCsrp: IOrderCsrp;

  constructor(protected orderCsrpService: OrderCsrpService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.orderCsrpService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'orderCsrpListModification',
        content: 'Deleted an orderCsrp'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-order-csrp-delete-popup',
  template: ''
})
export class OrderCsrpDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orderCsrp }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OrderCsrpDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.orderCsrp = orderCsrp;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/order-csrp', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/order-csrp', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
