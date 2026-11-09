This example registers several configurations with `filter.filterConfigurationBuilder()`.

The first configuration is made active on open with `makeCurrent()`. Its `age` condition sets a default value and hides the operation label directly on the builder (`defaultValue()`, `operationTextVisible(false)`), while the `active` condition takes a typed default via `add(component, defaultValue)` (checked at compile time). The second configuration uses a root `OR` operation (`operation(LogicalFilterComponent.Operation.OR)`), matching premium grade or senior age. `buildAndRegister()` rejects a duplicate configuration `id`.

A configuration built this way is a `DesignTimeConfiguration`, like one declared in XML. The user can change condition values but cannot edit or remove the configuration; to change the conditions, the user copies the configuration and saves the copy.
