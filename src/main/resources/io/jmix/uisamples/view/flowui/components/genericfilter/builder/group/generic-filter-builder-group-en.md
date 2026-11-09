This example builds a logical group in Java with `filterComponentBuilder().groupFilter()`.

Two property conditions are combined into an `OR` group with `addAll(...)`, so a customer matches when the first name contains the first value or the last name contains the second one. Use `operation(...)` to switch the group between `AND` and `OR`.
