import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVirtualMachine } from 'app/shared/model/virtualMachine/virtual-machine.model';

@Component({
  selector: 'jhi-virtual-machine-detail',
  templateUrl: './virtual-machine-detail.component.html'
})
export class VirtualMachineDetailComponent implements OnInit {
  virtualMachine: IVirtualMachine;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ virtualMachine }) => {
      this.virtualMachine = virtualMachine;
    });
  }

  previousState() {
    window.history.back();
  }
}
