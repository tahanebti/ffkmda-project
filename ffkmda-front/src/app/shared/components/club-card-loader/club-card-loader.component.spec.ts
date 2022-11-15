import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClubCardLoaderComponent } from './club-card-loader.component';

describe('ClubCardLoaderComponent', () => {
  let component: ClubCardLoaderComponent;
  let fixture: ComponentFixture<ClubCardLoaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClubCardLoaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClubCardLoaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
