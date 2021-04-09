package io.jmix.sampler.bean;

import io.jmix.sessions.events.JmixSessionCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.session.Session;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component("sampler_SessionManager")
public class SamplerSessionManager {

    @EventListener(JmixSessionCreatedEvent.class)
    protected void onSessionDestroyed(JmixSessionCreatedEvent<?> event) {
        Session session= event.getSession();
        session.setMaxInactiveInterval(Duration.ofMinutes(1));
    }
}
