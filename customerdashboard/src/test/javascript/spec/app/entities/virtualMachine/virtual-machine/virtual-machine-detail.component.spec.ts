import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CustomerdashboardTestModule } from '../../../../test.module';
import { VirtualMachineDetailComponent } from 'app/entities/virtualMachine/virtual-machine/virtual-machine-detail.component';
import { VirtualMachine } from 'app/shared/model/virtualMachine/virtual-machine.model';

describe('Component Tests', () => {
  describe('VirtualMachine Management Detail Component', () => {
    let comp: VirtualMachineDetailComponent;
    let fixture: ComponentFixture<VirtualMachineDetailComponent>;
    const route = ({ data: of({ virtualMachine: new VirtualMachine(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerdashboardTestModule],
        declarations: [VirtualMachineDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VirtualMachineDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VirtualMachineDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.virtualMachine).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
