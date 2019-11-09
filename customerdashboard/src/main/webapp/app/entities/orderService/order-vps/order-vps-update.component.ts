import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IOrderVps, OrderVps } from 'app/shared/model/orderService/order-vps.model';
import { OrderVpsService } from './order-vps.service';

@Component({
  selector: 'jhi-order-vps-update',
  templateUrl: './order-vps-update.component.html'
})
export class OrderVpsUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    vpspack: []
  });

  constructor(protected orderVpsService: OrderVpsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ orderVps }) => {
      this.updateForm(orderVps);
    });
  }

  updateForm(orderVps: IOrderVps) {
    this.editForm.patchValue({
      id: orderVps.id,
      name: orderVps.name,
      vpspack: orderVps.vpspack
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const orderVps = this.createFromForm();
    if (orderVps.id !== undefined) {
      this.subscribeToSaveResponse(this.orderVpsService.update(orderVps));
    } else {
      this.subscribeToSaveResponse(this.orderVpsService.create(orderVps));
    }
  }

  private createFromForm(): IOrderVps {
    return {
      ...new OrderVps(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      vpspack: this.editForm.get(['vpspack']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderVps>>) {
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
