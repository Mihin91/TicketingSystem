import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <app-configuration-form
      (configurationSubmitted)="handleConfiguration($event)"
    ></app-configuration-form>
    <p>Total Tickets Configured: {{ totalTickets }}</p>
  `,
})
export class AppComponent {
  totalTickets: number = 0;

  handleConfiguration(totalTickets: number) {
    console.log('Received Total Tickets:', totalTickets); // Debug log
    this.totalTickets = totalTickets; // Update the value
  }
}
