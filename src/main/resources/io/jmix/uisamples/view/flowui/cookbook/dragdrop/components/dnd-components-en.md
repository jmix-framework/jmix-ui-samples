This sample demonstrates the basic drag-and-drop API for arbitrary components.

Use `DragSource.create()` to make a component draggable and `DropTarget.create()` to turn a layout into a drop target. Both zones are drop targets, so the cards can be dragged from one zone to the other in either direction.

When a card is dropped, `DropEvent.getDragSourceComponent()` returns the dragged component, and adding it to the target zone moves it there from its previous parent.
