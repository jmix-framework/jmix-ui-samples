This example builds a logical group in Java with `filterComponentBuilder().groupFilter()`.

Two property conditions are combined into an `OR` group with `addAll(...)`, so a customer matches when either the first name or the last name contains the entered value. Use `operation(...)` to switch the group between `AND` and `OR`.
