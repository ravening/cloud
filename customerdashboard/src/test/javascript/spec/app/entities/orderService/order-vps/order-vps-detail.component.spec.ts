// import { ComponentFixture, TestBed } from '@angular/core/testing';
// import { ActivatedRoute } from '@angular/router';
// import { of } from 'rxjs';

// import { CustomerdashboardTestModule } from '../../../../test.module';
// import { OrderVpsDetailComponent } from 'app/entities/orderService/order-vps/order-vps-detail.component';
// import { OrderVps } from 'app/shared/model/orderService/order-vps.model';

// describe('Component Tests', () => {
//   describe('OrderVps Management Detail Component', () => {
//     let comp: OrderVpsDetailComponent;
//     let fixture: ComponentFixture<OrderVpsDetailComponent>;
//     const route = ({ data: of({ orderVps: new OrderVps(123) }) } as any) as ActivatedRoute;

//     beforeEach(() => {
//       TestBed.configureTestingModule({
//         imports: [CustomerdashboardTestModule],
//         declarations: [OrderVpsDetailComponent],
//         providers: [{ provide: ActivatedRoute, useValue: route }]
//       })
//         .overrideTemplate(OrderVpsDetailComponent, '')
//         .compileComponents();
//       fixture = TestBed.createComponent(OrderVpsDetailComponent);
//       comp = fixture.componentInstance;
//     });

//     describe('OnInit', () => {
//       it('Should call load all on init', () => {
//         // GIVEN

//         // WHEN
//         comp.ngOnInit();

//         // THEN
//         expect(comp.orderVps).toEqual(jasmine.objectContaining({ id: 123 }));
//       });
//     });
//   });
// });
