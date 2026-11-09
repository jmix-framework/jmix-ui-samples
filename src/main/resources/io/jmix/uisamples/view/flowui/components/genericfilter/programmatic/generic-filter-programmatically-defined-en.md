In this example, a `GenericFilter` component is created and configured fully in Java using the builder API.

Filter components are created with `filter.filterComponentBuilder()`, and a configuration is assembled and registered with `filter.filterConfigurationBuilder()`. The builder performs all the required wiring (condition delegation, parameter name and value component generation), so a programmatically built component behaves exactly like one declared in XML.

The same filter configured by hand, without the builder API, is shown in [Legacy: programmatic GenericFilter]({contextPath}/sample/generic-filter-programmatic-legacy).
