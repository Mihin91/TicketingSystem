// src/main/java/lk/ac/iit/Mihin/Server/Events/TicketsSoldOutEvent.java
package lk.ac.iit.Mihin.Server.Events;

import org.springframework.context.ApplicationEvent;

public class TicketsSoldOutEvent extends ApplicationEvent {

    public TicketsSoldOutEvent(Object source) {
        super(source);
    }
}