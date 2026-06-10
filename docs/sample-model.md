# The sample model

A sample is **not** a route. The only real routes are `MainView` (menu/home shell) and `SampleView` (`/sample/:sampleId`); `SampleView` instantiates the selected sample dynamically via `views.create(sampleId)` and hosts it next to auto-generated source and description tabs. See [`SampleView`](../src/main/java/io/jmix/uisamples/view/sys/sampleview/SampleView.java).

## A sample is six co-located files

Example: the "Simple Button" sample (`id = button-simple`).

| File | Location | Role |
|------|----------|------|
| `*Sample.java` | `src/main/java/.../view/flowui/<section>/<sample>/ButtonSimpleSample.java` | `@ViewController("button-simple")` + `@ViewDescriptor("button-simple.xml")`, extends `StandardView`. |
| `<id>.xml` | same package under `src/main/resources/.../<section>/<sample>/` | the `<view>` layout |
| `<id>-en.md` | next to the descriptor | English description |
| `<id>-ru.md` | next to the descriptor | Russian description |
| `<item id="<id>">` | `uisamples-menu.xml` | registers the sample in the menu tree |
| title keys | `messages_en.properties` / `messages_ru.properties` | `uisamples-menu-config.<id> = <Title>`, in both locales |

Optional: an icon under `META-INF/resources/icons/<section>/`, and extra source tabs via menu `<otherFiles>` (see [`adding-a-sample.md`](adding-a-sample.md)).

## The id ties it together

The same id is the `@ViewController` value, the `<item id="…">` in the menu, the prefix of the description files (`<id>-<locale>.md`), and the suffix of the title key (`uisamples-menu-config.<id>`). `SampleView` resolves the controller and template through `ViewRegistry`; `UiSamplesHelper` discovers the description and message files **by convention** (controller package + id + locale) — descriptions are not registered anywhere.

## Catalog structure

The catalog is strictly structured: one component is one menu group, and the package tree, the menu tree, and the title keys correspond one-to-one.

- **Packages** under `src/main/java/io/jmix/uisamples/view/flowui/<section>/` mirror the **menu** tree in `uisamples-menu.xml`: a `<menu>` group maps to a package, an `<item>` to a sample package below it.
- Every menu node (`<menu>` and `<item>`) has exactly one title key `uisamples-menu-config.<id>` in both `messages_en` and `messages_ru`.
- The `id` is the single join key across all three. Naming details — including flat "heterogeneous" groups where the package is the id with hyphens removed — live in [`conventions.md`](conventions.md).

## Menu attributes

Menu nodes live in `uisamples-menu.xml` (parsed by `UiSamplesMenuConfig`):

- `<menu>` — a grouping node: `url` (docs-section path, **inherited by descendants**), `isNew`, `isVaadinCommercial`, `isVisibleForProduction`.
- `<item>` — a sample: `page` (docs page name), `url`/`anchor` (docs link), `splitEnabled` (split demo vs source instead of stacked), `defaultFiles` (**default `true`** — auto-adds the descriptor + controller as source tabs), `isNew`, `isVaadinCommercial`, `isVisibleForProduction`. Children: `<otherFiles><file name="…"/>`, `<urlQueryParameters><parameter name="…" value="…"/>`, `<viewParamsType>` (params passed into the sample).
- `<overview>` — a section landing page (`location` points to an overview XML), rendered by `OverviewPageGenerator` instead of a sample.
- `isVisibleForProduction="false"` hides a `<menu>`/`<item>` when Vaadin runs in production mode — used to stage in-progress samples.

## How SampleView hosts a sample

- `beforeEnter` reads `sampleId`; an empty id forwards to `MainView`.
- `views.create(sampleId)` builds the sample view; `updateLayout` shows it either stacked above the tab sheet, or in a vertical split when the menu item sets `splitEnabled`.
- `updateTabs` builds a **Description** tab (rendered from `<id>-<locale>.md`, or `.html` if present) plus **source** tabs — the descriptor, the controller, any `<otherFiles>`, and the message bundles. Source text is read at runtime from the Java copied into resources by `processResources` (see [`../ARCHITECTURE.md`](../ARCHITECTURE.md)).
- The shown source is **pre-processed before display**: theme-specific markers are stripped so each theme shows only its own code, and per-theme files resolve via `{currentTheme}` (see [`theming.md`](theming.md)). What you see in the source tab is not the raw file.
- On a runtime theme switch, `SampleView` reloads the sample via `refreshCurrentRoute` so theme-specific source and demo components re-resolve (see [`theming.md`](theming.md)).
