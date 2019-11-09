import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdmindashboardTestModule } from '../../../test.module';
import { VpsDetailComponent } from 'app/entities/vps/vps-detail.component';
import { Vps } from 'app/shared/model/vps.model';

describe('Component Tests', () => {
  describe('Vps Management Detail Component', () => {
    let comp: VpsDetailComponent;
    let fixture: ComponentFixture<VpsDetailComponent>;
    const route = ({ data: of({ vps: new Vps(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdmindashboardTestModule],
        declarations: [VpsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VpsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VpsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vps).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
