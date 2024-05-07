import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StoreMainPageComponent } from './store-main-page.component';

describe('StoreMainPageComponent', () => {
  let component: StoreMainPageComponent;
  let fixture: ComponentFixture<StoreMainPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StoreMainPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StoreMainPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
