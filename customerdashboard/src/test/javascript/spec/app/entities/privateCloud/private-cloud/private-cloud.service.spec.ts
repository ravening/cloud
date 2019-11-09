import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { PrivateCloudService } from 'app/entities/privateCloud/private-cloud/private-cloud.service';
import { IPrivateCloud, PrivateCloud } from 'app/shared/model/privateCloud/private-cloud.model';

describe('Service Tests', () => {
  describe('PrivateCloud Service', () => {
    let injector: TestBed;
    let service: PrivateCloudService;
    let httpMock: HttpTestingController;
    let elemDefault: IPrivateCloud;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PrivateCloudService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PrivateCloud(0, 'AAAAAAA', 0, 'AAAAAAA', 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a PrivateCloud', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new PrivateCloud(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a PrivateCloud', () => {
        const returnedFromService = Object.assign(
          {
            domainName: 'BBBBBB',
            accountid: 1,
            accountName: 'BBBBBB',
            cpu: 1,
            ram: 1,
            storage: 1,
            traffic: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of PrivateCloud', () => {
        const returnedFromService = Object.assign(
          {
            domainName: 'BBBBBB',
            accountid: 1,
            accountName: 'BBBBBB',
            cpu: 1,
            ram: 1,
            storage: 1,
            traffic: 1
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PrivateCloud', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
