This sample shows how to allow a drop based on a condition — the dragged item's category.

Each card carries a category set with `DragSource.setDragData()` (`red` or `green`). When a card is dropped, the target reads `DropEvent.getDragData()` and accepts the card only if its category matches the container: red cards can be dropped only into the red container, green cards only into the green one. A drop into the wrong container is rejected and a notification is shown.

This data-driven check runs in the drop listener, so it behaves the same way in every browser.
