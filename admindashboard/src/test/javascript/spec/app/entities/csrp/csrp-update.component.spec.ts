import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AdmindashboardTestModule } from '../../../test.module';
import { CsrpUpdateComponent } from 'app/entities/csrp/csrp-update.component';
import { CsrpService } from 'app/entities/csrp/csrp.service';
import { Csrp } from 'app/shared/model/csrp.model';

describe('Component Tests', () => {
  describe('Csrp Management Update Component', () => {
    let comp: CsrpUpdateComponent;
    let fixture: ComponentFixture<CsrpUpdateComponent>;
    let service: CsrpService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdmindashboardTestModule],
        declarations: [CsrpUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CsrpUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CsrpUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CsrpService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Csrp(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Csrp();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
