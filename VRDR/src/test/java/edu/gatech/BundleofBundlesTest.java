package edu.gatech;

import edu.gatech.chai.model.util.BuildDCD;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;

import edu.gatech.chai.model.DeathCertificateDocument;

public class BundleofBundlesTest {
	public void testBundleOfBundles() {
		Bundle outerBundle = new Bundle();
		DeathCertificateDocument deathRecordDocument = BuildDCD.buildExampleDeathCertificateDocument();
		outerBundle.addEntry(new BundleEntryComponent().setResource(deathRecordDocument));
	}
}