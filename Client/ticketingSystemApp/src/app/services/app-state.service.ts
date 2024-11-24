import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AppStateService {
  private ticketCountSubject = new BehaviorSubject<number>(0); // Initialize with a default value
  ticketCount$ = this.ticketCountSubject.asObservable(); // Expose the observable for subscription

  updateTicketCount(count: number) {
    const currentValue = this.ticketCountSubject.value;
    this.ticketCountSubject.next(currentValue + count); // Update with the new value
  }
}
