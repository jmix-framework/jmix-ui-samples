This example builds JPQL conditions in Java with `filterComponentBuilder()`, showing the four flavors of `JpqlFilter`:

- `jpqlFilter()` (no argument) — a **void** checkbox that toggles a fixed `WHERE` clause; `defaultValue(true)` makes it active on open.
- `jpqlFilter(Class)` — a **typed** value bound to a named query parameter.
- `join()` — adds an explicit `JOIN` to the `FROM` clause.
- `hasInExpression(true)` — an `IN` condition over a collection of values.
