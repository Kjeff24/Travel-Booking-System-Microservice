import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateUpdateAccommodationComponent } from './create-update-accommodation.component';

describe('CreateUpdateAccommodationComponent', () => {
  let component: CreateUpdateAccommodationComponent;
  let fixture: ComponentFixture<CreateUpdateAccommodationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateUpdateAccommodationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreateUpdateAccommodationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
