A code block carries two predefined actions. The copy action puts the raw source of the block on the clipboard. The wrap action switches the block between horizontal scrolling and soft wrapping.

`codeWrapped` sets the initial state of wrapping, as the second block shows. Replacing the code of a block resets wrapping to its default value.

Either action can be removed with `copyActionEnabled` or `wrapActionEnabled`. A disabled action is not rendered, which is why the third block has no buttons.

Without a language the block has no header row.
