import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AdmindashboardTestModule } from '../../../../test.module';
import { PrivateCloudUpdateComponent } from 'app/entities/privateCloud/private-cloud/private-cloud-update.component';
import { PrivateCloudService } from 'app/entities/privateCloud/private-cloud/private-cloud.service';
import { PrivateCloud } from 'app/shared/model/privateCloud/private-cloud.model';

describe('Component Tests', () => {
  describe('PrivateCloud Management Update Component', () => {
    let comp: PrivateCloudUpdateComponent;
    let fixture: ComponentFixture<PrivateCloudUpdateComponent>;
    let service: PrivateCloudService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdmindashboardTestModule],
        declarations: [PrivateCloudUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PrivateCloudUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrivateCloudUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrivateCloudService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PrivateCloud(123);
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
        const entity = new PrivateCloud();
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
