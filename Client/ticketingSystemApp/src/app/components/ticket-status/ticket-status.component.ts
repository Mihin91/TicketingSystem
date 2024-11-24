import { Component, OnInit } from '@angular/core';
import { AppStateService } from '../../services/app-state.service';

@Component({
  selector: 'app-ticket-status',
  template: `
    <h2>Ticket Status</h2>
    <p>Available Tickets: {{ ticketCount }}</p>
  `,
})
export class TicketStatusComponent implements OnInit {
  ticketCount: number = 0;

  constructor(private appStateService: AppStateService) {}

  ngOnInit() {
    this.appStateService.ticketCount$.subscribe((count) => {
      this.ticketCount += count;
    });
  }
}
