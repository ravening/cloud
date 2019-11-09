import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICsrp } from 'app/shared/model/csrp.model';

@Component({
  selector: 'jhi-csrp-detail',
  templateUrl: './csrp-detail.component.html'
})
export class CsrpDetailComponent implements OnInit {
  csrp: ICsrp;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ csrp }) => {
      this.csrp = csrp;
    });
  }

  previousState() {
    window.history.back();
  }
}
