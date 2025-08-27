import { Component, inject, NgZone } from '@angular/core';
import {  FormsModule, NgModel, ReactiveFormsModule } from '@angular/forms';
import { TextareaModule } from 'primeng/textarea';
import { ButtonModule } from 'primeng/button';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputIcon } from 'primeng/inputicon';
import { IconField } from 'primeng/iconfield';
import { InputTextModule } from 'primeng/inputtext';
import { CardModule } from 'primeng/card';
import { ProgressSpinner } from 'primeng/progressspinner';


@Component({
  selector: 'app-ollama-view',
  standalone: true,
  imports: [ProgressSpinner,CardModule, InputTextModule, IconField, InputIcon, FormsModule, IconFieldModule, InputIconModule, TextareaModule, ReactiveFormsModule, ButtonModule, MessageModule, ToastModule, MessageModule],
  templateUrl: './ollama-view.component.html',
  styleUrl: './ollama-view.component.scss'

})
export class OllamaViewComponent {
  prompt: string = "";
  showResponse: boolean = false;
  messages: string[] = [];
  showSpinner:boolean=false;
  private eventSource?: EventSource;
  constructor(private ngZone: NgZone) {
  }

  searchPrompt() {
    if (this.eventSource) return; // already started
    this.showSpinner=true;
    this.messages = [];
    this.eventSource = new EventSource(`http://localhost:8080/message/${this.prompt}`, {
      withCredentials: false
    });


    this.eventSource.onmessage = (event) => {
      this.showSpinner=false;
      this.showResponse = true;
      this.ngZone.run(() => this.messages.push(event.data));
    };

    this.eventSource.onerror = (error) => {
      this.eventSource?.close(); // close connection on error
      this.eventSource = undefined; // optional: allow restarting
    };

  }

  ngOnDestroy() {
    this.eventSource?.close();
  }

}
