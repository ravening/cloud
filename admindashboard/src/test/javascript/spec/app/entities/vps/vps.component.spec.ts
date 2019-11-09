import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AdmindashboardTestModule } from '../../../test.module';
import { VpsComponent } from 'app/entities/vps/vps.component';
import { VpsService } from 'app/entities/vps/vps.service';
import { Vps } from 'app/shared/model/vps.model';

describe('Component Tests', () => {
  describe('Vps Management Component', () => {
    let comp: VpsComponent;
    let fixture: ComponentFixture<VpsComponent>;
    let service: VpsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdmindashboardTestModule],
        declarations: [VpsComponent],
        providers: []
      })
        .overrideTemplate(VpsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VpsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VpsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Vps(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.vps[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
