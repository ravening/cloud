import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IVps, Vps } from 'app/shared/model/vps.model';
import { VpsService } from './vps.service';

@Component({
  selector: 'jhi-vps-update',
  templateUrl: './vps-update.component.html'
})
export class VpsUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    cpu: [],
    ram: [],
    disk: [],
    traffic: [],
    template: []
  });

  constructor(protected vpsService: VpsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vps }) => {
      this.updateForm(vps);
    });
  }

  updateForm(vps: IVps) {
    this.editForm.patchValue({
      id: vps.id,
      name: vps.name,
      cpu: vps.cpu,
      ram: vps.ram,
      disk: vps.disk,
      traffic: vps.traffic,
      template: vps.template
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vps = this.createFromForm();
    if (vps.id !== undefined) {
      this.subscribeToSaveResponse(this.vpsService.update(vps));
    } else {
      this.subscribeToSaveResponse(this.vpsService.create(vps));
    }
  }

  private createFromForm(): IVps {
    return {
      ...new Vps(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      cpu: this.editForm.get(['cpu']).value,
      ram: this.editForm.get(['ram']).value,
      disk: this.editForm.get(['disk']).value,
      traffic: this.editForm.get(['traffic']).value,
      template: this.editForm.get(['template']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVps>>) {
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
