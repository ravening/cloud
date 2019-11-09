import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AdmindashboardTestModule } from '../../../../test.module';
import { OrderCsrpDeleteDialogComponent } from 'app/entities/orderService/order-csrp/order-csrp-delete-dialog.component';
import { OrderCsrpService } from 'app/entities/orderService/order-csrp/order-csrp.service';

describe('Component Tests', () => {
  describe('OrderCsrp Management Delete Component', () => {
    let comp: OrderCsrpDeleteDialogComponent;
    let fixture: ComponentFixture<OrderCsrpDeleteDialogComponent>;
    let service: OrderCsrpService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdmindashboardTestModule],
        declarations: [OrderCsrpDeleteDialogComponent]
      })
        .overrideTemplate(OrderCsrpDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderCsrpDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderCsrpService);
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
