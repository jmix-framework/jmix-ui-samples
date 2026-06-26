# Conventions

## Descriptions

- Markdown: `<id>-en.md` and `<id>-ru.md` next to the descriptor — both locales required. Not legacy `.html` (existing `.html` is being migrated; if both exist, `.html` still wins).
- Inline code with backticks, not `<code>`; paragraphs separated by a blank line, not `<p>`; inline HTML is allowed where needed (e.g. a link).
- Auto-discovered by id + locale — no menu or registry change to add or rename one.
- Link placeholders expanded by `SampleView`'s Markdown renderer: `{currentPath}` (this sample's URL), `{contextPath}` (app context path, e.g. `/ui-samples`), `{docsBaseUrl}` (Jmix docs base, opened in a new tab). Use them for cross-links to other samples or the docs.

## Markdown

- **Soft-wrap:** one physical line per paragraph (no manual line breaks within a paragraph), a blank line between paragraphs, a trailing newline at end of file. Applies to descriptions and to these docs.

## License headers

- Apache header in every file **except sample source**. A file is sample source if it is a sample controller, an `.xml` descriptor, a description, or it is referenced from a menu `<otherFiles>` — those carry no header (it would clutter the shown source).
- Verifiable: a `.java`/`.xml` reachable from `<otherFiles>` (or part of a sample) → no header; otherwise (infrastructure) → header.

## Versions

- Name the major line only (Java 21, Jmix 3.0, Vaadin Flow 25, Spring Boot 4). Exact pins live in `build.gradle` / `settings.gradle` / `gradle.properties` — don't duplicate them in docs.

## Controllers

- Sample controllers are minimal `StandardView` subclasses — only the logic the sample demonstrates, with `@Subscribe` handlers wired to descriptor ids.

## Package naming

Packages mirror the menu tree (see "Catalog structure" in [`sample-model.md`](sample-model.md)):

- Under a per-component sub-menu, the sample sits in `<component>/<suffix>` (e.g. `button-simple` → `…/button/simple/`).
- In a flat "heterogeneous" group (no per-component sub-menu), the package leaf is the id with hyphens removed (e.g. `markdown-editor-simple` → `…/markdowneditorsimple/`).
- Package-scoped message keys embed the controller package, so they move with the package.
- Controller class name: `PascalCase(id)` + `Sample` (e.g. `button-foo` → `ButtonFooSample`).
