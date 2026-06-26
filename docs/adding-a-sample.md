# Adding a sample

The authoritative step-by-step for adding a sample. The `create-sample` skill orchestrates this; this doc is the source of truth for the steps. For what a sample *is*, see [`sample-model.md`](sample-model.md); for authoring conventions (Markdown, headers, naming), see [`conventions.md`](conventions.md).

Beyond the required files below, a sample may need extra implementation files (fragments, sub-views, services, entities, CSS) — there's no fixed count (e.g. `wizard` has many). Create what the demo needs, and surface the ones worth showing via `<otherFiles>` (step 6).

## 1. Decide id, section, package

- Pick a unique kebab-case `id` (e.g. `button-foo`) — it is the join key everywhere.
- Choose the section and parent menu group; the package follows the menu tree: `src/main/java/io/jmix/uisamples/view/flowui/<section>/<sample>/` (see "Catalog structure" in [`sample-model.md`](sample-model.md)).

## 2. Create the controller and descriptor

Use a similar existing sample for conventions/patterns — copy its structure, not its file count. Sample source — the controller, descriptor, descriptions, and any `<otherFiles>` — carries **no license header** (see [`conventions.md`](conventions.md)).

`…/<sample>/<Name>Sample.java` (class name — see [`conventions.md`](conventions.md)):

```java
package io.jmix.uisamples.view.flowui.<section>.<sample>;

import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@ViewController("<id>")
@ViewDescriptor("<id>.xml")
public class <Name>Sample extends StandardView {
}
```

`…/<sample>/<id>.xml`:

```xml
<view xmlns="http://jmix.io/schema/flowui/view">
    <layout>
        <!-- demo components -->
    </layout>
</view>
```

Keep the controller minimal — only what the sample demonstrates (`@Subscribe` handlers wired to descriptor ids).

## 3. Write the descriptions (en + ru)

Create `<id>-en.md` and `<id>-ru.md` next to the descriptor. Markdown, soft-wrapped, both locales required. No menu change needed — descriptions are discovered by id+locale. Format rules in [`conventions.md`](conventions.md).

## 4. Register in the menu

Add an `<item>` under the right `<menu>` (next to its siblings) in `src/main/resources/io/jmix/uisamples/menu/uisamples-menu.xml`:

```xml
<item id="<id>" isNew="true"/>
```

- **`isNew` by default.** New samples are flagged `isNew`. When adding a whole new menu **block**, put `isNew` on the `<menu>` only — not on its child `<item>`s.
- Common attributes: `page`/`url`/`anchor` (docs link), `splitEnabled` (vertical split between demo and source instead of stacked). Full list: "Menu attributes" in [`sample-model.md`](sample-model.md).

## 5. Add the menu title (en + ru)

Add the title key to both bundles (missing title → the menu shows the raw id):

```properties
# messages_en.properties
uisamples-menu-config.<id> = <Title>
# messages_ru.properties
uisamples-menu-config.<id> = <Заголовок>
```

## 6. Derive `<otherFiles>` from the sample's source

Anything the sample *uses* that helps the reader belongs in an extra source tab. Inspect the controller and descriptor:

- Uses an entity (`io.jmix.uisamples.entity.*`)? Add it:

```xml
<item id="<id>">
    <otherFiles>
        <file name="io/jmix/uisamples/entity/<Entity>.java"/>
    </otherFiles>
</item>
```

- Adds a custom `classNames` backed by CSS? Add the per-theme file: `<file name="{currentTheme}/<section>/<file>.css"/>`.

`{currentTheme}` resolves to the active theme's sample resources (see [`theming.md`](theming.md)).

## 7. Update the section overview (only if needed)

Overview pages (`…/<section>/overview/<section>-overview.xml`) are hand-curated — one `<tag>` per component family, not per variant.

- New **component family**: add a tag to the right card — `<tag text="<label>" route="<id-simple>" classNames="overview-tag-font overview-tag-<color>"/>` — matching the card's color.
- Another **variant** of an existing family: usually leave the overview alone.

## 8. Verify

- `./gradlew compileJava` — compiles.
- `./gradlew bootRun`, open the sample: it appears in the menu (title in both locales) and shows demo + source + description tabs.
- Check it renders under **both themes** (Lumo and Aura). If a theme variant differs between themes, reconcile with markers — see [`theming.md`](theming.md).
