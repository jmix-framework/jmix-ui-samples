This example shows the way of configuring a `GenericFilter` in Java without the builder API: the component, its configuration and the filter component are created and wired by hand (`uiComponents.create(...)`, `PropertyConditionUtils.generateParameterName(...)`, `SingleFilterSupport.generateValueComponent(...)`, `DesignTimeConfiguration`).

Compare it with [GenericFilter builder: overview]({contextPath}/sample/generic-filter-programmatically-defined), which does the same with the builder API.
