import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AdmindashboardTestModule } from '../../../test.module';
import { VpsDeleteDialogComponent } from 'app/entities/vps/vps-delete-dialog.component';
import { VpsService } from 'app/entities/vps/vps.service';

describe('Component Tests', () => {
  describe('Vps Management Delete Component', () => {
    let comp: VpsDeleteDialogComponent;
    let fixture: ComponentFixture<VpsDeleteDialogComponent>;
    let service: VpsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdmindashboardTestModule],
        declarations: [VpsDeleteDialogComponent]
      })
        .overrideTemplate(VpsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VpsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VpsService);
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
