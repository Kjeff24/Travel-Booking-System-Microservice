import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MiniAboutComponent } from './mini-about.component';

describe('MiniAboutComponent', () => {
  let component: MiniAboutComponent;
  let fixture: ComponentFixture<MiniAboutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MiniAboutComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MiniAboutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
