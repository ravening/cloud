import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AdmindashboardTestModule } from '../../../test.module';
import { VpsUpdateComponent } from 'app/entities/vps/vps-update.component';
import { VpsService } from 'app/entities/vps/vps.service';
import { Vps } from 'app/shared/model/vps.model';

describe('Component Tests', () => {
  describe('Vps Management Update Component', () => {
    let comp: VpsUpdateComponent;
    let fixture: ComponentFixture<VpsUpdateComponent>;
    let service: VpsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdmindashboardTestModule],
        declarations: [VpsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VpsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VpsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VpsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Vps(123);
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
        const entity = new Vps();
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
