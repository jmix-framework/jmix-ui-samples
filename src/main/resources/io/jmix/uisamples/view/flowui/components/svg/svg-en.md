`Svg` renders SVG markup inline, as part of the page. The sample shows three ways to provide the markup:

- The nested `<content>` element. Wrap the markup in `CDATA`, otherwise the descriptor fails to load.
- The `file` attribute with a resource path loaded by `Resources`.
- The `setSvg()` method, which accepts a `String` or an `InputStream`. Use it for markup that is built in the controller.

Since the markup becomes part of the page, `currentColor` inside it resolves to the current text color, so the chart axis and the ring label follow the theme.

The component has no `width` and `height` attributes. Set the size in one of two ways:

- With the `width` and `height` attributes of the `<svg>` element in the markup itself, as in the "Content element" column.
- With the `css` attribute of the component or a CSS class added through `classNames`, as in the "File attribute" column. For the graphic to follow the component size, define only a `viewBox` in the markup, without `width` and `height`. The SVG then stretches to the component width, and its height follows the `viewBox` proportions.
