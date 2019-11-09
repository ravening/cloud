import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AdmindashboardTestModule } from '../../../../test.module';
import { OrderCsrpUpdateComponent } from 'app/entities/orderService/order-csrp/order-csrp-update.component';
import { OrderCsrpService } from 'app/entities/orderService/order-csrp/order-csrp.service';
import { OrderCsrp } from 'app/shared/model/orderService/order-csrp.model';

describe('Component Tests', () => {
  describe('OrderCsrp Management Update Component', () => {
    let comp: OrderCsrpUpdateComponent;
    let fixture: ComponentFixture<OrderCsrpUpdateComponent>;
    let service: OrderCsrpService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AdmindashboardTestModule],
        declarations: [OrderCsrpUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OrderCsrpUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderCsrpUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderCsrpService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrderCsrp(123);
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
        const entity = new OrderCsrp();
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
