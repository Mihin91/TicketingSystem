import { Component } from '@angular/core';
import { AppStateService } from '../../services/app-state.service';

@Component({
  selector: 'app-control-panel',
  template: `
    <button (click)="incrementTickets()">Add Ticket</button>
  `,
})
export class ControlPanelComponent {
  constructor(private appStateService: AppStateService) {}

  incrementTickets() {
    this.appStateService.updateTicketCount(1); // Increment ticket count
  }
}
