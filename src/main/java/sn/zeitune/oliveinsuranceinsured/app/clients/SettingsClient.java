package sn.zeitune.oliveinsuranceinsured.app.clients;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SettingsClient {
    Optional<VilleDto> searchVilleByName(String q);
    Optional<GenreDto> getGenre(UUID uuid);
    Optional<UsageDto> getUsage(UUID uuid);
    Optional<VehicleTypeDto> getVehicleType(UUID uuid);
    List<BrandDto> searchBrands(String q);
    List<ModelDto> searchModels(String brand, String q);

    record VilleDto(String libelle) {}
    record GenreDto(UUID uuid, String code, String libelle) {}
    record UsageDto(UUID uuid, String code, String libelle) {}
    record VehicleTypeDto(UUID uuid, String code, String libelle) {}
    record BrandDto(String code, String libelle) {}
    record ModelDto(String code, String libelle) {}
}

