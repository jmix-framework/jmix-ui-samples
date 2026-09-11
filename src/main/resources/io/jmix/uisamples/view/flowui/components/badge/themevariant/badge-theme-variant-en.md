`Badge` has the following theme variants:

- `success`, `warning`, `error` – color
- `small` – a smaller badge
- `filled` – a solid fill instead of a tinted surface
- `dot` – renders the badge as a small dot indicator and visually hides all its content
- `icon-only`, `number-only` – visually hide everything but the icon or the number, keeping the text for screen readers

<!-- theme-only:lumo -->
Lumo adds a `contrast` variant for a neutral badge.
<!-- theme-only:lumo:end -->

Apply variants with the `themeNames` attribute in the XML descriptor, or with `addThemeVariants(BadgeVariant…)` in the view controller.
