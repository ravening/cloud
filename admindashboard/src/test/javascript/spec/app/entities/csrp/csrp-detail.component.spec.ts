import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdmindashboardTestModule } from '../../../test.module';
import { CsrpDetailComponent } from 'app/entities/csrp/csrp-detail.component';
import { Csrp } from 'app/shared/model/csrp.model';

describe('Component Tests', () => {
  describe('Csrp Management Detail Component', () => {
    let comp: CsrpDetailComponent;
    let fixture: ComponentFixture<CsrpDetailComponent>;
    const route = ({ data: of({ csrp: new Csrp(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdmindashboardTestModule],
        declarations: [CsrpDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CsrpDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CsrpDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.csrp).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
