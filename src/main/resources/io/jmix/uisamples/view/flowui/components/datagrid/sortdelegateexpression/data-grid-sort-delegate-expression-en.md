`DataGrid.setSortBuilderDelegate()` customizes how a column is sorted.

The `{E}` alias refers to the loaded entity. Use this to make a column sort by a different or computed persistent expression than the one it displays. The expression is applied for database (persistent) sorting, which happens here because the data is paginated.

For a `KeyValueEntity` query, the `{E}` alias is not supported — use the concrete alias from the query instead, for example `"e.lastName"` for a query like `select e from Customer e`.
