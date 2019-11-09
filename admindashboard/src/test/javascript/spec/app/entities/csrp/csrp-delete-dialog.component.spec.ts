import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AdmindashboardTestModule } from '../../../test.module';
import { CsrpDeleteDialogComponent } from 'app/entities/csrp/csrp-delete-dialog.component';
import { CsrpService } from 'app/entities/csrp/csrp.service';

describe('Component Tests', () => {
  describe('Csrp Management Delete Component', () => {
    let comp: CsrpDeleteDialogComponent;
    let fixture: ComponentFixture<CsrpDeleteDialogComponent>;
    let service: CsrpService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdmindashboardTestModule],
        declarations: [CsrpDeleteDialogComponent]
      })
        .overrideTemplate(CsrpDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CsrpDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CsrpService);
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
