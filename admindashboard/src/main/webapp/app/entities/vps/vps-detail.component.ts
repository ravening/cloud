import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVps } from 'app/shared/model/vps.model';

@Component({
  selector: 'jhi-vps-detail',
  templateUrl: './vps-detail.component.html'
})
export class VpsDetailComponent implements OnInit {
  vps: IVps;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vps }) => {
      this.vps = vps;
    });
  }

  previousState() {
    window.history.back();
  }
}
