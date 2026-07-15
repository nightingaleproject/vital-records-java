package edu.gatech.chai.VRDR.model;

import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Extension;
import org.hl7.fhir.r4.model.IntegerType;
import org.hl7.fhir.r4.model.Parameters;
import org.hl7.fhir.r4.model.StringType;

import ca.uhn.fhir.model.api.annotation.ResourceDef;
import edu.gatech.chai.VRDR.model.util.CodingStatusValuesUtil;
import edu.gatech.chai.VRDR.model.util.CommonUtil;

@ResourceDef(name = "Parameters", profile = "http://hl7.org/fhir/us/vrdr/StructureDefinition/vrdr-coding-status-values")
public class CodingStatusValues extends Parameters {

	public CodingStatusValues() {
		super();
		CommonUtil.initResource(this);
	}

	public Parameters addShipmentNumber(String value) {
		return addParameter("shipmentNumber",value);
	}

	public Parameters addReceiptDate(String value) {
		return addParameter("receiptDate",value);
	}

	public Parameters addCoderStatus(String value) {
		return addParameter("coderStatus",value);
	}

	public Parameters addIntentionalReject(String value) {
		CodeableConcept ccValue = CommonUtil.findConceptFromCollectionUsingSimpleString(value, CodingStatusValuesUtil.intentionalRejectValueset);
		return addParameter("intentionalReject",ccValue);
	}

	public Parameters addAcmeSystemReject(String value) {
		CodeableConcept ccValue = CommonUtil.findConceptFromCollectionUsingSimpleString(value, CodingStatusValuesUtil.intentionalRejectValueset);
		return addParameter("acmeSystemReject",ccValue);
	}

	public Parameters addTransaxConversion(String value) {
		CodeableConcept ccValue = CommonUtil.findConceptFromCollectionUsingSimpleString(value, CodingStatusValuesUtil.intentionalRejectValueset);
		return addParameter("transaxConversion",ccValue);
	}

	public Parameters addParameter(String name, String value) {
		ParametersParameterComponent ppc = new ParametersParameterComponent();
		ppc.setName(name);
		ppc.setValue(new StringType(value));
		this.addParameter(ppc);
		return this;
	}

	public Parameters addParameter(String name, CodeableConcept value) {
		ParametersParameterComponent ppc = new ParametersParameterComponent();
		ppc.setName(name);
		ppc.setValue(value);
		this.addParameter(ppc);
		return this;
	}

	public Integer getCoderStatus() {
		return getParameter("coderStatus") == null ? null : ((IntegerType)getParameter("coderStatus").getValue()).getValue();
	}

	public String getShipmentNumber() {
		return getParameter("shipmentNumber") == null ? null : ((StringType)getParameter("shipmentNumber").getValue()).getValue();
	}

	public static final String PartialDateExtensionUrl = "http://hl7.org/fhir/us/vrdr/StructureDefinition/PartialDate";
	public static final String DateYearExtensionUrl = "http://hl7.org/fhir/us/vrdr/StructureDefinition/Date-Year";
	public static final String DateMonthExtensionUrl = "http://hl7.org/fhir/us/vrdr/StructureDefinition/Date-Month";
	public static final String DateDayExtensionUrl = "http://hl7.org/fhir/us/vrdr/StructureDefinition/Date-Day";

	public Integer getReceiptDatePart(String datePartExtensionUrl) {
		if (getParameter("receiptDate") == null) {
			return null;
		}
		ParametersParameterComponent receiptDateParam = getParameter("receiptDate");
		
		// Extensions on date values are stored in the element itself (_valueDate field in JSON)
		// In the FHIR model, this is accessed via the value's extension list
		Extension partialDate = null;
		
		// First, try to get extensions from the parameter itself
		partialDate = receiptDateParam.getExtensionByUrl(PartialDateExtensionUrl);
		
		// If not found, try to get from the value element (for _valueDate case)
		if (partialDate == null && receiptDateParam.getValue() != null) {
			// Check if the value has extensions
			if (receiptDateParam.getValue() instanceof org.hl7.fhir.r4.model.Type) {
				org.hl7.fhir.r4.model.Type typeValue = (org.hl7.fhir.r4.model.Type) receiptDateParam.getValue();
				partialDate = typeValue.getExtensionByUrl(PartialDateExtensionUrl);
			}
		}
		
		if (partialDate == null) {
			return null;
		}
		Extension datePart = partialDate.getExtensionByUrl(datePartExtensionUrl);
		if (datePart == null || datePart.getValue() == null) {
			return null;
		}
		
		// Handle both IntegerType and UnsignedIntType
		if (datePart.getValue() instanceof IntegerType) {
			return ((IntegerType)datePart.getValue()).getValue();
		} else if (datePart.getValue() instanceof org.hl7.fhir.r4.model.UnsignedIntType) {
			return ((org.hl7.fhir.r4.model.UnsignedIntType)datePart.getValue()).getValue();
		}
		return null;
	}

	public Integer getReceiptYear() {
		return getReceiptDatePart(DateYearExtensionUrl);
	}

	public Integer getReceiptMonth() {
		return getReceiptDatePart(DateMonthExtensionUrl);
	}

	public Integer getReceiptDay() {
		return getReceiptDatePart(DateDayExtensionUrl);
	}

}