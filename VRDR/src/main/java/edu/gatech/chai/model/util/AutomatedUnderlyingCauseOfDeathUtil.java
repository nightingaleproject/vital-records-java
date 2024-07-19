package edu.gatech.chai.model.util;

import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;

public class AutomatedUnderlyingCauseOfDeathUtil {
	public static final CodeableConcept code = new CodeableConcept()
			.addCoding(new Coding(CommonUtil.loincSystemUrl, "80358-5", "Cause of death.underlying [Automated]"));
}
