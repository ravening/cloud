import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdmindashboardTestModule } from '../../../../test.module';
import { PrivateCloudDetailComponent } from 'app/entities/privateCloud/private-cloud/private-cloud-detail.component';
import { PrivateCloud } from 'app/shared/model/privateCloud/private-cloud.model';

describe('Component Tests', () => {
  describe('PrivateCloud Management Detail Component', () => {
    let comp: PrivateCloudDetailComponent;
    let fixture: ComponentFixture<PrivateCloudDetailComponent>;
    const route = ({ data: of({ privateCloud: new PrivateCloud(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdmindashboardTestModule],
        declarations: [PrivateCloudDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PrivateCloudDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrivateCloudDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.privateCloud).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
