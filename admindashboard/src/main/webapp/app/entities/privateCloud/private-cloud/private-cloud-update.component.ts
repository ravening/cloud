import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPrivateCloud, PrivateCloud } from 'app/shared/model/privateCloud/private-cloud.model';
import { PrivateCloudService } from './private-cloud.service';

@Component({
  selector: 'jhi-private-cloud-update',
  templateUrl: './private-cloud-update.component.html'
})
export class PrivateCloudUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    domainName: [],
    accountid: [],
    accountName: [],
    cpu: [],
    ram: [],
    storage: [],
    traffic: []
  });

  constructor(protected privateCloudService: PrivateCloudService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ privateCloud }) => {
      this.updateForm(privateCloud);
    });
  }

  updateForm(privateCloud: IPrivateCloud) {
    this.editForm.patchValue({
      id: privateCloud.id,
      domainName: privateCloud.domainName,
      accountid: privateCloud.accountid,
      accountName: privateCloud.accountName,
      cpu: privateCloud.cpu,
      ram: privateCloud.ram,
      storage: privateCloud.storage,
      traffic: privateCloud.traffic
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const privateCloud = this.createFromForm();
    if (privateCloud.id !== undefined) {
      this.subscribeToSaveResponse(this.privateCloudService.update(privateCloud));
    } else {
      this.subscribeToSaveResponse(this.privateCloudService.create(privateCloud));
    }
  }

  private createFromForm(): IPrivateCloud {
    return {
      ...new PrivateCloud(),
      id: this.editForm.get(['id']).value,
      domainName: this.editForm.get(['domainName']).value,
      accountid: this.editForm.get(['accountid']).value,
      accountName: this.editForm.get(['accountName']).value,
      cpu: this.editForm.get(['cpu']).value,
      ram: this.editForm.get(['ram']).value,
      storage: this.editForm.get(['storage']).value,
      traffic: this.editForm.get(['traffic']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrivateCloud>>) {
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
