When the tree is filtered, each matching task is shown under its parent tasks, even if the parents don't match the filter. For example, the default filter finds `Task4`, and the tree shows it under `Task1` and `Task3`. `SimplePagination` counts only the matching tasks.

Without this solution, if the filter excludes a parent, `TreeDataGrid` hides its matching children, while `SimplePagination` still counts them.

How it works:

- `genericFilter` and `simplePagination` are bound to the `tasksDl` loader, so the `tasksDc` container holds only the matching tasks of the current page.
- The tree shows the `tasksTreeDc` container. When `tasksDc` changes, the `TreeAncestorsLoader` bean adds the missing parents loaded from the database, and the tree expands all rows.
- The tree container uses the standard sorter bound to `tasksDl`, so sorting by a column applies to all pages.

`showOrphans="true"` shows a task at the root level if its parent can't be loaded, for example, because of access restrictions. If you don't need to show the parents, this attribute alone is enough.
