import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { OllamaViewComponent } from './ollama-view/ollama-view.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.scss',
  imports: [OllamaViewComponent,RouterOutlet]
})
export class App {
  protected readonly title = signal('llm-gen-ai-ollama');
}
