import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IOrderCsrp, OrderCsrp } from 'app/shared/model/orderService/order-csrp.model';
import { OrderCsrpService } from './order-csrp.service';

@Component({
  selector: 'jhi-order-csrp-update',
  templateUrl: './order-csrp-update.component.html'
})
export class OrderCsrpUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    csrppack: []
  });

  constructor(protected orderCsrpService: OrderCsrpService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ orderCsrp }) => {
      this.updateForm(orderCsrp);
    });
  }

  updateForm(orderCsrp: IOrderCsrp) {
    this.editForm.patchValue({
      id: orderCsrp.id,
      name: orderCsrp.name,
      csrppack: orderCsrp.csrppack
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const orderCsrp = this.createFromForm();
    if (orderCsrp.id !== undefined) {
      this.subscribeToSaveResponse(this.orderCsrpService.update(orderCsrp));
    } else {
      this.subscribeToSaveResponse(this.orderCsrpService.create(orderCsrp));
    }
  }

  private createFromForm(): IOrderCsrp {
    return {
      ...new OrderCsrp(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      csrppack: this.editForm.get(['csrppack']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderCsrp>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
