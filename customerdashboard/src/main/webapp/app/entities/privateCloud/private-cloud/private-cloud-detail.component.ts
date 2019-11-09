import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrivateCloud } from 'app/shared/model/privateCloud/private-cloud.model';

@Component({
  selector: 'jhi-private-cloud-detail',
  templateUrl: './private-cloud-detail.component.html'
})
export class PrivateCloudDetailComponent implements OnInit {
  privateCloud: IPrivateCloud;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ privateCloud }) => {
      this.privateCloud = privateCloud;
    });
  }

  previousState() {
    window.history.back();
  }
}
