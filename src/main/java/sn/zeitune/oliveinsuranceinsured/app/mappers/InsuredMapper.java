package sn.zeitune.oliveinsuranceinsured.app.mappers;

import org.springframework.stereotype.Component;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.InsuredCreateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.requests.InsuredUpdateRequest;
import sn.zeitune.oliveinsuranceinsured.app.dtos.responses.InsuredResponse;
import sn.zeitune.oliveinsuranceinsured.app.entities.Insured;

@Component
public class InsuredMapper {

    /**
     * Converts InsuredCreateRequest to Insured entity using builder pattern
     */
    public Insured toEntity(InsuredCreateRequest request) {
        if (request == null) {
            return null;
        }

        return Insured.builder()
                .civility(request.civility())
                .lastName(request.lastName())
                .firstName(request.firstName())
                .birthDate(request.birthDate())
                .idDocumentType(request.idDocumentType())
                .idDocumentNumber(request.idDocumentNumber())
                .address(request.address())
                .city(request.city())
                .profession(request.profession())
                .activity(request.activity())
                .email(request.email())
                .landlinePhone(request.landlinePhone())
                .mobilePhone(request.mobilePhone())
                .companyName(request.companyName())
                .businessLicense(request.businessLicense())
                .tradeRegister(request.tradeRegister())
                .build();
    }

    /**
     * Converts Insured entity to InsuredResponse using builder pattern
     */
    public InsuredResponse toResponse(Insured entity) {
        if (entity == null) {
            return null;
        }

        return new InsuredResponse(
                entity.getId(),
                entity.getUuid(),
                entity.getCivility(),
                entity.getLastName(),
                entity.getFirstName(),
                entity.getBirthDate(),
                entity.getIdDocumentType(),
                entity.getIdDocumentNumber(),
                entity.getAddress(),
                entity.getCity(),
                entity.getProfession(),
                entity.getActivity(),
                entity.getEmail(),
                entity.getLandlinePhone(),
                entity.getMobilePhone(),
                entity.getCompanyName(),
                entity.getBusinessLicense(),
                entity.getTradeRegister(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Updates existing Insured entity with data from InsuredUpdateRequest
     * Only updates non-null fields from the request
     */
    public void updateEntity(Insured entity, InsuredUpdateRequest request) {
        if (entity == null || request == null) {
            return;
        }

        if (request.civility() != null) {
            entity.setCivility(request.civility());
        }
        if (request.lastName() != null) {
            entity.setLastName(request.lastName());
        }
        if (request.firstName() != null) {
            entity.setFirstName(request.firstName());
        }
        if (request.birthDate() != null) {
            entity.setBirthDate(request.birthDate());
        }
        if (request.idDocumentType() != null) {
            entity.setIdDocumentType(request.idDocumentType());
        }
        if (request.idDocumentNumber() != null) {
            entity.setIdDocumentNumber(request.idDocumentNumber());
        }
        if (request.address() != null) {
            entity.setAddress(request.address());
        }
        if (request.city() != null) {
            entity.setCity(request.city());
        }
        if (request.profession() != null) {
            entity.setProfession(request.profession());
        }
        if (request.activity() != null) {
            entity.setActivity(request.activity());
        }
        if (request.email() != null) {
            entity.setEmail(request.email());
        }
        if (request.landlinePhone() != null) {
            entity.setLandlinePhone(request.landlinePhone());
        }
        if (request.mobilePhone() != null) {
            entity.setMobilePhone(request.mobilePhone());
        }
        if (request.companyName() != null) {
            entity.setCompanyName(request.companyName());
        }
        if (request.businessLicense() != null) {
            entity.setBusinessLicense(request.businessLicense());
        }
        if (request.tradeRegister() != null) {
            entity.setTradeRegister(request.tradeRegister());
        }
    }
}