This sample shows the drop effects: `MOVE`, `COPY`, `LINK`, and `NONE`.

The cards declare `EffectAllowed.ALL` with `DragSource.setEffectAllowed()`, allowing every operation. Each target declares its operation with `DropTarget.setDropEffect()`:

- `Move here` uses `DropEffect.MOVE` — a dropped card is moved out of the source.
- `Copy here` uses `DropEffect.COPY` — a copy is added and the original stays.
- `Link here` uses `DropEffect.LINK` — a link to the card is added and the original stays.
- `No drop` uses `DropEffect.NONE` — the drop is forbidden, so a card cannot be dropped there.

The drop effect controls the cursor shown while dragging over a target and is reported by `DragEndEvent.getDropEffect()`. The operation must be permitted by the source's `EffectAllowed`.
