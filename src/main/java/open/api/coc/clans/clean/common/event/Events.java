package open.api.coc.clans.clean.common.event;

import java.util.Objects;
import org.springframework.context.ApplicationEventPublisher;

public class Events {

    private static ApplicationEventPublisher publisher;

    static void setPublisher(ApplicationEventPublisher publisher) {
        Events.publisher = publisher;
    }

    public static void raise(Object event) {
        if (Objects.isNull(publisher)) {return;}
        publisher.publishEvent(event);
    }

}