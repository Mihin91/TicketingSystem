import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-log-display',
  template: `
    <h3>Logs</h3>
    <ul>
      <li *ngFor="let log of logs">{{ log }}</li>
    </ul>
  `,
})
export class LogDisplayComponent {
  @Input() logs: string[] = [];
}
