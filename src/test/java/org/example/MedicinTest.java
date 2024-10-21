package org.example;

import org.example.Medicin.Medicin;
import org.example.Medicin.Medicin.Version;
import org.junit.Test;
import static org.junit.Assert.*;

public class MedicinTest {

    @Test
    public void testMedicinBuilder() {
        Version version = new Version("Tablets", "Bayer", "123456789", "2022-01-01", "2027-01-01", "FDA", "Blister", 20, 100.0f, "100mg", "2 times a day");

        Medicin medicin = new Medicin.MedicinBuilder("001", "Aspirin")
                .withPharm("Bayer")
                .withGroup("Analgesics")
                .withAnalogs(new String[]{"Acetylsalicylic acid"})
                .withVersions(new Version[]{version})
                .build();

        assertEquals("001", medicin.getId());
        assertEquals("Aspirin", medicin.getName());
        assertEquals("Bayer", medicin.getPharm());
        assertEquals("Analgesics", medicin.getGroup());
        assertArrayEquals(new String[]{"Acetylsalicylic acid"}, medicin.getAnalogs());
        assertEquals(1, medicin.getVersions().length);
        assertEquals("Tablets", medicin.getVersions()[0].getForm());
        assertEquals("Bayer", medicin.getVersions()[0].getManufacturer());
        assertEquals("123456789", medicin.getVersions()[0].getCertificateNumber());
    }
}
