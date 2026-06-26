This sample demonstrates row drag-and-drop between two grids for the three grid types: `DataGrid`, `TreeDataGrid`, and `GroupDataGrid`.
Each tab shows two grids side-by-side, and a row can be dragged from one grid into the other.

Dragging is enabled with `rowsDraggable="true"`, and each grid is a drop target with `dropMode="ON_GRID"`. The `GridDragStartEvent` remembers the dragged rows and their source container, and the `GridDropEvent` listener moves them into the target container.

For the `TreeDataGrid`, the dragged node is moved together with its whole subtree and becomes a root node in the target tree. For the `GroupDataGrid`, the moved rows are regrouped automatically by the grouping column.
