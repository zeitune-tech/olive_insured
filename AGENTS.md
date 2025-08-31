# agents.md — `olive_insurance_insured_service`

## 🎯 Rôle de l’agent

Tu es un agent développeur Spring Boot expert, JPA/Hibernate, validation Bean, sécurité JWT, Caffeine, microservices (service discovery via **Consul**), PostgreSQL.
Ta mission : **concevoir et livrer** les modules **Insured (Assuré)** et **Risk (Véhicule)** conformément aux specs ci-dessous, avec interopérabilité stricte entre microservices.

---

## 🌍 Contexte & Objectif global

* Plateforme **Olive** en **microservices** ; objectif final : **générer un contrat d’assurance** et son **attestation**.
* Communication interservices via **WebClient** (Spring), **timeouts** et **caching** (Caffeine).

    * URLs statiques dans `application.yml` (utile en dev/local/test).
* Convention des endpoints interop : **`/interservices/...`** uniquement (ni `/app/**` ni `/admin/**` pour l’interop).

### Architecture applicative (rappel)

```
├── controllers/     # REST (USER/ADMIN), validation, mapping HTTP <-> DTO
├── services/        # Business logic (interfaces + impl), cache, orchestration interservices
├── repositories/    # Spring Data JPA
├── entities/        # JPA (Long id interne, UUID public)
├── dto/             # DTOs request/response
├── mappers/         # MapStruct (ou manual) entity <-> DTO
├── security/        # JWT (clés publiques user/admin), filtres, RBAC
└── exceptions/      # Exceptions métier + GlobalExceptionHandler
```

---

## ⚙️ Configuration d’exécution (dev)

* **Base**

    * Port app : **8040**
    * Contexte : **`/api/v1/`**
    * **Consul** : `localhost:8500`
* **DB**

    * PostgreSQL sur `localhost:5600`, database `olive_exploitation`, credentials `lhacksrt/lhacksrt`.
    * Hibernate DDL **create-drop** (dev) ;
* **Sécurité**

    * Clés publiques JWT dans `src/main/resources/keys/jwt/` (user/admin).
    * Clés QR codes dans `src/main/resources/keys/qrcode/`.

### application.yml (extrait, fallback si Consul indispo)

```yaml
server:
  port: 8040
  servlet:
    context-path: /api/v1/

spring:
  datasource:
    url: jdbc:postgresql://localhost:5600/olive_exploitation
    username: lhacksrt
    password: lhacksrt
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
        jdbc.lob.non_contextual_creation: true

services:
  olive-insurance-auth-service:
    base-url: http://localhost:8010
  olive-insurance-administration-service:
    base-url: http://localhost:8020
  olive-insurance-insured-service:
    base-url: http://localhost:8040/api/v1
  olive-insurance-pricing-service:
    base-url: http://localhost:8030
  olive-insurance-attestation-service:
    base-url: http://localhost:8050
  olive-insurance-settings-service:
    base-url: http://localhost:8060
  olive-insurance-exploitation-service:
    base-url: http://localhost:8070
```
---

## 📚 Règles de références (#ref / ##ref)

* **#ref** (dénormalisation) : on **valide** côté Settings/Administration puis on **copie le libellé** (pas de FK, pas d’UUID stocké).
* **##ref** (référence forte) : on **stocke l’UUID** du service propriétaire et on **résout à la demande** (endpoint enrichi + cache court).

---

## 🧩 Bloc A — Domaine **Insured (Assuré)**

### Modèle fonctionnel (champs)

* `civilite [enum]` (MR, MME, MLLE)
* `nom` *(req.)*, `prenom` *(req.)*, `dateNaissance` *(req.)*
* `typePiece [enum]`, `numeroPiece` *(unicité par type ?)*
* `adresse`, `ville #ref` *(copié)*
* `email` *(format email, unicité optionnelle par tenant)*
* `phoneFixe`, `phoneMobile` (E.164)
* `profession #ref`, `activite #ref`, `qualite #ref`
* `nomEntreprise`, `patente`, `registreDeCommerce`

### Endpoints publics (CRUD)

* `POST /app/insured`
* `GET /app/insured/{uuid}`
* `GET /app/insured?query=...&page=..&size=..`
* `PUT /app/insured/{uuid}`
* `PATCH /app/insured/{uuid}`
* `DELETE /app/insured/{uuid}` → **soft delete**

### Interservices consommés (pour #ref)

* `GET /interservices/settings/villes?query=...`
* `GET /interservices/settings/professions?query=...`
* `GET /interservices/settings/activites?query=...`
* `GET /interservices/settings/qualites?query=...`

### DTOs (exemples)

```java
public record InsuredCreateRequest(
  String civilite, @NotBlank String nom, @NotBlank String prenom, @NotNull LocalDate dateNaissance,
  String typePiece, String numeroPiece, String adresse,
  String villeRef, @Email String email, String phoneFixe, String phoneMobile,
  String professionRef, String activiteRef, String qualiteRef,
  String nomEntreprise, String patente, String registreDeCommerce
) {}

public record InsuredResponse(
  UUID uuid, String civilite, String nom, String prenom, LocalDate dateNaissance,
  String typePiece, String numeroPiece, String adresse, String ville,
  String email, String phoneFixe, String phoneMobile,
  String profession, String activite, String qualite,
  String nomEntreprise, String patente, String registreDeCommerce,
  Instant createdAt, Instant updatedAt
) {}
```

### Persistance & Identity

* **UUID Strategy** : Long `id` auto (DB) pour interne, **UUID** public pour exposition externe.
* Table `insured` : soft delete (`deleted`, `deletedAt`), timestamps.
* Index : `(nom, prenom)`, `email unique` (option), `numeroPiece+typePiece unique` (option).

---

## 🚗 Bloc B — Domaine **Risk (Véhicule)**

### Modèle fonctionnel

* `immatriculation` *(unique par tenant, req.)*
* `marque #ref`, `modele #ref` *(copiés)*
* `genre ##ref`, `usage ##ref`
* `dateMiseEnCirculation`, `energie [enum]`
* `numChassis`, `numMoteur`, `typeCarrosserie [enum]`
* `hasTurbo`, `hasRemorque`, `isEnflammable`
* `puissance`, `tonnage` *(par défaut **PTAC** en kg)*, `cylindre`, `nbPlace`
* `numAttestation ##ref`
* `valeurANeuve`, `valeurVenale`

### Endpoints publics

* `POST /app/risks`
* `GET /app/risks/{uuid}`
* `GET /app/risks?immatriculation=...`
* `PUT /app/risks/{uuid}` / `PATCH /app/risks/{uuid}`
* `DELETE /app/risks/{uuid}` → **soft delete**
* **Enrichi** : `GET /app/risks/{uuid}/view` (résout labels de `##ref`)

### Interservices requis

* **Settings** : `brands`, `models`, `genres/{uuid}`, `usages/{uuid}`
* **Attestation** : `attestations/{uuid}`

### DTOs (exemples)

```java
public record RiskCreateRequest(
  @NotBlank String immatriculation,
  String marqueRef, String modeleRef, // #ref entrants -> copier libellés
  UUID genreUuid, UUID usageUuid,     // ##ref
  LocalDate dateMiseEnCirculation, String energie, String numChassis, String numMoteur,
  String typeCarrosserie, Boolean hasTurbo, Boolean hasRemorque, Boolean isEnflammable,
  BigDecimal puissance, BigDecimal tonnage, BigDecimal cylindre, Integer nbPlace,
  UUID numAttestationUuid, BigDecimal valeurANeuve, BigDecimal valeurVenale
) {}

public record RiskResponse(
  UUID uuid, String immatriculation, String marque, String modele,
  UUID genreUuid, UUID usageUuid, LocalDate dateMiseEnCirculation, String energie,
  String numChassis, String numMoteur, String typeCarrosserie,
  Boolean hasTurbo, Boolean hasRemorque, Boolean isEnflammable,
  BigDecimal puissance, BigDecimal tonnage, BigDecimal cylindre, Integer nbPlace,
  UUID numAttestationUuid, BigDecimal valeurANeuve, BigDecimal valeurVenale,
  Instant createdAt, Instant updatedAt
) {}
```

### Persistance & Identity

* `risk` avec Long id interne, **UUID public**, soft delete, timestamps.
* Index : `immatriculation unique`, `genreUuid`, `usageUuid`, `numAttestationUuid`.
* `marque`, `modele` stockés en clair (varchar).

---

## 🔌 Adapters / WebClient / Discovery

* **Discovery** : Résoudre par `services.*.base-url`.
* **JWT** : propager `Authorization` (`Bearer ...`) via `ExchangeFilterFunction`.
* **onStatus** : convertir erreurs HTTP en exceptions métier (`RESOURCE_NOT_FOUND`, `REF_SERVICE_UNAVAILABLE`, `VALIDATION_FAILED`).
* **Cache** : Caffeine 5–15 min pour résolutions `##ref`.

**Clients à fournir**

* `SettingsClient`

    * `Optional<VilleDto> searchVilleByName(String q)`
    * `Optional<GenreDto> getGenre(UUID uuid)`
    * `Optional<UsageDto> getUsage(UUID uuid)`
    * `List<BrandDto> searchBrands(String q)`
    * `List<ModelDto> searchModels(String brand, String q)`
* `AttestationClient`

    * `Optional<AttestationDto> get(UUID uuid)`

---

## 🔐 Sécurité

* Valider les tokens JWT via **clés publiques** (user/admin) fournies.
* Ajouter **correlation-id** (MDC) pour logs distribués.

---

## 🛠️ Règles de validation

* `civilite`, `typePiece`, `energie`, `typeCarrosserie` → enums internes.
* `dateNaissance` ≤ today, âge ≥ 18 si requis.
* `immatriculation` : unique par tenant + pattern local (SN) si besoin.
* `nbPlace` > 0 ; `puissance/tonnage/cylindre` ≥ 0.
* `genreUuid`, `usageUuid`, `numAttestationUuid` doivent exister (404 ⇒ 422 métier).

---

## 🧪 DoD & Tests

* CRUD **Insured** et **Risk** complets + pagination.
* Convention **`/interservices/...`** respectée (aucun coupling aux endpoints publics).
* Endpoint **`/app/risks/{uuid}/view`** renvoie libellés résolus.
---

## ✅ Checklists

### Insured

* [ ] Entité + migrations (Long id, UUID public, soft delete)
* [ ] Enums `Civilite`, `TypePiece`
* [ ] DTOs create/update/response
* [ ] Service (résolution **#ref** et copie libellés)
* [ ] `SettingsClient` + cache + retries
* [ ] Controller CRUD + pagination
* [ ] Index/contraintes DB

### Risk

* [ ] Entité + migrations (immatriculation unique)
* [ ] Enums `Energie`, `TypeCarrosserie`
* [ ] DTOs create/update/response
* [ ] Service (#ref copie / ##ref conserve UUID)
* [ ] Clients (Settings/Attestation), cache + retries
* [ ] `/app/risks/{uuid}/view` enrichi

---

## 🧑‍💻 Prompts prêts à l’emploi

### 1) CRUD complet **Insured**

> Rôle: Expert Spring Boot (WebFlux/WebMVC), JPA, validation, JWT, Resilience4j, Caffeine, Consul.
> Tâche: Crée le module `Insured` (entité Long id + UUID public + soft delete, enums, DTOs, mapper, service avec résolution **#ref** via `SettingsClient`, controller CRUD + pagination, `SettingsClient`). Fournis **tout le code**.

### 2) CRUD complet **Risk (Véhicule)**

> Rôle: Expert Spring Boot + microservices.
> Tâche: Crée `Risk` (entité, enums, DTOs, services, contrôleurs). **#ref**: `marque`,`modele` (copie libellés validés). **##ref**: `genreUuid`,`usageUuid`,`numAttestationUuid` (stocker UUID, endpoint `/app/risks/{uuid}/view` qui résout via Settings/Attestation avec cache Caffeine 5–15 min)..

### 3) Clients **Settings** & **Attestation**

> Rôle: Expert WebClient + discovery.
> Tâche: Implémente `SettingsClient` & `AttestationClient` (interfaces + impls) avec `services.*.base-url`, `onStatus` → exceptions métier, cache Caffeine pour `##ref`.

---

## 🔍 Points ouverts (défauts proposés)

* **Tonnage** : **PTAC** (kg) par défaut (champ `tonnage`).
* **Unicités** : `numeroPiece+typePiece` (Insured), `immatriculation` par tenant (Risk).
* **Pagination** : standard Spring Data (page/size/sort).
* **Logs** : ajouter correlation-id et timing pour appels interservices.

---

## 🧩 Extraits techniques rapides

**Ville (#ref) → copie libellé**

```java
String resolveVilleLabel(String villeRef) {
  return settingsClient.searchVilleByName(villeRef)
    .map(VilleDto::libelle)
    .orElseThrow(() -> new IllegalArgumentException("Ville inconnue: " + villeRef));
}
```

**Genre (##ref) cache**

```java
@Cacheable(cacheNames = "genres", key = "#uuid")
public GenreDto getGenre(UUID uuid) {
  return settingsClient.getGenre(uuid)
    .orElseThrow(() -> new EntityNotFoundException("Genre introuvable: " + uuid));
}
```

**Endpoint enrichi Risk**

```java
@GetMapping("/app/risks/{uuid}/view")
public RiskViewResponse getView(@PathVariable UUID uuid) {
  var r = riskService.get(uuid);
  var genre = Optional.ofNullable(r.getGenreUuid()).map(refs::getGenre).orElse(null);
  var usage = Optional.ofNullable(r.getUsageUuid()).map(refs::getUsage).orElse(null);
  var att   = Optional.ofNullable(r.getNumAttestationUuid()).map(attClient::get).orElse(null);
  return RiskViewResponse.from(r, genre, usage, att);
}
```