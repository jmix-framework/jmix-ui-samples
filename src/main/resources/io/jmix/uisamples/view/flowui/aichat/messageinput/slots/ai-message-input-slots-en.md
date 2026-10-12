The `prefix` and `suffix` elements put application controls inside the input box, before and after the field. A component declared in either element is injectable by id, exactly like a top-level component of the view.

Two theme variants change the layout. `no-header-footer-gap` removes the gap that separates a band from the box, so the band reads as a continuation of the field; it affects both bands at once and leaves the spacing inside the box untouched.

`fixed-toolbar` keeps the toolbar layout at all times. Without it the message input keeps its controls on one row while the text fits a single line, and reflows them into a row of their own once the text grows past it.
