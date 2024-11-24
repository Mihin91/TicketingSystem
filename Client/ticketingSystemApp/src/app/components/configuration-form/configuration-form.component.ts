import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-configuration-form', // Matches the tag in the AppComponent template
  template: `
    <form (ngSubmit)="submitForm()">
      <label for="totalTickets">Total Tickets:</label>
      <input
        id="totalTickets"
        [(ngModel)]="totalTickets"
        name="totalTickets"
        type="number"
        required
      />
      <button type="submit">Submit</button>
    </form>
  `,
})
export class ConfigurationFormComponent {
  @Output() configurationSubmitted = new EventEmitter<number>();
  totalTickets: number = 0;

  submitForm() {
    console.log('Submitting:', this.totalTickets); // Debug log
    this.configurationSubmitted.emit(this.totalTickets); // Emit as number
  }  
}
