`DataGrid.setSortBuilderDelegate()` customizes how a column is sorted.

The sample above sorts the *Grade* column by a custom business priority (HIGH, then PREMIUM, then STANDARD) instead of the default enumeration order, using a `Comparator` supplied through `DataGridSortBuilder.replaceSort()`.

Without the delegate, the column sorts by the declaration order of its constants (PREMIUM, HIGH, STANDARD), because Java enums are `Comparable` by `ordinal()`.

Comparators are applied for in-memory sorting, which happens here because the whole dataset is loaded on the client. For database (persistent) sorting, supply a JPQL expression instead.
