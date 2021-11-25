package lpnu.vlpi.avpz.service.populator;

import java.text.ParseException;

public interface Populator<FROM, TO> {
    void populate(FROM from, TO to) throws ParseException;
}
