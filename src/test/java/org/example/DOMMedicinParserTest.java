package org.example;

import org.example.Medicin.Medicin;
import org.example.Parsers.DOMMedicinParser;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class DOMMedicinParserTest {

    @Test
    public void testParseValidXML() {
        DOMMedicinParser parser = new DOMMedicinParser();
        List<Medicin> medicins = parser.parse("src/main/resources/test_medicins.xml");

        assertNotNull(medicins);
        assertFalse(medicins.isEmpty());
        assertEquals(2, medicins.size());
        assertEquals("001", medicins.get(0).getId());
        assertEquals("Aspirin", medicins.get(0).getName());
        assertEquals("Bayer", medicins.get(0).getPharm());
    }
}
