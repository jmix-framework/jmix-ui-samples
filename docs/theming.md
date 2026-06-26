# Theming (Lumo & Aura)

The app ships two themes and switches between them at runtime (no reload). Default is **Aura**; the project is mid-migration from Lumo to Aura. Keep one sample working under both themes — don't fork it.

## How it works

- [`AppTheme`](../src/main/java/io/jmix/uisamples/theme/AppTheme.java) — enum (`LUMO`/`AURA`) holding the ordered stylesheet URLs (base Vaadin theme + Jmix theme + `themes/<id>/styles.css`). Default is `AURA`.
- [`ThemeManager`](../src/main/java/io/jmix/uisamples/theme/ThemeManager.java) — `@UIScope` bean; swaps stylesheets via `Page.addStyleSheet`, persists the choice in `localStorage`, fires `ThemeChangedEvent`. UI-scoped on purpose: stylesheet `Registration`s are per-`Page`, so a shared list would corrupt multi-tab state.
- `SampleView` listens for `ThemeChangedEvent` and calls `refreshCurrentRoute(false)` so the live demo and source tabs re-resolve for the new theme without a full page reload.
- Theme CSS lives under `src/main/resources/META-INF/resources/themes/{lumo,aura}/` (`app/`, `components/`, `samples/`, `src/`).

## Per-theme sample differences (markers)

Some component theme-variants differ between Lumo and Aura. Reconcile them inside the single sample with comment markers processed by [`UiSamplesHelper`](../src/main/java/io/jmix/uisamples/util/UiSamplesHelper.java) — never fork the sample.

- `{currentTheme}` in a menu `<file name="…">` → resolves to `META-INF/resources/themes/<themeId>/samples`. Missing file ⇒ that source tab is silently skipped.
- `theme-only:<themeId>` … `theme-only:<themeId>:end` (in XML/Java comments) — content for one theme. In the **shown source** the foreign-theme block is stripped (`stripForDisplay`); in the **live demo** an XML element with an `id` inside a foreign-theme block is hidden, not removed (`findThemeHiddenComponentIds` + `SampleView.hideForeignThemeComponents`, run after `views.create`) — so `@ViewComponent` injections still succeed.
- `sample-hide` / `sample-hide:start`…`sample-hide:end` — sample-app plumbing that runs at runtime but must never appear in the shown source (e.g. a `themeManager.getCurrentTheme()` guard).

**Deciding which variants differ:** don't classify by grepping the theme packages — many structural variants live in the component's own base styles and work in both themes. The only reliable check is toggling the variant in the real UI under each theme and comparing the render.

## CSS conventions (Aura)

Follow Vaadin/Aura idioms strictly: justify each rule against the reference, don't mechanically find-replace Lumo, and don't blindly port Lumo compensation hacks (a Lumo rule often offsets Lumo's own behavior and breaks under Aura).

Token authority for `--aura-*` / `--vaadin-*`:
- Vaadin `web-components` repo, the `aura` package (`packages/aura/src/`: `typography.css`, `size.css`, `color.css`, `palette.css`, `surface.css`; per-component `components/*.css`) and the shared `component-base` style props — https://github.com/vaadin/web-components.
- Jmix's own migrated theme — the `jmix-flowui` dependency's `themes/jmix-aura` (diff against `jmix-lumo` for before/after).
- If you have these repos checked out locally, read them directly; otherwise use the upstream above.

Common Lumo→Aura mapping:
- `--lumo-space-*` → `--vaadin-gap-*` (gaps) / `--vaadin-padding-*` (padding, margin)
- `--lumo-primary-*` → `--aura-accent-*`; `--lumo-error`/`--lumo-success-color` → `--aura-red`/`--aura-green` (+ `-text`)
- `--lumo-secondary-text-color` → `--vaadin-text-color-secondary`
- `--lumo-contrast-Npct` → `--vaadin-border-color(-secondary)` (borders) or `--vaadin-background-container(-strong)` (fills)
- `--lumo-base-color` → `--vaadin-background-color`; `--lumo-border-radius-*` → `--vaadin-radius-*`
- `--lumo-*-color-10pct` → `color-mix(in srgb, <color> 10%, transparent)`
- dark mode: `html[theme~='dark']` override → `light-dark(light, dark)`
- style components outside-in with `::part()` / host selectors, not Lumo Shadow-DOM injection

Verify against the reference by toggling `theme=aura` in the Vaadin component docs embeds (same-origin, so computed styles are measurable).
