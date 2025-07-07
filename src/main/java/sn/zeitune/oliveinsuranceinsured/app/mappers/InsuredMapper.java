package sn.zeitune.oliveinsuranceinsured.app.mappers;

import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.InsuredRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.InsuredResponse;
import sn.zeitune.oliveinsuranceinsured.app.entities.Insured;

public class InsuredMapper {

    public static Insured map(InsuredRequest request) {
        return Insured.builder()
                .lastName(request.lastName())
                .firstName(request.firstName())
                .birthDate(request.birthDate())
                .gender(request.gender())
                .maritalStatus(request.maritalStatus())
                .address(request.address())
                .city(request.city())
                .postalCode(request.postalCode())
                .email(request.email())
                .phone(request.phone())
                .licenseNumber(request.licenseNumber())
                .licenseIssueDate(request.licenseIssueDate())
                .licenseCategory(request.licenseCategory())
                .registrationDate(request.registrationDate())
                .profession(request.profession())
                .employmentStatus(request.employmentStatus())
                .build();
    }

    public static InsuredResponse map(Insured insured) {
        return InsuredResponse.builder()
                .id(insured.getUuid())
                .lastName(insured.getLastName())
                .firstName(insured.getFirstName())
                .birthDate(insured.getBirthDate())
                .gender(insured.getGender())
                .maritalStatus(insured.getMaritalStatus())
                .address(insured.getAddress())
                .city(insured.getCity())
                .postalCode(insured.getPostalCode())
                .email(insured.getEmail())
                .phone(insured.getPhone())
                .licenseNumber(insured.getLicenseNumber())
                .licenseIssueDate(insured.getLicenseIssueDate())
                .licenseCategory(insured.getLicenseCategory())
                .registrationDate(insured.getRegistrationDate())
                .profession(insured.getProfession())
                .employmentStatus(insured.getEmploymentStatus())
                .build();
    }
}
