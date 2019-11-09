import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AdmindashboardTestModule } from '../../../../test.module';
import { VirtualMachineUpdateComponent } from 'app/entities/virtualMachine/virtual-machine/virtual-machine-update.component';
import { VirtualMachineService } from 'app/entities/virtualMachine/virtual-machine/virtual-machine.service';
import { VirtualMachine } from 'app/shared/model/virtualMachine/virtual-machine.model';

describe('Component Tests', () => {
  describe('VirtualMachine Management Update Component', () => {
    let comp: VirtualMachineUpdateComponent;
    let fixture: ComponentFixture<VirtualMachineUpdateComponent>;
    let service: VirtualMachineService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdmindashboardTestModule],
        declarations: [VirtualMachineUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VirtualMachineUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VirtualMachineUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VirtualMachineService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VirtualMachine(123);
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
        const entity = new VirtualMachine();
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
