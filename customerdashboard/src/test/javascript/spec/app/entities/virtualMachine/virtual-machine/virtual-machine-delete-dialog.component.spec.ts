import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CustomerdashboardTestModule } from '../../../../test.module';
import { VirtualMachineDeleteDialogComponent } from 'app/entities/virtualMachine/virtual-machine/virtual-machine-delete-dialog.component';
import { VirtualMachineService } from 'app/entities/virtualMachine/virtual-machine/virtual-machine.service';

describe('Component Tests', () => {
  describe('VirtualMachine Management Delete Component', () => {
    let comp: VirtualMachineDeleteDialogComponent;
    let fixture: ComponentFixture<VirtualMachineDeleteDialogComponent>;
    let service: VirtualMachineService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerdashboardTestModule],
        declarations: [VirtualMachineDeleteDialogComponent]
      })
        .overrideTemplate(VirtualMachineDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VirtualMachineDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VirtualMachineService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
