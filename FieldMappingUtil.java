package com.bcbsfl.pharmacy.etl.load.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bcbsfl.pharmacy.etl.load.entity.PrimePriorAuths;

public class FieldMappingUtil {

    private static final Map<String, String> columnToFieldMap = new HashMap<>();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 

    static {
        columnToFieldMap.put("pareferenceid", "pareferenceid");
        columnToFieldMap.put("benefitscoordination_pbmmemberid", "hccId");
        columnToFieldMap.put("patient_name_lastname", "patientNameLastname");
        columnToFieldMap.put("patient_name_firstname", "patientNameFirstname");
        columnToFieldMap.put("patient_gender", "patientGender");
        columnToFieldMap.put("patient_dateofbirth_date", "patientDateOfBirth");
        columnToFieldMap.put("patient_address_addressline1", "patientAddressLine1");
        columnToFieldMap.put("patient_address_city", "patientAddressCity");
        columnToFieldMap.put("patient_address_stateprovince", "patientAddressStateProvince");
        columnToFieldMap.put("patient_address_postalcode", "patientAddressPostalCode");
        columnToFieldMap.put("patient_address_countrycode", "patientAddressCountryCode");
        columnToFieldMap.put("pharmacy_identification_ncpdpid", "pharmacyIdentificationNcpdpId");
        columnToFieldMap.put("pharmacy_identification_npi", "pharmacyIdentificationNpi");
        columnToFieldMap.put("pharmacy_pharmacist_lastname", "pharmacyPharmacistLastname");
        columnToFieldMap.put("pharmacy_pharmacist_firstname", "pharmacyPharmacistFirstname");
        columnToFieldMap.put("pharmacy_businessname", "pharmacyBusinessName");
        columnToFieldMap.put("pharmacy_address_addressline1", "pharmacyAddressLine1");
        columnToFieldMap.put("pharmacy_address_city", "pharmacyAddressCity");
        columnToFieldMap.put("pharmacy_address_stateprovince", "pharmacyAddressStateProvince");
        columnToFieldMap.put("pharmacy_address_postalcode", "pharmacyAddressPostalCode");
        columnToFieldMap.put("pharmacy_address_countrycode", "pharmacyAddressCountryCode");
        columnToFieldMap.put("prescriber_identification_npi", "prescriberIdentificationNpi");
        columnToFieldMap.put("prescriber_name_lastname", "prescriberNameLastname");
        columnToFieldMap.put("prescriber_name_firstname", "prescriberNameFirstname");
        columnToFieldMap.put("prescriber_address_addressline1", "prescriberAddressLine1");
        columnToFieldMap.put("prescriber_address_city", "prescriberAddressCity");
        columnToFieldMap.put("prescriber_address_stateprovince", "prescriberAddressStateProvince");
        columnToFieldMap.put("prescriber_address_postalcode", "prescriberAddressPostalCode");
        columnToFieldMap.put("prescriber_address_countrycode", "prescriberAddressCountryCode");
        columnToFieldMap.put("prescriber_communicationnumbers_primarytelephone_number", "prescriberCommunicationNumbersPrimaryTelephone");
        columnToFieldMap.put("prescriber_communicationnumbers_fax_number", "prescriberCommunicationNumbersFax");
        columnToFieldMap.put("medicationprescribed_drugdescription", "medicationPrescribedDrugDescription");
        columnToFieldMap.put("medicationprescribed_drugcoded_productcode_code", "medicationPrescribedDrugCodedProductCode");
        columnToFieldMap.put("medicationprescribed_drugcoded_productcode_qualifier", "medicationPrescribedDrugCodedProductCodeQualifier");
        columnToFieldMap.put("medicationprescribed_quantity_value", "medicationPrescribedQuantityValue");
        columnToFieldMap.put("medicationprescribed_quantity_codelistqualifier", "medicationPrescribedQuantityCodeListQualifier");
        columnToFieldMap.put("medicationprescribed_quantity_quantityunitofmeasure_code", "medicationPrescribedQuantityUnitOfMeasureCode");
        columnToFieldMap.put("medicationprescribed_sig_sigtext", "medicationPrescribedSigSigText");
        columnToFieldMap.put("medicationprescribed_sig_codesystem_snomedversion", "medicationPrescribedSigCodeSystemSnomedVersion");
        columnToFieldMap.put("medicationprescribed_sig_codesystem_fmtversion", "medicationPrescribedSigCodeSystemFmtVersion");
        columnToFieldMap.put("medicationprescribed_sig_ins_da_dosedeliverymethod_text", "medicationPrescribedSigInsDaDoseDeliveryMethodText");
        columnToFieldMap.put("medicationprescribed_sig_ins_da_dosedeliverymethod_qualifier", "medicationPrescribedSigInsDaDoseDeliveryMethodQualifier");
        columnToFieldMap.put("medicationprescribed_sig_ins_da_dosedeliverymethod_code", "medicationPrescribedSigInsDaDoseDeliveryMethodCode");
        columnToFieldMap.put("medicationprescribed_sig_ins_da_doseamount_text", "medicationPrescribedSigInsDaDoseAmountText");
        columnToFieldMap.put("medicationprescribed_sig_ins_da_doseamount_qualifier", "medicationPrescribedSigInsDaDoseAmountQualifier");
        columnToFieldMap.put("medicationprescribed_sig_ins_da_doseamount_code", "medicationPrescribedSigInsDaDoseAmountCode");
        columnToFieldMap.put("medicationprescribed_sig_ins_da_doseclarifyingfreetext", "medicationPrescribedSigInsDaDoseClarifyingFreeText");
        columnToFieldMap.put("medicationprescribed_sig_ins_da_doseform_text", "medicationPrescribedSigInsDaDoseFormText");
        columnToFieldMap.put("medicationprescribed_sig_ins_da_doseform_qualifier", "medicationPrescribedSigInsDaDoseFormQualifier");
        columnToFieldMap.put("medicationprescribed_sig_ins_da_doseform_code", "medicationPrescribedSigInsDaDoseFormCode");
        columnToFieldMap.put("medicationprescribed_sig_ins_da_routeofadministration_text", "medicationPrescribedSigInsDaRouteOfAdministrationText");
        columnToFieldMap.put("medicationprescribed_sig_ins_da_routeofadministration_qualifier", "medicationPrescribedSigInsDaRouteOfAdministrationQualifier");
        columnToFieldMap.put("medicationprescribed_sig_ins_da_routeofadministration_code", "medicationPrescribedSigInsDaRouteOfAdministrationCode");
        columnToFieldMap.put("medicationprescribed_sig_ins_da_siteofadministration_text", "medicationPrescribedSigInsDaSiteOfAdministrationText");
        columnToFieldMap.put("medicationprescribed_sig_ins_da_siteofadministration_qualifier", "medicationPrescribedSigInsDaSiteOfAdministrationQualifier");
        columnToFieldMap.put("medicationprescribed_sig_ins_da_siteofadministration_code", "medicationPrescribedSigInsDaSiteOfAdministrationCode");
        columnToFieldMap.put("medicationprescribed_sig_ins_td_frequency_frequencynumericvalue", "medicationPrescribedSigInsTdFrequencyFrequencyNumericValue");
        columnToFieldMap.put("medicationprescribed_sig_ins_td_frequency_frequencyunits_text", "medicationPrescribedSigInsTdFrequencyFrequencyUnitsText");
        columnToFieldMap.put("medicationprescribed_sig_ins_td_frequency_frequencyunits_qualifier", "medicationPrescribedSigInsTdFrequencyFrequencyUnitsQualifier");
        columnToFieldMap.put("medicationprescribed_sig_ins_td_frequency_frequencyunits_code", "medicationPrescribedSigInsTdFrequencyFrequencyUnitsCode");
        columnToFieldMap.put("medicationprescribed_sig_ins_td_timingclarifyingfreetext", "medicationPrescribedSigInsTdTimingClarifyingFreeText");
        columnToFieldMap.put("provider_name_lastname", "providerNameLastname");
        columnToFieldMap.put("provider_name_firstname", "providerNameFirstname");
        columnToFieldMap.put("provider_address_addressline1", "providerAddressAddressLine1");
        columnToFieldMap.put("provider_address_city", "providerAddressCity");
        columnToFieldMap.put("provider_address_stateprovince", "providerAddressStateProvince");
        columnToFieldMap.put("provider_address_postalcode", "providerAddressPostalCode");
        columnToFieldMap.put("provider_address_countrycode", "providerAddressCountryCode");
        columnToFieldMap.put("response_responsestatus_denied_pacaseid", "responseResponseStatusDeniedPaCaseId");
        columnToFieldMap.put("response_responsestatus_denied_appeal_iseappealsupported", "responseResponseStatusDeniedAppealIsAppealSupported");
        columnToFieldMap.put("response_responsestatus_denied_appeal_panote", "responseResponseStatusDeniedAppealPaNote");
        columnToFieldMap.put("paadditionaldata_benefittype", "paadditionaldataBenefittype");
        columnToFieldMap.put("paadditionaldata_procedurecode", "paadditionaldataProcedurecode");
        columnToFieldMap.put("paadditionaldata_proceduretypecode", "paadditionaldataProceduretypecode");
        columnToFieldMap.put("paadditionaldata_pasystemrequestreceiveddatetime_date", "paadditionaldataPasystemrequestreceiveddatetimeDate");
        columnToFieldMap.put("paadditionaldata_finaloutcomedatetime_date", "paadditionaldataFinaloutcomedatetimeDate");
        columnToFieldMap.put("paadditionaldata_parequeststatus", "paadditionaldataParequeststatus");
        columnToFieldMap.put("paadditionaldata_additionaldrugcode_code", "paadditionaldataAdditionaldrugcodeCode");
        columnToFieldMap.put("paadditionaldata_additionaldrugcode", "paadditionaldataAdditionaldrugcode");
        columnToFieldMap.put("paadditionaldata_casetypecode", "paadditionaldataCasetypecode");
        columnToFieldMap.put("paadditionaldata_requesteddrugquantity", "paadditionaldataRequesteddrugquantity");
        columnToFieldMap.put("paadditionaldata_requesteddrugunitofmeasure", "paadditionaldataRequesteddrugunitofmeasure");
        columnToFieldMap.put("paadditionaldata_plancode", "paadditionaldataPlancode");
        columnToFieldMap.put("paadditionaldata_patientaddressline2", "paadditionaldataPatientaddressline2");
        columnToFieldMap.put("paadditionaldata_patientphonenumber", "paadditionaldataPatientphonenumber");
        columnToFieldMap.put("paadditionaldata_caseoutcomecode", "paadditionaldataCaseoutcomecode");
        columnToFieldMap.put("paadditionaldata_casestatuscode", "paadditionaldataCasestatuscode");
        columnToFieldMap.put("paadditionaldata_casestatusdesc", "paadditionaldataCasestatusdesc");
        columnToFieldMap.put("paadditionaldata_genericdrugflag", "paadditionaldataGenericdrugflag");
        columnToFieldMap.put("paadditionaldata_drugstrength", "paadditionaldataDrugstrength");
        columnToFieldMap.put("paadditionaldata_diagnosisdescription", "paadditionaldataDiagnosisdescription");
        columnToFieldMap.put("paadditionaldata_provideraddressline2", "paadditionaldataProvideraddressline2");
        columnToFieldMap.put("paadditionaldata_tollstartdate", "paadditionaldataTollstartdate");
        columnToFieldMap.put("paadditionaldata_tollenddate", "paadditionaldataTollenddate");
        columnToFieldMap.put("paadditionaldata_receiveddatesupportingstatement", "paadditionaldataReceiveddatesupportingstatement");
        columnToFieldMap.put("paadditionaldata_receiveddatepaymentstatement", "paadditionaldataReceiveddatepaymentstatement");
        columnToFieldMap.put("paadditionaldata_contractid", "paadditionaldataContractid");
        columnToFieldMap.put("paadditionaldata_requestclassificationcode", "paadditionaldataRequestclassificationcode");
        columnToFieldMap.put("paadditionaldata_requesttype", "paadditionaldataRequesttype");
        columnToFieldMap.put("paadditionaldata_physicianreviewdatetime", "paadditionaldataPhysicianreviewdatetime");
        columnToFieldMap.put("paadditionaldata_datefilesenttoire", "paadditionaldataDatefilesenttoire");
        columnToFieldMap.put("paadditionaldata_targetduedate", "paadditionaldataTargetduedate");
        columnToFieldMap.put("paadditionaldata_deniedduetolackofsuppinfo", "paadditionaldataDeniedduetolackofsuppinfo");
        columnToFieldMap.put("paadditionaldata_addressline2", "paadditionaldataAddressline2");
        columnToFieldMap.put("paadditionaldata_denyforlackofmedicalnecessity", "paadditionaldataDenyforlackofmedicalnecessity");
        columnToFieldMap.put("paadditionaldata_casetype", "paadditionaldataCasetype");
        columnToFieldMap.put("paadditionaldata_hashospice", "paadditionaldataHashospice");
        columnToFieldMap.put("created_date_time", "createdDateTime");
        columnToFieldMap.put("last_update_date_time", "lastUpdateDateTime");


        

    }

    
    





    public static String getFieldNameForColumn(String columnName) {
        return columnToFieldMap.get(columnName);
    }

    public static void setProperty(PrimePriorAuths primePriorAuths, String columnName, String value) {
        String fieldName = columnToFieldMap.get(columnName);
        if (fieldName != null) {
            try {
                Field field = PrimePriorAuths.class.getDeclaredField(fieldName);
                field.setAccessible(true);

                // 1 - custom handler for special fields . 
                // 2 - generic type conversion . 
                
                if ("customfield".equals(fieldName)) {
                    // Custom processing for someSpecialField
                    // Example: field.set(primePriorAuths, customProcess(value));
                } else if (field.getType().equals(Date.class)) {
                    try {
                    	// lets say you wanted to add a generic date formatter . 
						Date dateValue = dateFormat.parse(value);
                        field.set(primePriorAuths, dateValue);
                        
                    } catch (ParseException e) {
                        System.out.println("Error parsing date for column: " + columnName + " with value: " + value);
                    }
                } else if (field.getType().equals(String.class)) {
                    field.set(primePriorAuths, value);
                }
                // Add more else if blocks for other data types as needed
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.out.println("Error setting property for column: " + columnName + " with value: " + value);
            }
        }
    }


    private static String customProcess(String value) {
    	String processedValue = "" ;  
        return processedValue;
    }    
}
