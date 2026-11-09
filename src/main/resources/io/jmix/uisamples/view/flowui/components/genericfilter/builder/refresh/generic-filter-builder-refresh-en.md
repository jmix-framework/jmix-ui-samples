This example modifies a configuration after it was registered, then refreshes the filter UI.

An empty configuration is registered with `filterConfigurationBuilder()` and activated. A filter component is then added to its root group directly, and the filter is re-rendered with `refreshCurrentConfiguration()`, which shows the new condition and updates the data loader condition.
