import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AdmindashboardTestModule } from '../../../../test.module';
import { OrderVpsDeleteDialogComponent } from 'app/entities/orderService/order-vps/order-vps-delete-dialog.component';
import { OrderVpsService } from 'app/entities/orderService/order-vps/order-vps.service';

describe('Component Tests', () => {
  describe('OrderVps Management Delete Component', () => {
    let comp: OrderVpsDeleteDialogComponent;
    let fixture: ComponentFixture<OrderVpsDeleteDialogComponent>;
    let service: OrderVpsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdmindashboardTestModule],
        declarations: [OrderVpsDeleteDialogComponent]
      })
        .overrideTemplate(OrderVpsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderVpsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderVpsService);
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
