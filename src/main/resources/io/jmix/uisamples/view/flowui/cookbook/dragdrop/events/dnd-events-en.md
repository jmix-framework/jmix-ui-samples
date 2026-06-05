This sample subscribes to every drag-and-drop event and shows a notification for each one.

A `DragSource` fires `DragStartEvent` when dragging begins and `DragEndEvent` when it ends — use `isSuccessful()` to tell a completed drop from a cancelled drag. A `DropTarget` fires `DropEvent` when the user drops on it.

`Drag sources` and `Drop zone` are drop targets, so a card can be dragged between them to fire `Dropped` and a successful `Drag ended`. `Cannot drop here` is not a drop target, so dropping a card on it produces no `Dropped` event and a cancelled `Drag ended`.
