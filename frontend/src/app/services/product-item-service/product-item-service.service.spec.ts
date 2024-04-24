import { TestBed } from '@angular/core/testing';

import { ProductItemService } from './product-item-service.service';

describe('ProductItemService', () => {
  let service: ProductItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductItemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
