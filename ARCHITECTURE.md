# ARCHITECTURE.md

## Overview

A Spring Boot web app serving a catalog of Jmix Flow UI samples. Two real routes — `MainView` (menu/home shell) and `SampleView` (`/sample/:sampleId`) — host dynamically-instantiated sample views together with auto-generated source and description tabs.

## Tech stack

Major line only; exact pins live in `build.gradle` / `settings.gradle` / `gradle.properties`.

- **Java 21** — required by Spring Boot 4 + Vaadin 25.
- **Jmix 3.0** (`io.jmix` Gradle plugin + BOM), on **Spring Boot 4** and **Vaadin Flow 25**.
- Persistence: EclipseLink (`jmix-eclipselink-starter`) + **HSQLDB** (in-memory) + Liquibase.
- Premium Jmix add-ons: charts, maps, kanban, fullcalendar, pivottable, groupgrid (need premium-repo creds: `-PpremiumRepoUser`/`-PpremiumRepoPass` or `CUBA_PREMIUM_USER`/`CUBA_PREMIUM_PASSWORD`).
- Libraries: commonmark + jsoup (description rendering), so-charts, vcf-input-mask.
- Frontend: Vite + TypeScript; custom web components under `src/main/frontend/src/`.

## Build

- Gradle plugins: `io.jmix`, `org.springframework.boot`, `com.vaadin`, `war`.
- `processResources` copies all Java sources into resources (`from sourceSets.main.allJava`) so source files can be read at runtime and shown in the sample source tabs.
- The Vaadin frontend bundle is built during `build`; `hilla`/`copilot` are excluded.

## Runtime

- Entry: `UiSamplesApplication` + `UiSamplesConfiguration` (security, datasource beans, scheduling).
- Security: anonymous access (`CoreSecurityConfiguration`, `@AnonymousAllowed` views).
- **Per-session in-memory DB:** `UiSamplesRoutingDataSource` (the `@Primary` `DataSource`) routes each session to its own HSQLDB (`jdbc:hsqldb:mem:<sessionId>`), Liquibase-initialized on first use and reaped on session expiry — so data-mutating samples are isolated per visitor. Demo data is seeded by the `*DataGenerator` beans in `bean/`; file storage via `SamplesFileStorage`.
- Config: `application.properties` — context path `/ui-samples`, `jmix.ui.main-view-id = MainView`, locales `en,ru`.

## App composition

- `src/main/java/io/jmix/uisamples/`: `view/flowui/<section>/` (samples), `view/sys/` (`MainView`, `SampleView`), `bean/`, `config/`, `theme/`, `component/`, `doc/`, `rest/`, `entity/`.
- Menu: `uisamples-menu.xml` → parsed by `UiSamplesMenuConfig` into `UiSamplesMenuItem` (property `jmix.uisamples.menu-config`).
- Custom components: registered as `ComponentRegistration` Spring beans co-located with their `customcomponents` samples.
- `DocController` (`/doc`, `/doc/{id}`) serves each sample's description + source as plain text — a machine-readable export of the catalog.

## Pointers

- How a sample is wired and hosted → [`docs/sample-model.md`](docs/sample-model.md).
- Dual-theme system (Lumo / Aura) → [`docs/theming.md`](docs/theming.md).
