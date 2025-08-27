import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OllamaViewComponent } from './ollama-view.component';

describe('OllamaViewComponent', () => {
  let component: OllamaViewComponent;
  let fixture: ComponentFixture<OllamaViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OllamaViewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OllamaViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
