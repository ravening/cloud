import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AdmindashboardTestModule } from '../../../../test.module';
import { PrivateCloudDeleteDialogComponent } from 'app/entities/privateCloud/private-cloud/private-cloud-delete-dialog.component';
import { PrivateCloudService } from 'app/entities/privateCloud/private-cloud/private-cloud.service';

describe('Component Tests', () => {
  describe('PrivateCloud Management Delete Component', () => {
    let comp: PrivateCloudDeleteDialogComponent;
    let fixture: ComponentFixture<PrivateCloudDeleteDialogComponent>;
    let service: PrivateCloudService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdmindashboardTestModule],
        declarations: [PrivateCloudDeleteDialogComponent]
      })
        .overrideTemplate(PrivateCloudDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrivateCloudDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrivateCloudService);
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
