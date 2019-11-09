import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IVirtualMachine, VirtualMachine } from 'app/shared/model/virtualMachine/virtual-machine.model';
import { VirtualMachineService } from './virtual-machine.service';

@Component({
  selector: 'jhi-virtual-machine-update',
  templateUrl: './virtual-machine-update.component.html'
})
export class VirtualMachineUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    accountid: [],
    accountName: [],
    cpu: [],
    ram: [],
    disk: [],
    traffic: [],
    template: []
  });

  constructor(protected virtualMachineService: VirtualMachineService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ virtualMachine }) => {
      this.updateForm(virtualMachine);
    });
  }

  updateForm(virtualMachine: IVirtualMachine) {
    this.editForm.patchValue({
      id: virtualMachine.id,
      name: virtualMachine.name,
      accountid: virtualMachine.accountid,
      accountName: virtualMachine.accountName,
      cpu: virtualMachine.cpu,
      ram: virtualMachine.ram,
      disk: virtualMachine.disk,
      traffic: virtualMachine.traffic,
      template: virtualMachine.template
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const virtualMachine = this.createFromForm();
    if (virtualMachine.id !== undefined) {
      this.subscribeToSaveResponse(this.virtualMachineService.update(virtualMachine));
    } else {
      this.subscribeToSaveResponse(this.virtualMachineService.create(virtualMachine));
    }
  }

  private createFromForm(): IVirtualMachine {
    return {
      ...new VirtualMachine(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      accountid: this.editForm.get(['accountid']).value,
      accountName: this.editForm.get(['accountName']).value,
      cpu: this.editForm.get(['cpu']).value,
      ram: this.editForm.get(['ram']).value,
      disk: this.editForm.get(['disk']).value,
      traffic: this.editForm.get(['traffic']).value,
      template: this.editForm.get(['template']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVirtualMachine>>) {
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
