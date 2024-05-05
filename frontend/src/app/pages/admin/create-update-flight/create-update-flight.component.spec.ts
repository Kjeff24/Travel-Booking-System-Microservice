import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateUpdateFlightComponent } from './create-update-flight.component';

describe('CreateUpdateFlightComponent', () => {
  let component: CreateUpdateFlightComponent;
  let fixture: ComponentFixture<CreateUpdateFlightComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateUpdateFlightComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreateUpdateFlightComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
