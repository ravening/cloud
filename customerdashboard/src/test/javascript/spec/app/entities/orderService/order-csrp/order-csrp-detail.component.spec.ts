import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CustomerdashboardTestModule } from '../../../../test.module';
import { OrderCsrpDetailComponent } from 'app/entities/orderService/order-csrp/order-csrp-detail.component';
import { OrderCsrp } from 'app/shared/model/orderService/order-csrp.model';

describe('Component Tests', () => {
  describe('OrderCsrp Management Detail Component', () => {
    let comp: OrderCsrpDetailComponent;
    let fixture: ComponentFixture<OrderCsrpDetailComponent>;
    const route = ({ data: of({ orderCsrp: new OrderCsrp(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerdashboardTestModule],
        declarations: [OrderCsrpDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OrderCsrpDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderCsrpDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.orderCsrp).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
