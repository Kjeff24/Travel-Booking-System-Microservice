import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateUpdateHotelComponent } from './create-update-hotel.component';

describe('CreateUpdateHotelComponent', () => {
  let component: CreateUpdateHotelComponent;
  let fixture: ComponentFixture<CreateUpdateHotelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateUpdateHotelComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreateUpdateHotelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
