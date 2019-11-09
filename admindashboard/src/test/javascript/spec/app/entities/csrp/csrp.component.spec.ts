import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AdmindashboardTestModule } from '../../../test.module';
import { CsrpComponent } from 'app/entities/csrp/csrp.component';
import { CsrpService } from 'app/entities/csrp/csrp.service';
import { Csrp } from 'app/shared/model/csrp.model';

describe('Component Tests', () => {
  describe('Csrp Management Component', () => {
    let comp: CsrpComponent;
    let fixture: ComponentFixture<CsrpComponent>;
    let service: CsrpService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdmindashboardTestModule],
        declarations: [CsrpComponent],
        providers: []
      })
        .overrideTemplate(CsrpComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CsrpComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CsrpService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Csrp(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.csrps[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
