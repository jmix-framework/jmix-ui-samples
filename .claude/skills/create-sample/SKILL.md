---
name: create-sample
description: Use when adding a new UI sample to the jmix-ui-samples catalog — scaffolds the controller, XML descriptor, en/ru descriptions, menu item, and title messages, derives otherFiles, and proposes sample variants first.
---

# Create sample

The procedure is in `docs/adding-a-sample.md` (the model in `docs/sample-model.md`, the rules in `docs/conventions.md`). This skill adds two things on top: propose variants first, and gate verification on the user's permission.

## Phase 0 — Propose variants (first)

Infer the component/topic and section. Survey how similar components are covered (catalog patterns: `-simple` / `-dataaware` / `-theme-variant` / `-custom-items`, …) and **propose a set of samples** to build — which variants, how many. Let the user pick; don't default to one.

## Phase 1 — Build

Read and follow `docs/adding-a-sample.md` step by step for each chosen sample — it is the source of truth for the artifacts, `<otherFiles>`, `isNew`, and the overview. Don't skip it.

## Phase 2 — Verify (only with the user's go-ahead)

Verification runs Gradle and starts the app — do it **only after the user explicitly approves**. Then verify per step 8 of `docs/adding-a-sample.md` (compile, menu title in both locales, render under both themes).
