import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateUpdateCarRentalComponent } from './create-update-car-rental.component';

describe('CreateUpdateCarRentalComponent', () => {
  let component: CreateUpdateCarRentalComponent;
  let fixture: ComponentFixture<CreateUpdateCarRentalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateUpdateCarRentalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreateUpdateCarRentalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
