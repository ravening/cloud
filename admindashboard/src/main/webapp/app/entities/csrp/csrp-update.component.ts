import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICsrp, Csrp } from 'app/shared/model/csrp.model';
import { CsrpService } from './csrp.service';

@Component({
  selector: 'jhi-csrp-update',
  templateUrl: './csrp-update.component.html'
})
export class CsrpUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    domainName: [],
    cpu: [],
    ram: [],
    storage: [],
    traffic: []
  });

  constructor(protected csrpService: CsrpService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ csrp }) => {
      this.updateForm(csrp);
    });
  }

  updateForm(csrp: ICsrp) {
    this.editForm.patchValue({
      id: csrp.id,
      domainName: csrp.domainName,
      cpu: csrp.cpu,
      ram: csrp.ram,
      storage: csrp.storage,
      traffic: csrp.traffic
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const csrp = this.createFromForm();
    if (csrp.id !== undefined) {
      this.subscribeToSaveResponse(this.csrpService.update(csrp));
    } else {
      this.subscribeToSaveResponse(this.csrpService.create(csrp));
    }
  }

  private createFromForm(): ICsrp {
    return {
      ...new Csrp(),
      id: this.editForm.get(['id']).value,
      domainName: this.editForm.get(['domainName']).value,
      cpu: this.editForm.get(['cpu']).value,
      ram: this.editForm.get(['ram']).value,
      storage: this.editForm.get(['storage']).value,
      traffic: this.editForm.get(['traffic']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICsrp>>) {
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
